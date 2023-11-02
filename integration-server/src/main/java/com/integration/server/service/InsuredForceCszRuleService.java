package com.integration.server.service;

import com.integration.server.dto.insuredForceCszRule.InsuredForceCszRuleDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cyh
 * 对应配置数据接口
 */
public interface InsuredForceCszRuleService {

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    List<InsuredForceCszRuleDTO> findAllByOrderByModifyTime();

    /**
     * 导出
     *
     * @param fgsCode  分公司
     * @param response resp
     */
    void exportForceRules(String fgsCode, HttpServletResponse response);
}
