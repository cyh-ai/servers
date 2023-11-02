package com.integration.server.repository;


import com.integration.core.Repository.BaseRepository;
import com.integration.server.eneity.InsuredForceCszRule;

import java.util.List;

/**
 * @author cyh
 * 查询配置数据
 */
public interface InsuredForceCszRuleRepository extends BaseRepository<InsuredForceCszRule, Long> {

    /**
     * 所有数据
     *
     * @return 所有数据
     */
    List<InsuredForceCszRule> findAllByOrderByModifyTime();
}
