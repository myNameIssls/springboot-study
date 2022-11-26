package cn.tyrone.springboot.example.mapstruct;

import java.math.BigDecimal;

public class VirtualAccountEntity {

    private Long id;

    private String virtualAccountName;

    private String virtualAccount;

    private BigDecimal accountBalance = BigDecimal.ZERO;

    private AccountProperty accountProperty;



}
