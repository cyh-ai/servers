package com.integration.core.excp;

import com.integration.core.Enum.TsfErrorConstant;

/**
 * 对外统一异常
 */
public class FaInsExcept extends RuntimeException{


    private String errCode;

    public FaInsExcept() {
        super();
    }

    public FaInsExcept(TsfErrorConstant tsfErrorConstant){
        super(tsfErrorConstant.getMessage());
        this.errCode = tsfErrorConstant.getCode();
    }

//	/**
//	 * @param errCode 异常码，用于运维追踪问题节点
//	 * */
//	public FaInsExcept(String errCode) {
//		super();
//		this.errCode = errCode;
//	}

    /**
     * @param errCode 异常码，用于运维追踪问题节点
     * @param message 异常信息
     */
    public FaInsExcept(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    /**
     * @param errCode 异常码，用于运维追踪问题节点
     * @param cause   异常链上一个节点
     */
    public FaInsExcept(String errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
    }

    /**
     * @param errCode 异常码，用于运维追踪问题节点
     * @param message 异常信息
     * @param cause   异常链上一个节点
     */
    public FaInsExcept(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }






}
