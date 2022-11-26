package cn.tyrone.springboot.springdatajpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Gender {

    MAN("MAN", "男性"),
    WOMEN("WOMEN", "女性"),
    ;
    private String gender;

    private String desc;

    public static Gender of(String genderValue) {
        return Stream.of(Gender.values())
                .filter(gender -> gender.gender.equals(genderValue))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
