package com.integration.view.controller;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.Enum.RedisKeyEnum;
import com.integration.core.excp.FaInsExcept;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cyh
 * 测试ctr
 */
@Api(tags = "测试控制类")
@RestController
@RequestMapping("/test")
public class TestController {



    @ApiOperation(value = "测试异常类")
    @RequestMapping(value = "/error", method = RequestMethod.POST)
    @ResponseBody
    public String redis(@RequestBody String cyh) {
        String name = "程亚辉";
        List<String> list = new ArrayList<>();
        list.add("张飞");
        list.add("张三");
        list.add("关于");
        list.add("关羽");
        list.add("李飞");
        list.add(cyh);
        if (list.contains(name)){
            System.out.println("业务逻辑开始");

        }else {
            throw new FaInsExcept(ErrorConstans.TEST_ERROR,"测试异常打印");
        }
        return "程亚辉成功调用";
    }
}
