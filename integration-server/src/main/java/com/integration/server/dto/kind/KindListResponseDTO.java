package com.integration.server.dto.kind;


import com.integration.core.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author cyh
 * 险种响应DTO对象
 */
@ApiModel(description = "险种响应DTO对象")
public class KindListResponseDTO {

    @ApiModelProperty(value = "页数")
    private String totalNum;

    @ApiModelProperty(value = "险种集合DTO")
    private List<InsElementDTO> insuranceDTOList;

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public List<InsElementDTO> getInsuranceDTOList() {
        return insuranceDTOList;
    }

    public void setInsuranceDTOList(List<InsElementDTO> insuranceDTOList) {
        this.insuranceDTOList = insuranceDTOList;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
