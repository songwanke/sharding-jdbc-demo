package com.jd.b2b.shardingjdbc.service.impl;

import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.domain.OrderItem;
import com.jd.b2b.shardingjdbc.mapper.OrderMapper;
import com.jd.b2b.shardingjdbc.service.OrderItemService;
import com.jd.b2b.shardingjdbc.service.OrderService;
import com.jd.ecc.commons.db.hintmanager.HintRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @author songwanke
 * @date 2018/1/24
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    private OrderItemService orderItemService;

//    @Override
////    @HintRoute
//    @Transactional(timeout = 2000)
//    public Long insert(Order model) {
//        List<Order> orders = orderMapper.selectAll();
//
//        List<OrderItem> orderItems1 = orderItemService.selectAll();
//        Long orderId = orderMapper.insert(model);
//
//        List<OrderItem> orderItems2 = orderItemService.selectAll();
//
//        Order order1 = orderMapper.select(model.getOrderId());
//
//        model.setStatus("2345");
//        model.setOrderId(order1.getOrderId());
//        Long insert = orderMapper.updateStatus(model);
//
//        try {
//            Thread.sleep(10000);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < 10; i++) {
//            model.setStatus("2345" + i);
//            Long id = orderMapper.insert(model);
//            Random random = new Random();
//
//            if (random.nextInt(100) % 2 == 0) {
//                throw new RuntimeException();
//            }
//        }
//
//
//        List<Order> orders1 = orderMapper.selectAll();
//        return orderId;
//    }

    @Override
    public void delete(Long orderId) {
        orderMapper.delete(orderId);
    }

    @Override
    public void batchInsert(List<Order> orderList) {
        orderMapper.batchInsert(orderList);
    }

    @Override
    public List<Long> selectUnion(Long orderId) {
        return orderMapper.selectUnion(orderId);
    }

    /**
     * 校验or
     *
     * @return
     */
    @Override
    public List<Order> selector() {
        return orderMapper.selector();
    }

    // @HintRoute
    @Override
    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    @Override
    @Transactional
    public Long insertBath(List<Order> list) {
        List<Order> orders = orderMapper.selectAll();
        orderMapper.insertBath(list);
        int a = 0;
        a = 1 / a;
        List<Order> orders1 = orderMapper.selectAll();
        return 1L;
    }

    @Override
//    @HintRoute
    @Transactional(timeout = 2000)
    public Long insert(Order model) {

        Order order1 = orderMapper.select(model.getOrderId());
        if(null == order1){
            Long orderId = orderMapper.insert(model);
        }else{
            model.setStatus("2345");
            model.setOrderId(order1.getOrderId());
            Long insert = orderMapper.updateStatus(model);
        }

        return 1L;
    }
}
