package com.jd.b2b.shardingjdbc.domain;


import io.swagger.annotations.ApiModelProperty;

/**
 * @author songwanke
 */
public final class OrderItem {

    @ApiModelProperty(name = "orderItemId",hidden = true)
    private long orderItemId;

    @ApiModelProperty("orderId")
    private long orderId;

    @ApiModelProperty("userId")
    private int userId;

    @ApiModelProperty("status")
    private String status;
    
    public long getOrderItemId() {
        return orderItemId;
    }
    
    public void setOrderItemId(final long orderItemId) {
        this.orderItemId = orderItemId;
    }
    
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
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
