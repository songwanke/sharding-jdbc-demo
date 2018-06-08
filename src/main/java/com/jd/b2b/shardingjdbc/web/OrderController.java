package com.jd.b2b.shardingjdbc.web;

import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.service.OrderService;
import com.jd.ecc.commons.db.hintmanager.HintManagerUtil;
import io.shardingjdbc.core.api.HintManager;
import io.shardingjdbc.core.api.algorithm.sharding.ShardingValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songwanke
 * @date 2018/1/30
 */
@RestController
@RequestMapping("/order/test")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/insert",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public void insert(Order order){
        log.info("插入order信息：{}",order);
        Order order1 = new Order(48L,22,"is ok");
        orderService.insert(order);
    }

    @RequestMapping(value = "/selectAll",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    public List<Order> selctAll() throws Exception {

        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue("user_id");
        List<Order> orders = orderService.selectAll();
        hintManager.close();
        log.info("orders：{},orders.size:{}",orders,orders.size());
        return orders;
    }

        @RequestMapping(value = "/test",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
        public String test() throws Exception {


        return "";
    }

    @RequestMapping(value = "/insertBath",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public void insertBath(){
        Order order1 = new Order(6565L,22,"is ok");
        Order order2 = new Order(6566L,22,"is not ok");
        List<Order> list = new ArrayList<>();
        list.add(order1);
        list.add(order2);
        log.info("插入order信息：{}",list);
        orderService.insertBath(list);
    }

    @RequestMapping(value = "/selector",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public void selector() throws Exception {
        List<Order> orderList = orderService.selector();
        orderList.stream().forEach(order -> {
            log.info(order.toString());
        });
    }
}
