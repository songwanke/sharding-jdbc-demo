package com.jd.b2b.shardingjdbc.web;

import com.jd.b2b.shardingjdbc.domain.OrderItem;
import com.jd.b2b.shardingjdbc.service.OrderItemService;
import io.shardingjdbc.core.api.HintManager;
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
@RequestMapping("/orderItem/test")
@Slf4j
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping(value = "/selectAll",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    public List<OrderItem> selectAll(){
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue(45);
        List<OrderItem> orderItems = orderItemService.selectAll();
        hintManager.close();
        log.info("orderItems：{}",orderItems.size());

        return orderItems;
    }

    @RequestMapping(value = "/insert",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public void insert(OrderItem orderItem){

        log.info("orderItem：{}",orderItem);
        orderItemService.insert(orderItem);

    }

}
