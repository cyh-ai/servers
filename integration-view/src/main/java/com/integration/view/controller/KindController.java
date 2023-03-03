package com.integration.view.controller;


import com.integration.core.util.CollectionUtils;
import com.integration.view.controller.dto.InsElementDTO;
import com.integration.view.controller.dto.KindListResponseDTO;
import com.integration.view.controller.vo.InsElement;
import com.integration.view.controller.vo.KindListRequest;
import com.integration.view.controller.vo.KindListResponse;
import com.integration.view.service.KindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cyh
 * @date 2023.02.27
 * 测试对接vue
 */
@RestController
@RequestMapping("/kind")
public class KindController {

    @Autowired
    KindService kindService;

    @RequestMapping(method = RequestMethod.POST, value = "/findKindInfo")
    public KindListResponse findKindInfo(@Validated KindListRequest kindListRequest) {
        KindListResponse kindListResponse = new KindListResponse();
        KindListResponseDTO info = kindService.findKindInfo(kindListRequest.getCyh(), kindListRequest.getPageIndex(), kindListRequest.getPageSize());
        List<InsElementDTO> kindList = info.getInsuranceDTOList();
        List<InsElement> insElements;
        if (CollectionUtils.isNotEmpty(kindList)) {
            insElements = kindList.stream().map(insElementDTO -> {
                InsElement insElement = new InsElement();
                insElement.setInsuranceCode(insElementDTO.getInsuranceCode());
                insElement.setInsuranceName(insElementDTO.getInsuranceName());
                return insElement;
            }).collect(Collectors.toList());
            kindListResponse.setInsuranceList(insElements);
            kindListResponse.setTotalNum(info.getTotalNum());
        } else {
            kindListResponse.setTotalNum("0");
        }
        return kindListResponse;
    }

}
