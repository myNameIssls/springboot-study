package cn.tyrone.springboot.springdatajpa.entity;

import javax.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<Gender, String> {

    /**
     * 枚举转到数据库值
     * @param gender
     * @return
     */
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender.getGender();
    }

    /**
     * 数据库值转换成枚举值
     * @param value
     * @return
     */
    @Override
    public Gender convertToEntityAttribute(String value) {
        return Gender.of(value);
    }
}
