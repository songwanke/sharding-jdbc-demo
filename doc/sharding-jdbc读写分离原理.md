#### 读写分离原理解读  
参考：http://blog.csdn.net/yanyan19880509/article/details/78170233  

#### sharding-jdbc读写分离特性  
##### 支持项  
> * 提供了一主多从的读写分离配置，可独立使用，也可配合分库分表使用。
> * 独立使用读写分离支持SQL透传。 
> * 同一线程且同一数据库连接内，如有写入操作，以后的读操作均从主库读取，用于保证数据一致性。  
> * Spring命名空间。  
> * 基于Hint的强制主库路由。  

##### 不支持范围  
> * 主库和从库的数据同步。  
> * 主库和从库的数据同步延迟导致的数据不一致。  
> * 主库双写或多写(不支持批量写操作)  

#### 读写分离实现原理  
一般我们是这样来执行sql语句的：  
```
  Connection conn = dataSource.getConnection();
  PreparedStatement preparedStatement = conn.prepareStatement(sql);
  preparedStatement.executeQuery();  
```  
这是利用原生jdbc操作数据库查询语句的一般流程，获取一个连接，然后生成Statement，最后再执行查询。那么sharding-jdbc是在哪一块进行扩展从而实现读写分离的呢？  
想一下，想要实现读写分离，必然会涉及到多个底层的Connection，从而构造出不同连接下的Statement语句，而很多第三方软件，如Spring，为了实现事务，调用dataSource.getConnection()之后，在一次请求过程中，可能就不会再次调用getConnection方法了，所以在dataSource.getConnection中做读写扩展是不可取的。为了更好的说明问题，看下面的例子：  
```  
Connection conn = getConnection();
PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
preparedStatement1.executeQuery();

Connection conn2 = getConnection();
PreparedStatement preparedStatement2 = conn2.prepareStatement(sql2);
preparedStatement2.executeUpdate();  
```  
一次请求过程中，为了实现事务，一般的做法是当线程第一次调用getConnection方法时，获取一个底层连接，然后存储到ThreadLocal变量中去，下次就直接在ThreadLocal中获取了。为了实现一个事务中，针对一个数据源，既可能获取到主库连接，也可能获取到从库连接，还能够切换，sharding-jdbc在PreparedStatement(实际上为ShardingPreparedStatement)的executeXX层进行了主从库的连接处理。  
下图为sharding-jdbc执行的部分流程：  
![](./img/sharding-jdbc读写分离执行的部分流程图.png)  

sharding-jdbc使用ShardingPreparedStatement来替代PreparedStatement，在执行ShardingPreparedStatement的executeXX方法时，通过路由计算，得到PreparedStatementUnit单元列表，然后执行后合并结果返回，而PreparedStatementUnit只不过封装了原生的PreparedStatement。读写分离最关键的地方在上图标绿色的地方，也就是生成PreparedStatement的地方。 
在使用SQLEcecutionUnit转换为PreparedStatement的时候，有一个重要的步骤就是必须先获取Connection，源码如下：
```
public Connection getConnection(final String dataSourceName, final SQLType sqlType) throws SQLException {
        if (getCachedConnections().containsKey(dataSourceName)) {
            return getCachedConnections().get(dataSourceName);
        }
        DataSource dataSource = shardingContext.getShardingRule().getDataSourceRule().getDataSource(dataSourceName);
        Preconditions.checkState(null != dataSource, "Missing the rule of %s in DataSourceRule", dataSourceName);
        String realDataSourceName;
        if (dataSource instanceof MasterSlaveDataSource) {
            NamedDataSource namedDataSource = ((MasterSlaveDataSource) dataSource).getDataSource(sqlType);
            realDataSourceName = namedDataSource.getName();
            if (getCachedConnections().containsKey(realDataSourceName)) {
                return getCachedConnections().get(realDataSourceName);
            }
            dataSource = namedDataSource.getDataSource();
        } else {
            realDataSourceName = dataSourceName;
        }
        Connection result = dataSource.getConnection();
        getCachedConnections().put(realDataSourceName, result);
        replayMethodsInvocation(result);
        return result;
    }
```  
如果发现数据源对象为MasterSlaveDataSource类型，则会使用如下方式获取真正的数据源：
```
public NamedDataSource getDataSource(final SQLType sqlType) {
    if (isMasterRoute(sqlType)) {
        DML_FLAG.set(true);
        return new NamedDataSource(masterDataSourceName, masterDataSource);
    }
    String selectedSourceName = masterSlaveLoadBalanceStrategy.getDataSource(name, masterDataSourceName, new ArrayList<>(slaveDataSources.keySet()));
    DataSource selectedSource = selectedSourceName.equals(masterDataSourceName) ? masterDataSource : slaveDataSources.get(selectedSourceName);
    Preconditions.checkNotNull(selectedSource, "");
    return new NamedDataSource(selectedSourceName, selectedSource);
}
private static boolean isMasterRoute(final SQLType sqlType) {
    return SQLType.DQL != sqlType || DML_FLAG.get() || HintManagerHolder.isMasterRouteOnly();
}
```  
有三种情况会认为一定要走主库： 
> * 不是查询类型的语句，比如更新字段 
> * DML_FLAG变量为true的时候 
> * 强制Hint方式走主库  
当执行了更新语句的时候，isMasterRoute()==true，这时候，Connection为主库的连接，并且引擎会强制设置DML_FLAG的值为true，这样一个请求后续的所有读操作都会走主库。   
有些时候，我们想强制走主库，这时候在请求最开始执行Hint操作即可，如下所示：
```
HintManager hintManager = HintManager.getInstance();
hintManager.setMasterRouteOnly();
```  
在获取数据源的时候，如果走的是从库，会使用从库负载均衡算法类进行处理，该类的实现比较简单，如下所示：  
```
public final class RoundRobinMasterSlaveLoadBalanceStrategy implements MasterSlaveLoadBalanceStrategy {

    private static final ConcurrentHashMap<String, AtomicInteger> COUNT_MAP = new ConcurrentHashMap<>();

    @Override
    public String getDataSource(final String name, final String masterDataSourceName, final List<String> slaveDataSourceNames) {
        AtomicInteger count = COUNT_MAP.containsKey(name) ? COUNT_MAP.get(name) : new AtomicInteger(0);
        COUNT_MAP.putIfAbsent(name, count);
        count.compareAndSet(slaveDataSourceNames.size(), 0);
        return slaveDataSourceNames.get(count.getAndIncrement() % slaveDataSourceNames.size());
    }
}
```  
