package com.jd.b2b.shardingjdbc.service.impl;

import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.domain.OrderItem;
import com.jd.b2b.shardingjdbc.service.OrderItemService;
import com.jd.b2b.shardingjdbc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
            order.setUserId(56);
            order.setStatus("ok");
            orderService.insert(order);
            long orderId = order.getOrderId();
            log.info("orderId：{}",orderId);
            orderItem.setOrderId(orderId);
            orderItem.setStatus("ok");
            orderItem.setUserId(56);
            orderItemService.insert(orderItem);

//            orderService.delete(orderId);
        }
    }

    /**
     * 不支持批量操作
     * @throws Exception
     */
    @Test
    public void batchInsert() throws Exception {
        List<Order> orderList = new ArrayList<>();
        for (int i=0;i<5;i++){
            Order order = new Order();
            order.setUserId(5+i);
            order.setStatus("ok"+i);
            orderList.add(order);
        }
        log.info("order信息：{}",orderList);
        orderService.batchInsert(orderList);
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

    /**
     * 支持
     * @throws Exception
     */
    @Test
    public void selectUnion() throws Exception {
        Long orderId = 7L;
        List<Long> orderList = orderService.selectUnion(orderId);
        orderList.stream().forEach(order -> {
            log.info(order.toString());
        });
    }

    /**
     * 数据不对
     * @throws Exception
     */
    @Test
    public void selector() throws Exception {
        List<Order> orderList = orderService.selector();
        orderList.stream().forEach(order -> {
            log.info(order.toString());
        });
    }


}