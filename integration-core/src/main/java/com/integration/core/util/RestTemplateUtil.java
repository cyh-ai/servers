package com.integration.core.util;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.excp.FaInsExcept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author cyh
 */
@Component
public class RestTemplateUtil {

//    @Autowired
//    private RestTemplate restTemplate;


    private void checkSchema(String url){
        if (url.startsWith("http://")||url.startsWith("https://")){
            return;
        }
        throw new FaInsExcept(ErrorConstans.PROTOCOL_ANOMALY,"只支持HTTP或HTTPS协议");
    }
}
