package com.integration.server.repoService;


import com.integration.server.dto.KindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KindRepoService {


    KindDTO findByCodeAndState(String code, Boolean state);


    List<KindDTO> findByState(Boolean state);

    Page<KindDTO> findByState(Boolean state, Pageable pageable);

    Page<KindDTO> findByCodeAndState(String code, Boolean state, Pageable pageable);

    Page<KindDTO> findByNameLikeAndState(String name,Boolean state,Pageable pageable);

}
