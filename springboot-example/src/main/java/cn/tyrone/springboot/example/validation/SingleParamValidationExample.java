package cn.tyrone.springboot.example.validation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 单个参数校验
 * 注意：单个参数校验时，需要在控制器上增加@Validated注解，否则，校验不生效
 */
@Validated
@RestController
public class SingleParamValidationExample {

    /**
     * 正例：http://localhost:7100/SingleParamValidation?singleParam=1
     * 反例：http://localhost:7100/SingleParamValidation
     * @param singleParam
     * @return
     */
    @GetMapping("/SingleParamValidation")
    public Object SingleParamValidation(@NotBlank(message = "单个参数不允许为空") String singleParam){
        String response = "单个参数校验示例程序测试通过";
        return response;
    }

}
