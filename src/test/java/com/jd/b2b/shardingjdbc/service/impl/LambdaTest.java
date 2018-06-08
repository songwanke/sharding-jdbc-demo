package com.jd.b2b.shardingjdbc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songwanke
 * @date 2018/5/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class LambdaTest {

    @Test
    public void mapTest(){
        List<String> G7 = Arrays.asList("bb","l","o","v","u","kk");
        String collect = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(","));
        System.out.println(collect);
    }
}
