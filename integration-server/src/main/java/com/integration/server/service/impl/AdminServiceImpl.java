package com.integration.server.service.impl;

import com.integration.server.dto.KindListResponseDTO;
import com.integration.server.service.AdminService;
import com.integration.server.service.KindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    KindService kindService;

    @Override
    public KindListResponseDTO findKindInfo(String cyh, String pageIndex, String pageSize) {
        return kindService.findKindInfo(cyh,pageIndex,pageSize);
    }
}
