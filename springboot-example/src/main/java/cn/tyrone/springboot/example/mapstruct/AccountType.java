package cn.tyrone.springboot.example.mapstruct;

//@AllArgsConstructor
public enum AccountType {

    BALANCE("BALANCE", "余额账户"),
    PROFIT("PROFIT", "收益账户"),

    ;
    public String accountType;

    public String describe;

    AccountType(){}

    AccountType(String accountType, String describe){
        this.accountType = accountType;
        this.describe = describe;
    }



}
