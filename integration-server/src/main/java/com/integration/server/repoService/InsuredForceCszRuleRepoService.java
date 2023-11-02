package com.integration.server.repoService;

import com.integration.server.dto.insuredForceCszRule.InsuredForceCszRuleDTO;

import java.util.List;

/**
 * @author cyh
 * 对应配置repo层
 */
public interface InsuredForceCszRuleRepoService {

    /**
     * 查询所有配置信息
     *
     * @return 配置信息
     */
    List<InsuredForceCszRuleDTO> findAllByOrderByModifyTime();
}
