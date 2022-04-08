package com.zxh.constants;

public enum RecordStateEnum {

    Success(1, "SUCCESS","成功"),
    Fail(0,"FAIL","失败");

    private Integer code;
    private String value;
    private String show;

    RecordStateEnum(int code, String value, String show) {
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

