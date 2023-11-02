package com.integration.view.vo.kind;

import com.integration.core.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cyh
 * 险种请求对象
 */
@ApiModel(description = "险种请求对象")
public class KindListRequest {

    @ApiModelProperty(value = "险种名称")
    private String cyh;

    @ApiModelProperty(value = "下标")
    private String pageIndex;

    @ApiModelProperty(value = "数量")
    private String pageSize;

    public String getCyh() {
        return cyh;
    }

    public void setCyh(String cyh) {
        this.cyh = cyh;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
