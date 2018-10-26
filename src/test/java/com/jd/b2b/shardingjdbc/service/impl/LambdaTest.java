package com.jd.b2b.shardingjdbc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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

    @Test
    public void andThen(){
        Function<Integer, Integer> name = e -> e * 2;
        Function<Integer, Integer> square = e -> e * e;

        //  andThen 先执行调用者，然后再执行参数 3*2-->6*6
        int value = name.andThen(square).apply(3);
        System.out.println("andThen value=" + value);

        // compose 函数先执行参数，然后执行调用者 3*3->9*2
        int value2 = name.compose(square).apply(3);
        System.out.println("compose value2=" + value2);

        //返回一个执行了apply()方法之后只会返回输入参数的函数对象
        Object identity = Function.identity().apply("huohuo");
        System.out.println(identity);

    }
}
