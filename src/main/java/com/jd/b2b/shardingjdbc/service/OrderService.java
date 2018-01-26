package com.jd.b2b.shardingjdbc.service;

import com.jd.b2b.shardingjdbc.domain.Order;
import org.springframework.stereotype.Service;

/**
 * @author songwanke
 * @date 2018/1/24
 */
@Service
public interface OrderService {

    Long insert(Order model);

    void delete(Long orderId);
}
