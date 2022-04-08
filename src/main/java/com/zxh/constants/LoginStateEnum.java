package com.zxh.constants;

public enum LoginStateEnum {
    FailCaseAccount(0,"FAIL","登录失败,账号或密码错误"),
    FailCaseCode(1,"FAIL","登录失败，验证错误"),
    Success(2, "SUCCESS","登录成功"),
    UserException(3,"FAIL","用户状态异常");


    private int code;
    private String value;
    private String show;

    LoginStateEnum(int code, String value, String show) {
        this.code = code;
        this.value = value;
        this.show=show;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getShow(){
        return show;
    }
}
