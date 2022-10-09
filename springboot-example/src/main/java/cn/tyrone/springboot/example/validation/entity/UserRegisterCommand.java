package cn.tyrone.springboot.example.validation.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * 用户注册命令
 */
@Data
public class UserRegisterCommand {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不允许为空")
    @Length(min = 2, max = 4, message = "用户名最少2个字符，最多4个字符")
    private String userName;

    /**
     * 密码
     */
    @NotNull(message = "密码不允许为空")
    @Size(min = 6, max = 10, message = "密码长度必须在6-10之间")
    private String cipher;

    @Min(value = 18, message = "年龄最小18岁")
    @Max(value = 60, message = "年龄最大60岁")
    private Integer age;

    /**
     * 邮箱
     */
    @Email(message = "邮箱不合法")
    @NotBlank(message = "邮箱不允许为空")
    private String email;
    /**
     * 备注
     */
    private String memo;

}
