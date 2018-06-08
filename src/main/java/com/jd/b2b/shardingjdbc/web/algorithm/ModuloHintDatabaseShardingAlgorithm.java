package com.jd.b2b.shardingjdbc.web.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.ListShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.ShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.hint.HintShardingAlgorithm;

import java.util.Collection;
import java.util.Collections;

/**
 * @author songwanke
 * @date 2018/5/22
 *
 */
public class ModuloHintDatabaseShardingAlgorithm implements HintShardingAlgorithm{
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
        for(String each:availableTargetNames){
            Collection values = ((ListShardingValue) shardingValue).getValues();
            Integer next = ((int)values.iterator().next());
            if(each.endsWith(next % 2 +"")){
                return Collections.singletonList(each);
            }
        }
        throw new UnsupportedOperationException();
    }
}
