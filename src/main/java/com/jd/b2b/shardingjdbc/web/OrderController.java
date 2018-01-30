package com.jd.b2b.shardingjdbc.web;

import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        orderService.insert(order);
    }

    @RequestMapping(value = "/selectAll",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    public Integer selectAll() throws Exception {

        List<Order> orders = orderService.selectAll();
        log.info("orders：{},orders.size:{}",orders,orders.size());

        return orders.size();
    }
}
