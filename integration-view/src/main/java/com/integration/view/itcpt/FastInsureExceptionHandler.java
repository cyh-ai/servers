package com.integration.view.itcpt;


import com.integration.core.excp.FaInsExcept;
import com.integration.core.excp.TokenExcept;
import com.integration.core.util.AesUtil;
import com.integration.core.util.JsonUtil;
import com.integration.core.util.LogUtis;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author cyh
 * 处理参数响应格式和响应结果
 */
@ControllerAdvice
@SuppressWarnings("rawtypes")
public class FastInsureExceptionHandler implements ResponseBodyAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 一个封装返回类，包含状态码，状态信息，具体数据。
     */
    private static final String RETURN_TPYE = "com.cpic.fastInsure.core.spec.StdResponse";
    /**
     * 控制是否加密
     */
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

    /**
     * 不加密方法(在yml文件中配置对应的方法名即可)
     */
    @Value("${aesDecode.unDecodeMethod}")
    private String[] unDecodeMethod;


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
            errMsg = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        } else if (e instanceof ResourceAccessException) {
            response.put("errCode", "0");
            logger.error("FastInsureExceptHandler got uncaught ResourceAccessException: ", e);
            errMsg = "系统有点拥挤，等下再试试吧";
        } else {
            response.put("errCode", "0");
            logger.error("FastInsureExceptHandler got uncaught exception ", e);
            //errMsg = "系统异常" + getSimpleExpMsg(e) + ":" + MDC.get("RequestNo"); ...
            errMsg = "代码报错！！！查看异常打印";
        }
        logger.error("start return error with {}", errMsg);
        response.put("errMsg", errMsg);
        return response;
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter parameter, MediaType mediaType, Class clazz, ServerHttpRequest req, ServerHttpResponse resp) {
        //parameter.getMethod().getReturnType().getName()  获取指定路径下封装的返回类
        if (parameter.getMethod() != null && StringUtils.equals(parameter.getMethod().getReturnType().getName(), RETURN_TPYE)) {
            logger.info("统一处理");
            return body;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("errCode", "1");
        String formatString = JsonUtil.toJsonContainEmpty(body);
        logger.info("start return response with {}", LogUtis.toLogStr(req,formatString));
        //String skipVal = req.getHeaders().getFirst("platformVal"); ...

        boolean notDecode = DO_NOT_DECODE.equals(doDecode) || ArrayUtils.contains(unDecodeMethod, parameter.getMethod().getName());
        //notDecode为false(yml文件中doDecode配置为1) 走加密，需用对应方法解密
        //unDecodeMethod为不加密的方法名，存在则不走加密
        response.put("responseBody", notDecode ? body == null ? null : JsonUtil.toObject(formatString, body.getClass()) : AesUtil.aesCbcPKCS5PaddingEncrypt(formatString, false));
        return response;
    }

    @Override
    public boolean supports(MethodParameter parameter, Class clazz) {
        if (null != parameter.getMethod()) {
            logger.info("beforeBodyWrite supports: {}", parameter.getMethod().getName());
        }
        return null == parameter.getMethod() || !ArrayUtils.contains(excludeMethod, parameter.getMethod().getName());
    }


}
