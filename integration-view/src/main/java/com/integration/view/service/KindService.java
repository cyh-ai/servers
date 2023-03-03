package com.integration.view.service;


import com.integration.view.controller.dto.KindDTO;
import com.integration.view.controller.dto.KindListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KindService {


    KindDTO findByCodeAndState(String code, Boolean state);


    List<KindDTO> findByState(Boolean state);

    Page<KindDTO> findByState(Boolean state, Pageable pageable);


    KindListResponseDTO findKindInfo(String cyh, String pageIndex, String pageSize);

}
