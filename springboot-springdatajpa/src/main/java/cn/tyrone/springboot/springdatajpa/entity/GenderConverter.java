package cn.tyrone.springboot.springdatajpa.entity;

import javax.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender.getGender();
    }

    @Override
    public Gender convertToEntityAttribute(String value) {
        return Gender.of(value);
    }
}
