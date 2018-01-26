package com.jd.b2b.shardingjdbc.service.impl;

import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.domain.OrderItem;
import com.jd.b2b.shardingjdbc.service.OrderItemService;
import com.jd.b2b.shardingjdbc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author songwanke
 * @date 2018/1/24
 */
@Slf4j
public class OrderItemServiceImplTest extends BasetTest{

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;



    @Test
    public void insert() throws Exception {
        OrderItem orderItem = new OrderItem();
        for (int i=0;i<5;i++){
            Order order = new Order();
            order.setUserId(66);
            order.setStatus("ok");
            orderService.insert(order);
            long orderId = order.getOrderId();

            orderItem.setOrderId(orderId);
            orderItem.setStatus("ok");
            orderItem.setUserId(77);
            orderItemService.insert(orderItem);
        }
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void selectAll() throws Exception {

        List<OrderItem> orderItems = orderItemService.selectAll();
        log.info("orderItems：{}",orderItems.size());
  /*      orderItems.stream().forEach(orderItem -> {
            log.info(orderItem.toString());
        });*/

        List<OrderItem> orderItemList = orderItemService.selectAll();
        log.info("orderItemList:{}",orderItemList.size());
       /* orderItemList.stream().forEach(orderItem -> {
            log.info(orderItem.toString());
        });*/

    }

}