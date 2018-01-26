package com.jd.b2b.shardingjdbc.mapper;


import com.jd.b2b.shardingjdbc.domain.Order;

public interface OrderMapper {

    Long insert(Order model);
    
    void delete(Long orderId);
    
}
