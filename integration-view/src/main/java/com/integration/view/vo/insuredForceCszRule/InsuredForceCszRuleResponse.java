package com.integration.view.vo.insuredForceCszRule;

import com.integration.server.dto.insuredForceCszRule.InsuredForceCszRuleDTO;

import java.util.List;

/**
 * @author cyh
 * 响应类
 */
public class InsuredForceCszRuleResponse {

    private List<InsuredForceCszRuleDTO> list;

    public List<InsuredForceCszRuleDTO> getList() {
        return list;
    }

    public void setList(List<InsuredForceCszRuleDTO> list) {
        this.list = list;
    }
}
