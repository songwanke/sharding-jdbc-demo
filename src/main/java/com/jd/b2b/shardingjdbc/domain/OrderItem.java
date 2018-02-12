package com.jd.b2b.shardingjdbc.domain;


import io.swagger.annotations.ApiModelProperty;

/**
 * @author songwanke
 */
public final class OrderItem {

    @ApiModelProperty(name = "orderItemId",hidden = true)
    private Long orderItemId;

    @ApiModelProperty("orderId")
    private Long orderId;

    @ApiModelProperty("userId")
    private Integer userId;

    @ApiModelProperty("status")
    private String status;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

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
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
