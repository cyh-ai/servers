package com.integration.view.vo.insuredForceCszRule;

/**
 * @author cyh
 * 导出方法入参
 */
public class ExportCszRuleReq {

    /**
     * 分公司
     */
    private String fgsCode;

    /**
     * 中支公司
     */
    private String zzgsCode;

    public String getFgsCode() {
        return fgsCode;
    }

    public void setFgsCode(String fgsCode) {
        this.fgsCode = fgsCode;
    }

    public String getZzgsCode() {
        return zzgsCode;
    }

    public void setZzgsCode(String zzgsCode) {
        this.zzgsCode = zzgsCode;
    }
}
