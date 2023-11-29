package com.integration.server.service.impl;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.excp.FaInsExcept;
import com.integration.core.util.CollectionUtils;
import com.integration.core.util.ExcelUtils;
import com.integration.server.dto.insuredForceCszRule.InsuredForceCszRuleDTO;
import com.integration.server.dto.insuredForceCszRule.InsuredForceCszRuleExportTemplate;
import com.integration.server.repoService.InsuredForceCszRuleRepoService;
import com.integration.server.service.InsuredForceCszRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cyh
 * 对应配置数据接口实现类
 */
@Service
public class InsuredForceCszRuleServiceImpl implements InsuredForceCszRuleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    InsuredForceCszRuleRepoService insuredForceCszRuleRepoService;


    @Override
    public List<InsuredForceCszRuleDTO> findAllByOrderByModifyTime() {
        return insuredForceCszRuleRepoService.findAllByOrderByModifyTime();
    }

    /**
     * 导出数据
     *
     * @param fgsCode  分公司
     * @param response resp
     */
    @Override
    public void exportForceRules(String fgsCode, HttpServletResponse response) {
        List<InsuredForceCszRuleDTO> allByOrderByModifyTime = insuredForceCszRuleRepoService.findAllByOrderByModifyTime();
        if (CollectionUtils.isEmpty(allByOrderByModifyTime)) {
            throw new FaInsExcept(ErrorConstans.ADMIN_COMMON_EXCP, "暂无数据");
        }
        try {
            List<InsuredForceCszRuleExportTemplate> templateInfoList = new ArrayList<>();
            allByOrderByModifyTime.forEach(result -> {
                InsuredForceCszRuleExportTemplate template = new InsuredForceCszRuleExportTemplate();
                // BeanUtils.copyProperties(有数据的,被填充的)
                BeanUtils.copyProperties(result, template);
                template.setIsCheckAnti("1".equals(template.getIsCheckAnti()) ? "是" : "否");
                templateInfoList.add(template);
            });
            ExcelUtils.export(response, "配置数据", templateInfoList, InsuredForceCszRuleExportTemplate.class);
        } catch (Exception e) {
            logger.error("配置导出异常:", e);
            throw new FaInsExcept(ErrorConstans.ADMIN_COMMON_EXCP, "配置导出失败");
        }
    }
}
