package cn.tyrone.springboot.springdatajpa.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

@Data
@Builder
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Entity(name = "user_")
//@Table(name = "user_")
@org.hibernate.annotations.Table(appliesTo = "user_", comment = "用户表")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_code", unique = true, nullable = false, columnDefinition = "varchar(10) comment '用户编码'")
    private String userCode;
    @Column(name = "name", nullable = false, columnDefinition = "varchar(50) comment '用户名称'")
    private String name;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(50) comment '用户密码'")
    private String password;
    @Convert(converter = GenderConverter.class)
    @Column(name = "gender", nullable = false,  columnDefinition = "varchar(50) comment '性别：MAN：男性，WOMAN：女性'" )
    private Gender gender;

    @Type(type = "json")
    @Column(name = "extend_info", nullable = true, columnDefinition = "json comment '适用企业信息'")
    private Map<String, String> extendInfo;

}
