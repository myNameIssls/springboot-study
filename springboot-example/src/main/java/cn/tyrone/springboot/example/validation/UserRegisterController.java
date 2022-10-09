package cn.tyrone.springboot.example.validation;

import cn.tyrone.springboot.example.validation.entity.Response;
import cn.tyrone.springboot.example.validation.entity.UserRegisterCommand;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class UserRegisterController {

    @RequestMapping("/register")
    public Response register(@Valid UserRegisterCommand command, BindingResult bindingResult){
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
