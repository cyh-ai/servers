package com.integration.server.repoService.impl;

import com.integration.core.util.CopyUtil;
import com.integration.server.dto.insuredForceCszRule.InsuredForceCszRuleDTO;
import com.integration.server.eneity.InsuredForceCszRule;
import com.integration.server.repoService.InsuredForceCszRuleRepoService;
import com.integration.server.repository.InsuredForceCszRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cyh
 * 对应配置repo实现层
 */
@Service
public class InsuredForceCszRuleRepoServiceImpl implements InsuredForceCszRuleRepoService {

    @Autowired
    InsuredForceCszRuleRepository insuredForceCszRuleRepository;


    @Override
    public List<InsuredForceCszRuleDTO> findAllByOrderByModifyTime() {
        return CopyUtil.convertList(insuredForceCszRuleRepository.findAllByOrderByModifyTime(), this::toDTO);
    }


    /**
     * 转换
     *
     * @param insuredForceCszRule entity对象
     * @return DTO对象
     */
    private InsuredForceCszRuleDTO toDTO(InsuredForceCszRule insuredForceCszRule) {
        return CopyUtil.convert(insuredForceCszRule, InsuredForceCszRuleDTO.class);
    }
}
