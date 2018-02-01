#### mysql 主从复制原理  
> * Mysql的主从同步就是当master（主库）发生数据变化的时候，会实时同步到slave（从库）。  
> * 主从复制可以水平扩展数据库的负载能力，容错，高可用，数据备份。  
> * 不管是delete、update、insert，还是创建函数、存储过程，都是在master上，当master有操作的时候，slave会快速的接受到这些操作，从而做同步。  

主要的**实现原理：**  
> * 在master机器上，主从同步时间会被写到特殊的log文件中（binary-log);  
> * 在slave机器上，slave读取主从同步事件，并根据读取的事件变化，在slave库上做相应的更改。  


参考：https://www.cnblogs.com/xiaoyuanren/p/7887451.html