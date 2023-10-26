package com.integration.view.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cyh
 * 待补充类说明
 */
@ApiModel(description = "险种对象")
public class InsElement {

    /**
     * 险种名称
     */
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
}
