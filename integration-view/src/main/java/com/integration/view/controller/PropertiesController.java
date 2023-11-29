package com.integration.view.controller;

import com.integration.core.Enum.RedisKeyEnum;
import com.integration.core.commonService.PropertiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cyh
 * 系统配置测试
 */
@Api(tags = "系统配置查询")
@RestController
@RequestMapping("/properties")
public class PropertiesController {

    @Autowired
    PropertiesService propertiesService;


    @ApiOperation(value = "查询配置")
    @RequestMapping(value = "/property", method = RequestMethod.POST)
    @ResponseBody
    public Object property() {
        String oneByKey = propertiesService.findOneByKey("Properties", "cyh");
        System.out.println(oneByKey);
        Map<String,Object> map = new HashMap<>();
        map.put("cyh",oneByKey);
        return map;
        //return oneByKey;
    }
}
