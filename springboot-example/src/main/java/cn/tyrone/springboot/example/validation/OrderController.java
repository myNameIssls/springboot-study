package cn.tyrone.springboot.example.validation;

import cn.tyrone.springboot.example.validation.entity.Order;
import cn.tyrone.springboot.example.validation.entity.Response;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class OrderController {

    @RequestMapping("/save")
    public Response save(@Validated Order order, BindingResult bindingResult){
        Response response = Response.builder().build();
        response.success();
        boolean hasErrors = bindingResult.hasErrors();
        if (hasErrors) {
            String message = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
            response.error(message);
        }
        return response;
    }

}
