package com.jd.b2b.shardingjdbc.web.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @author songwanke
 * @date 2018/5/15
 */
public class ModuloPreciseDatabaseShardingAlgorithm implements PreciseShardingAlgorithm {

    private final String bdPrefix = "demo_ds_";

    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        return bdPrefix + (Integer)shardingValue.getValue() % 2 ;
    }

}
