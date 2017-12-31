package com.lyc.exc.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lyc94 on 2017/12/23.
 */
@RestController
@RequestMapping(value = "/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam(name = "code")String code,
                     @RequestParam(name = "state")String state) {
        log.info("code={}",code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx09fbad7a8e79d69a&secret=SECRET&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}",response);
    }
}
