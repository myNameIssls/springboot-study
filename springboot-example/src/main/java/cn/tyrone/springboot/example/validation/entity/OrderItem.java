package cn.tyrone.springboot.example.validation.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderItem {

    /**
     * 订单项流水号
     */
    @NotBlank(message = "订单项流水号不允许为空")
    private String orderItemSerialNum;

}
