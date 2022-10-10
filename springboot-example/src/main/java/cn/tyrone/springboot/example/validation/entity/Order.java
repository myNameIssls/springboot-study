package cn.tyrone.springboot.example.validation.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Order {

    /**
     * 订单流水号
     */
    @NotBlank(message = "订单流水号不允许为空")
    private String orderSerialNum;

    @Valid // 添加该注解，可以校验OrderItem属性
    @NotNull(message = "订单项不允许为空")
    @Size(min = 1, message = "最少包含一个订单项")
    private List<OrderItem> orderItemList;

}
