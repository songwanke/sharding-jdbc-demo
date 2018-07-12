package com.jd.b2b.shardingjdbc.web.algorithm;

import io.shardingjdbc.core.api.ConfigMapContext;
import io.shardingjdbc.core.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author songwanke
 * @date 2018/6/11
 */
@Slf4j
public class WeightRandomMasterSlaveLoadBalanceAlgorithm implements MasterSlaveLoadBalanceAlgorithm{

    @Override
    public String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames) {

        TreeMap<Double, String> weightMap = new TreeMap<Double, String>();
        List<Pair<String, Integer>> starsPair = new ArrayList<Pair<String,Integer>>();
        //配置中心获取权重信息
        Map<String, Object> masterSlaveConfig = ConfigMapContext.getInstance().getMasterSlaveConfig();

       for(Map.Entry<String, Object> entry:masterSlaveConfig.entrySet()){
           //构建一个Pair对象存储权重和对应的Key
           Pair<String, Integer> starPair = new Pair<String, Integer>(entry.getKey(), Integer.valueOf((String)entry.getValue()));
           //list组装
           starsPair.add(starPair);
       }

       for(Pair<String,Integer> pair:starsPair){
           //利用TreeMap实现存储权重信息
           double lastWeight = weightMap.size() == 0 ? 0 : weightMap.lastKey().doubleValue();
           //权重相加
           weightMap.put(pair.getValue().doubleValue()+lastWeight,pair.getKey());
       }

        //获取随机权重
        double randomWeight = weightMap.lastKey()*Math.random();
        // 获取key>=随机权重的数据
        SortedMap<Double, String> tailMap = weightMap.tailMap(randomWeight, false);

        name = weightMap.get(tailMap.firstKey());
        log.info("数据库名称：{}",name);
        return name;
    }
}
