package com.integration.server.service;

import com.integration.server.dto.kind.KindListResponseDTO;

/**
 * @author cyh
 * <p>
 * 管理调用接口
 */
public interface AdminService {


    /**
     * 险种信息
     *
     * @param cyh       险种名称
     * @param pageIndex 下标
     * @param pageSize  数量
     * @return 对应险种信息
     */
    KindListResponseDTO findKindInfo(String cyh, String pageIndex, String pageSize);
}
