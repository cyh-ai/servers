package com.integration.server.repository;


import com.integration.core.Repository.BaseRepository;
import com.integration.server.eneity.Kind;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KindRepository extends BaseRepository<Kind,Long> {

    Kind findByCodeAndState(String code, Boolean state);


    List<Kind> findByState(Boolean state);

    Page<Kind> findByState(Boolean state, Pageable pageable);


    Page<Kind> findByCodeAndState(String code,Boolean state,Pageable pageable);

    /**
     * 1111
     *
     * @param name 4
     * @param state 4
     * @param pageable 1
     * @return 1
     */
    Page<Kind> findByNameLikeAndState(String name,Boolean state,Pageable pageable);


}