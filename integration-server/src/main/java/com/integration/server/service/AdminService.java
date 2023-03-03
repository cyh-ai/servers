package com.integration.server.service;

import com.integration.server.dto.KindListResponseDTO;

public interface AdminService {



    KindListResponseDTO findKindInfo(String cyh, String pageIndex, String pageSize);
}
