package com.integration.core.reposervice.support;

import com.integration.core.util.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyh
 * 增加repoService抽象类，统一实现Entity转DTO,List<Entity>转List<DTO>
 */
public abstract class SimpleRepoServiceSupport<D, E> {

    private final Class<D> domainClazz;

    public SimpleRepoServiceSupport(Class<D> domainClazz) {
        this.domainClazz = domainClazz;
    }

    /**
     * entity转换为DTO
     *
     * @param entity 实体类
     * @return DTO
     */
    public D populateDTO(E entity) {
        if (entity == null) {
            return null;
        }
        try {
            D dto = this.domainClazz.newInstance();
            // BeanUtils.copyProperties(有数据的,被填充的)
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (IllegalAccessException | InstantiationException e) {
            return null;
        }
    }


    public List<D> populateDTOList(List<E> entitys){
        List<D> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(entitys)){
            entitys.forEach(order->{
                list.add(populateDTO(order));
            });
        }
        return list;
    }
}
