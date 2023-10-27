package com.integration.view.itcpt;


import com.integration.core.excp.FaInsExcept;
import com.integration.core.excp.TokenExcept;
import com.integration.core.util.AesUtil;
import com.integration.core.util.JsonUtil;
import com.integration.core.util.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cyh
 * 待补充类说明
 */
@ControllerAdvice
@SuppressWarnings("rawtypes")
public class FastInsureExceptionHandler implements ResponseBodyAdvice {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String RETURN_TPYE = "com.cpic.fastInsure.core.spec.StdResponse";//add+ 20220823
    private static final String DO_NOT_DECODE = "0";


    /**
     * 控制是否走加密
     */
    @Value("${aesDecode.doDecode}")
    private String doDecode;

    /**
     * 需要走格式化响应参数的方法(在yml文件中配置对应的方法名即可)
     */
    @Value("${aesDecode.excludeMethod}")
    private String[] excludeMethod;


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Object handleException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        String errMsg = e.getMessage();
        if (e instanceof FaInsExcept) {
            FaInsExcept ex = (FaInsExcept) e;
            response.put("errCode", ex.getErrCode());
            logger.info("FastInsureExceptHandler got caught FaInsExcept: FaInsExcept code:{}, message:{}", ex.getErrCode(), ex.getMessage());
        } else if (e instanceof TokenExcept) {
            TokenExcept ex = (TokenExcept) e;
            response.put("errCode", ex.getErrCode());
            response.put("errType", ex.getErrType());
        } else if (e instanceof BindException) {
            response.put("errCode", "0");
            BindException ex = (BindException) e;
            //logger.error(JsonUtil.toJson(((BindException) e).getBindingResult()));
            errMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        } else if (e instanceof MethodArgumentNotValidException) {
            response.put("errCode", "0");
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            //logger.error(JsonUtil.toJson(((MethodArgumentNotValidException) e).getBindingResult()));
            errMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        } else if (e instanceof ResourceAccessException) {
            response.put("errCode", "0");
            logger.error("FastInsureExceptHandler got uncaught ResourceAccessException: ", e);
            errMsg = "系统有点拥挤，等下再试试吧";
        } else {
            response.put("errCode", "0");
            logger.error("FastInsureExceptHandler got uncaught exception ", e);
            //errMsg = "系统异常" + getSimpleExpMsg(e) + ":" + MDC.get("RequestNo");
            errMsg = "请上报运维工单处理";
        }
        logger.error("start return error with {}", errMsg);
        response.put("errMsg", errMsg);
        return response;
    }

    private String getSimpleExpMsg(Exception e) {
        try {
            String msg = e.toString();
            if (msg.indexOf(":") > -1)
                msg = msg.substring(0, msg.indexOf(":"));

            int i = msg.lastIndexOf(".");
            if (i > -1 && i < msg.length())
                msg = msg.substring(msg.lastIndexOf("."));
            return msg;
        } catch (Exception ex) {
            return ex.getLocalizedMessage();
        }
    }

    @Override
    public Object beforeBodyWrite(Object arg0, MethodParameter m, MediaType arg2, Class arg3, ServerHttpRequest req, ServerHttpResponse resp) {
        if (m == null || (m != null && m.getMethod() != null && StringUtils.equals(m.getMethod().getReturnType().getName(), RETURN_TPYE))) {
            logger.info("统一处理");
            return arg0;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("errCode", "1");
        String formatString = JsonUtil.toJsonContainEmpty(arg0);
        logger.info("start return response with {}", formatString);
        String skipVal = req.getHeaders().getFirst("platformVal");

        boolean notDecode = DO_NOT_DECODE.equals(doDecode);
        //notDecode为false(yml文件中doDecode配置为1) 走加密，需用对应方法解密
        response.put("responseBody", notDecode ? arg0 == null ? arg0 : JsonUtil.toObject(formatString, arg0.getClass()) : AesUtil.aesCbcPKCS5PaddingEncrypt(formatString, false));
        return response;
    }

    @Override
    public boolean supports(MethodParameter arg0, Class arg1) {
        if (null != arg0 && null != arg0.getMethod()) {
            logger.info("beforeBodyWrite supports: {}", arg0.getMethod().getName());
        }
        return null == arg0 || null == arg0.getMethod() || !ArrayUtils.contains(excludeMethod, arg0.getMethod().getName());
    }


}
