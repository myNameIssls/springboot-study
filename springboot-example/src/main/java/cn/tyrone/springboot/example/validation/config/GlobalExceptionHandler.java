package cn.tyrone.springboot.example.validation.config;

import cn.tyrone.springboot.example.validation.entity.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 单个参数校验失败异常
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response constraintViolationExceptionHandler(ConstraintViolationException exception){
        String message = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        Response response = Response.builder().build();
        response.error(message);
        return response;
    }

    /**
     * Http 请求类型不支持
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException exception){
        String message = exception.getMessage();
        Response response = Response.builder().build();
        response.error(message);
        return response;
    }

    /**
     * 处理org.springframework.validation.BindException异常，解决form表单类型校验失败问题
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Response bindException(BindException exception) {
        String message = exception.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        Response response = Response.builder().build();
        response.error(message);
        return response;
    }

    /**
     * 处理org.springframework.http.converter.HttpMessageNotReadableException异常，处理参数缺失异常
     *
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        String message = "缺少必需的请求正文";
        Response response = Response.builder().build();
        response.error(message);
        return response;
    }

    /**
     * 处理 org.springframework.web.bind.MethodArgumentNotValidException，解决@RequestBody参数校验问题
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        Response response = Response.builder().build();
        response.error(message);
        return response;
    }

}
