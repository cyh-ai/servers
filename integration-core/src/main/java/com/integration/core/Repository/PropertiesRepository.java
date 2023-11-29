package com.integration.core.Repository;


import com.integration.core.entity.Properties;

/**
 * @author cyh
 */
public interface PropertiesRepository extends BaseRepository<Properties,Long> {

    Properties findByKeyAndEnvironment(String key,String environment);
}
