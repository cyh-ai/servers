package com.integration.server.service;


import com.integration.server.dto.kind.KindDTO;
import com.integration.server.dto.kind.KindListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author cyh
 * 险种方法层
 */
public interface KindService {

    /**
     * 查询指定险种信息
     *
     * @param code  险种代码
     * @param state 状态
     * @return 对应险种信息
     */
    KindDTO findByCodeAndState(String code, Boolean state);

    /**
     * 查询险种信息集合
     *
     * @param state 险种状态
     * @return 险种集合
     */
    List<KindDTO> findByState(Boolean state);

    /**
     * 分页 险种信息
     *
     * @param state    险种状态
     * @param pageable 可分页
     * @return 分页险种数据
     */
    Page<KindDTO> findByState(Boolean state, Pageable pageable);


    /**
     * 查询险种信息 分页
     *
     * @param cyh       险种名称
     * @param pageIndex 下标
     * @param pageSize  数量
     * @return 险种信息
     */
    KindListResponseDTO findKindInfo(String cyh, String pageIndex, String pageSize);

}
