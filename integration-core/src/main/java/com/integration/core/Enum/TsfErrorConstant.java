package com.integration.core.Enum;

public enum  TsfErrorConstant {

    SUCCESS("0000000","成功"),
    FAIL("0000001","接口级异常")
    ;


    private String code;

    private String message;


    TsfErrorConstant(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
