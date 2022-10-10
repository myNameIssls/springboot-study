package cn.tyrone.springboot.example.validation.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddAndEditCommand {

    @NotBlank(message = "ID不允许为空", groups = EditGroup.class)
    private String id;

    @NotBlank(message = "姓名不允许为空")
    private String name;

    @NotNull(message = "地址不允许为null")
    private String addr;

}
