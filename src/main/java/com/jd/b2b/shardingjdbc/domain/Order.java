package com.jd.b2b.shardingjdbc.domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * The type Order.
 * @author songwanke
 */
public class Order {

    @ApiModelProperty(name = "orderId",hidden = true)
    private long orderId;

    @ApiModelProperty(name = "userId")
    private int userId;

    @ApiModelProperty(name = "status")
    private String status;
    
    public long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(final long orderId) {
        this.orderId = orderId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
