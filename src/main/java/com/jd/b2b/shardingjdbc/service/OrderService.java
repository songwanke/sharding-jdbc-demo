package com.jd.b2b.shardingjdbc.service;

import com.jd.b2b.shardingjdbc.domain.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songwanke
 * @date 2018/1/24
 */
@Service
public interface OrderService {

    Long insert(Order model);

    void delete(Long orderId);

    void batchInsert(List<Order> orderList);

    /**
     * 校验Union
     * @param orderId
     * @return
     */
    List<Long> selectUnion(Long orderId);

    /**
     * 校验or
     * @return
     */
    List<Order> selector();

    /**
     * 查询所有
     * @return
     */

    List<Order> selectAll();
}
