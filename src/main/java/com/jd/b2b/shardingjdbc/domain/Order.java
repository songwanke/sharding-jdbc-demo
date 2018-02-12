package com.jd.b2b.shardingjdbc.domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * The type Order.
 * @author songwanke
 */
public class Order {

    @ApiModelProperty(name = "orderId",hidden = true)
    private Long orderId;

    @ApiModelProperty(name = "userId")
    private Integer userId;

    @ApiModelProperty(name = "status")
    private String status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
