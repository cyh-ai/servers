package com.integration.view.controller;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.Enum.RedisKeyEnum;
import com.integration.core.excp.FaInsExcept;
import com.integration.core.service.RedisLock;
import com.integration.core.service.RedisService;
import com.integration.server.dto.InsElementDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author cyh
 * 待补充类说明
 */
@Api(tags = "redis接口测试控制类")
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    RedisService redisService;

    @ApiOperation(value = "存redis")
    @RequestMapping(value = "/redis", method = RequestMethod.POST)
    @ResponseBody
    public void redis() {
        //存redis
        String key = redisService.generateKey(RedisKeyEnum.CYH.getBusiness(), RedisKeyEnum.CYH.getSubBusiness(), "redisCyh");

        redisService.set(key, 1, 1L, TimeUnit.DAYS);
    }

    @ApiOperation(value = "获取redis缓存内容")
    @RequestMapping(value = "/redis2", method = RequestMethod.POST)
    @ResponseBody
    public void redis2() {
        //获取redis缓存内容
        String key = redisService.generateKey(RedisKeyEnum.CYH.getBusiness(), RedisKeyEnum.CYH.getSubBusiness(), "redisCyh");

        Integer o = (Integer) redisService.get(key);
        System.out.println(o);
        System.out.println("----------------------测试key+1---------------------");
        //测试，以增量方式存储long值
        Long incr = redisService.incr(key);
        System.out.println(incr);
    }


    @ApiOperation(value = "控制接口重复调用")
    @RequestMapping(value = "/redis3", method = RequestMethod.POST)
    @ResponseBody
    public void redis3() throws InterruptedException {
        //控制接口重复调用
        String name = "ABC";
        String lockKey = "cyh:" + name;
        //根据redis锁，来控制接口重复调用
        RedisLock redisLock = redisService.tryLock(lockKey, 100L);
        if (redisLock.successGet()) {
            try {

                //模拟接口为调用结束
                Thread.sleep(10000);
                System.out.println("123456");
            } finally {
                redisLock.releaseLock();
            }
        } else {
            logger.error("接口重复调用请求,name:{}", name);
            System.out.println("重复调用了");
            throw new FaInsExcept(ErrorConstans.REPEAT_REQUEST, "正在调用，请勿重复调用");
        }
    }

    @ApiOperation(value = "测试多key组合存取缓存数据")
    @RequestMapping(value = "/redis4", method = RequestMethod.POST)
    @ResponseBody
    public void redis4() {
        //测试多key组合存取缓存数据

        InsElementDTO insElementDTO = new InsElementDTO();
        insElementDTO.setInsuranceCode("123456789");
        insElementDTO.setInsuranceName("产品中国红");
        insElementDTO.setInsuranceIsOptional("1");
        redisService.set(RedisKeyEnum.DOUBLE, insElementDTO, insElementDTO.getInsuranceCode(), insElementDTO.getInsuranceName());

        System.out.println("-----------保存完成---------------");

        InsElementDTO insElement = (InsElementDTO) redisService.get(RedisKeyEnum.DOUBLE, insElementDTO.getInsuranceCode(), insElementDTO.getInsuranceName());
        System.out.println(insElement);
    }


    @ApiOperation(value = "测试map集合的缓存存取")
    @RequestMapping(value = "/redis5", method = RequestMethod.POST)
    @ResponseBody
    public void redis5() {
        //测试map集合的缓存存取
        Map<Object, Object> map = new HashMap<>();
        List<InsElementDTO> list = new ArrayList<>();
        InsElementDTO insElementDTO = new InsElementDTO();
        insElementDTO.setInsuranceCode("123456789");
        insElementDTO.setInsuranceName("产品中国红");
        insElementDTO.setInsuranceIsOptional("1");
        list.add(insElementDTO);
        map.put("cyh", list);
        redisService.hSetAll(RedisKeyEnum.MAP, map, RedisKeyEnum.MAP.getTime(), RedisKeyEnum.MAP.getTimeUnit(), "mapCyh", "AAA");

        System.out.println("-----------保存完成map---------------");

        Map<Object, Object> insElements = redisService.entries(RedisKeyEnum.MAP, "mapCyh", "AAA");

        System.out.println(insElements);
        List<InsElementDTO> cyh = (List<InsElementDTO>) insElements.get("cyh");
        System.out.println(cyh);
        InsElementDTO elementDTO = cyh.get(0);


        System.out.println("--------------------------------------------分割线----------------------------------------------------");


        boolean all = redisService.hSetAll(RedisKeyEnum.MAP, map);
        System.out.println(all);

        System.out.println("-----------保存完成map---------------");

        Map<Object, Object> entries = redisService.entries(RedisKeyEnum.MAP);
        System.out.println(entries);
        List<InsElementDTO> dtoList = (List<InsElementDTO>) entries.get("cyh");
        System.out.println(dtoList);
        InsElementDTO dto = dtoList.get(0);

    }
}
