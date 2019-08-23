package cn.tyrone.springboot.rabbitmq.beans;

import java.io.Serializable;

public class RabbitObject implements Serializable {

    private String id;

    private String messge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    @Override
    public String toString() {
        return "RabbitObject{" +
                "id='" + id + '\'' +
                ", messge='" + messge + '\'' +
                '}';
    }
}
