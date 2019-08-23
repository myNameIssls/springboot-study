package cn.tyrone.springboot.integrate.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select(value = {"select * from sfb_capital_user_project"})
    public List<Map<String, Object>> list();

    public List<Map<String, Object>> listxml();
}
