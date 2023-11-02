package com.integration.view.controller;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.excp.FaInsExcept;
import com.integration.server.dto.insuredForceCszRule.InsuredForceCszRuleDTO;
import com.integration.server.service.InsuredForceCszRuleService;
import com.integration.view.vo.insuredForceCszRule.ExportCszRuleReq;
import com.integration.view.vo.insuredForceCszRule.InsuredForceCszRuleRequest;
import com.integration.view.vo.insuredForceCszRule.InsuredForceCszRuleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cyh
 * 配置数据查询控制类
 */
@Api(tags = "配置查询")
@RestController
@RequestMapping("/insuredForce")
public class InsuredForceCszRuleController {

    @Autowired
    InsuredForceCszRuleService insuredForceCszRuleService;


    @ApiOperation(value = "获取配置列表")
    @RequestMapping(method = RequestMethod.POST, value = "/findInsuredForce")
    @ApiImplicitParams({@ApiImplicitParam(name = "insuredForceCszRuleRequest", value = "InsuredForceCszRuleRequest",
            required = true, dataType = "InsuredForceCszRuleRequest", dataTypeClass = InsuredForceCszRuleRequest.class)})
    public InsuredForceCszRuleResponse findInsuredForce(@RequestBody @Validated InsuredForceCszRuleRequest insuredForceCszRuleRequest) {
        if (insuredForceCszRuleRequest == null) {
            throw new FaInsExcept(ErrorConstans.PARAM_CHECK_FAIL, "入参不能为空");
        }
        InsuredForceCszRuleResponse insuredForceCszRuleResponse = new InsuredForceCszRuleResponse();
        List<InsuredForceCszRuleDTO> allByOrderByModifyTime = insuredForceCszRuleService.findAllByOrderByModifyTime();
        insuredForceCszRuleResponse.setList(allByOrderByModifyTime);
        return insuredForceCszRuleResponse;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation("导出配置")
    public void exportForbidRules(@RequestBody @Validated ExportCszRuleReq exportCszRuleReq, HttpServletResponse response) {
        insuredForceCszRuleService.exportForceRules(exportCszRuleReq.getFgsCode(), response);
    }


}
