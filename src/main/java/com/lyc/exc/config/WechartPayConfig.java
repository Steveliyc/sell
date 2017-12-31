package com.lyc.exc.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by lyc94 on 2017/12/25.
 */
@Component
public class WechartPayConfig {

    @Autowired
    private WeChartAccountConfig weChartAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService() {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(weChartAccountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(weChartAccountConfig.getMpAppSecret());
        wxPayH5Config.setMchId(weChartAccountConfig.getMchId());
        wxPayH5Config.setMchKey(weChartAccountConfig.getMchKey());
        wxPayH5Config.setKeyPath(weChartAccountConfig.getKeyPath());
        wxPayH5Config.setNotifyUrl(weChartAccountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
