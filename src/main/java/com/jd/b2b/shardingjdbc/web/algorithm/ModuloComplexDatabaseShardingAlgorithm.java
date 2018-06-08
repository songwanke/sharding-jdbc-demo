package com.jd.b2b.shardingjdbc.web.algorithm;

import com.google.common.collect.Range;
import io.shardingjdbc.core.api.algorithm.sharding.ListShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.ShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * @author songwanke
 * @date 2018/5/17
 */
public class ModuloComplexDatabaseShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
        ShardingValue shardingValue = shardingValues.iterator().next();
        if(shardingValue instanceof PreciseShardingValue){
            return doEqualsSharding(availableTargetNames,(PreciseShardingValue<Integer>) shardingValue);
        }

        if(shardingValue instanceof ListShardingValue){
            return doInSharding(availableTargetNames,(ListShardingValue<Integer>) shardingValue);
        }

        if(shardingValue instanceof RangeShardingValue){
            return doRangeSharding(availableTargetNames,(RangeShardingValue<Integer>) shardingValue);
        }
        return null;
    }

    public Collection<String> doEqualsSharding(Collection<String> availableTargetNames, PreciseShardingValue shardingValue) {
        Integer modulo = Integer.parseInt(shardingValue.getValue().toString()) % 2;
        for(String each:availableTargetNames){
            if(each.endsWith(modulo.toString())){
                return Collections.singletonList(each);
            }
        }

        throw new UnsupportedOperationException();
    }

    public Collection<String> doInSharding(Collection<String> availableTargetNames, ListShardingValue shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Collection<Integer> values = shardingValue.getValues();
        for(Integer value:values) {
            for (String each : availableTargetNames) {
                if (each.endsWith(value % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    public Collection<String> doRangeSharding(Collection<String> availableTargetNames, RangeShardingValue shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Integer> range = shardingValue.getValueRange();
        for(Integer i = range.lowerEndpoint();i<=range.upperEndpoint();i++){
            for (String each : availableTargetNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}
