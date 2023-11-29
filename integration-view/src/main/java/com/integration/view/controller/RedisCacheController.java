package com.integration.view.controller;

import com.integration.server.service.RedisRegisterService;
import com.integration.view.vo.redisCache.ClearRedisCacheRequest;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cyh
 * 缓存维护
 */
@RestController
@RequestMapping("/redisCache")
@Api(tags = "缓存维护")
public class RedisCacheController {

    @Autowired
    RedisRegisterService redisRegisterService;



    @RequestMapping(method = RequestMethod.POST,value = "/clearRedisCacheCategory")
    public void clearRedisCacheCategory(@RequestBody ClearRedisCacheRequest request){
        redisRegisterService.clearRedisRegister(request.getCategoryKey(),request.getNameKey());
    }

}
