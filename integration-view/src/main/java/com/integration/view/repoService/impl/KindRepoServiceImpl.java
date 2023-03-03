package com.integration.view.repoService.impl;

import com.integration.core.util.CopyUtil;

import com.integration.view.controller.dto.KindDTO;
import com.integration.view.eneity.Kind;
import com.integration.view.repoService.KindRepoService;
import com.integration.view.repository.KindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KindRepoServiceImpl implements KindRepoService {

    @Autowired
    KindRepository kindRepository;


    @Override
    public KindDTO findByCodeAndState(String code, Boolean state) {
        return CopyUtil.convert(kindRepository.findByCodeAndState(code,state),this::toDTO);
    }

    @Override
    public List<KindDTO> findByState(Boolean state) {
        return CopyUtil.convertList(kindRepository.findByState(state),this::toDTO);
    }

    @Override
    public Page<KindDTO> findByState(Boolean state, Pageable pageable) {
        return kindRepository.findByState(state,pageable).map(this::toDTO);
    }

    @Override
    public Page<KindDTO> findByCodeAndState(String code, Boolean state, Pageable pageable) {
        return null;
    }

    @Override
    public Page<KindDTO> findByNameLikeAndState(String name, Boolean state, Pageable pageable) {
        return null;
    }


    private KindDTO toDTO(Kind kind){
        return  CopyUtil.convert(kind,KindDTO.class);
    }
}
