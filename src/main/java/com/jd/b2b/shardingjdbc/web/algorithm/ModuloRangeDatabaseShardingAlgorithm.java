package com.jd.b2b.shardingjdbc.web.algorithm;

import com.google.common.collect.Range;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author songwanke
 * @date 2018/5/16
 */
public class ModuloRangeDatabaseShardingAlgorithm implements RangeShardingAlgorithm{

    private final String bdPrefix = "demo_ds_";

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, RangeShardingValue shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames);
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            result.add(bdPrefix + i % 2);
        }
        return result;
    }
}
