package com.integration.view.vo;

public class InsElement {

    /**
     * 险种名称
     */
    private String insuranceName;

    /**
     * 险种代码
     */
    private String insuranceCode;

    /**
     * 险种是否可选
     */
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
