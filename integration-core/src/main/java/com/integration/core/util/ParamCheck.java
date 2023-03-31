package com.integration.core.util;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.excp.FaInsExcept;
import org.apache.commons.lang3.StringUtils;


import java.util.Objects;

/**
 * 参数校验
 * @author cyh
 */
public class ParamCheck {

    public static void notNull(Object obj,String errorMsg){
        if (Objects.isNull(obj)){
            throw new FaInsExcept(ErrorConstans.PARAM_CHECK_FAIL,errorMsg);
        }
    }

    public static void notBlank(String param,String errorMsg){
        if (StringUtils.isBlank(param)){
            throw new FaInsExcept(ErrorConstans.PARAM_CHECK_FAIL,errorMsg);
        }
    }
}
