package com.integration.core.excp;

/**
 * token异常
 */
public class TokenExcept extends RuntimeException {
    private static final long serialVersionUID = 2045497797561458714L;

    private String errCode;
    private String errType;


    /**
     * 非法请求
     */
    public final static String ERR_SESSION_ILLEGAL = "0";
    public final static String ERR_TOKEN_NULL = "2";

    /**
     * @param errCode 异常码，用于运维追踪问题节点
     */
    public TokenExcept(String errCode, String errType, String message) {
        super(message);
        this.errCode = errCode;
        this.errType = errType;
    }

    public static TokenExcept createTokenException0() {
        return new TokenExcept(ERR_SESSION_ILLEGAL, "", "非法请求");
    }

    public static TokenExcept createTokenException0(String message) {
        return new TokenExcept(ERR_SESSION_ILLEGAL, "", message);
    }

    public static TokenExcept createTokenException2(String errType) {
        return new TokenExcept(ERR_TOKEN_NULL, errType, "认证失败");
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrType() {
        return errType;
    }
}