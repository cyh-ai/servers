package com.integration.server.dto;

import com.integration.core.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cyh
 * 待补充类说明
 */
//5.DTO添加swagger注解ApiModel(模块名称)
@ApiModel(value = "险种DTO")
public class InsElementDTO {


    /**
     * 险种名称
     */
    //6.字段添加swagger注解ApiModelProperty(模块中字段名称)
    @ApiModelProperty(value = "险种名称")
    private String insuranceName;

    /**
     * 险种代码
     */
    @ApiModelProperty(value = "险种代码")
    private String insuranceCode;

    /**
     * 险种是否可选
     */
    @ApiModelProperty(value = "险种是否可选")
    private String insuranceIsOptional;

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getInsuranceCode() {
        return insuranceCode;
    }

    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }

    public String getInsuranceIsOptional() {
        return insuranceIsOptional;
    }

    public void setInsuranceIsOptional(String insuranceIsOptional) {
        this.insuranceIsOptional = insuranceIsOptional;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
