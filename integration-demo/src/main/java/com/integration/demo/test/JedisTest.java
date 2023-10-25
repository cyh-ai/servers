package com.integration.demo.test;



import com.integration.view.util.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.ResourceBundle;

/**
 * @author cyh
 */
public class JedisTest {

    public static void main(String[] args) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.auth(ResourceBundle.getBundle("redis").getString("redis.auth"));

        jedis.sadd("key1","abc","aaa","def");
        long key1 = jedis.scard("key1");
        System.out.println("运行结果："+key1);

        jedis.close();
    }
}
