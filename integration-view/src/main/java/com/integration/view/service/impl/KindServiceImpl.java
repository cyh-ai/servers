package com.integration.view.service.impl;


import com.google.common.collect.Lists;
import com.integration.core.util.CollectionUtils;


import com.integration.view.controller.dto.InsElementDTO;
import com.integration.view.controller.dto.KindDTO;
import com.integration.view.controller.dto.KindListResponseDTO;
import com.integration.view.repoService.KindRepoService;
import com.integration.view.service.KindService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 缓存和判断
 */
@Service
public class KindServiceImpl implements KindService {

    @Autowired
    private KindRepoService kindRepoService;



    @Override
    public KindDTO findByCodeAndState(String code, Boolean state) {

        return null;
    }

    @Override
    public List<KindDTO> findByState(Boolean state) {
        return kindRepoService.findByState(state);
    }

    @Override
    public Page<KindDTO> findByState(Boolean state, Pageable pageable) {
        return kindRepoService.findByState(state,pageable);
    }






    @Override
    public KindListResponseDTO findKindInfo(String cyh, String pageIndex, String pageSize) {
        KindListResponseDTO userListResponseDTO = new KindListResponseDTO();
        Page<KindDTO> kindList = Page.empty();
        Pageable pageable = PageRequest.of(Integer.parseInt(pageIndex) - 1, Integer.parseInt(pageSize));
        if (StringUtils.isBlank(cyh)) {
            //调用查询全部 pageable
            kindList = kindRepoService.findByState(true,pageable);
        } else if (StringUtils.isNumeric(cyh)) {
            //查询指定条件
            KindDTO kindDTO = kindRepoService.findByCodeAndState(cyh,true);
            if (kindDTO != null) {
                kindList = new PageImpl<>(Lists.newArrayList(kindDTO));
            }
        } else {
            //根据名称模糊搜索 pageable
            kindList = kindRepoService.findByNameLikeAndState("%"+cyh+"%",true,pageable);
        }
        if (CollectionUtils.isNotEmpty(kindList)) {
            String totalNum = String.valueOf(kindList.getTotalElements());
            List<InsElementDTO> insElementDTOS = kindList.stream().map(kindDTO -> {
                InsElementDTO u = new InsElementDTO();
                u.setInsuranceCode(kindDTO.getCode());
                u.setInsuranceName(kindDTO.getName());
                return u;
            }).collect(Collectors.toList());
            userListResponseDTO.setTotalNum(totalNum);
            userListResponseDTO.setInsuranceDTOList(insElementDTOS);
        }
        return userListResponseDTO;
    }
}
