package com.jd.b2b.shardingjdbc.mapper;


import com.jd.b2b.shardingjdbc.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface OrderMapper {

    Long insert(Order model);
    
    void delete(Long orderId);

    void batchInsert(@Param("orderList") List<Order> orderList);

    List<Long> selectUnion(Long orderId);


    List<Order> selector();

    List<Order> selectAll();
}
