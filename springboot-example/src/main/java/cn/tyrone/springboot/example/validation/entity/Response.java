package cn.tyrone.springboot.example.validation.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    private Boolean success;
    private String message;

    public void success(){
        this.success = Boolean.TRUE;
        this.message = "操作成功";
    }

    public void error(String message){
        this.success = Boolean.FALSE;
        this.message = message;
    }

}
