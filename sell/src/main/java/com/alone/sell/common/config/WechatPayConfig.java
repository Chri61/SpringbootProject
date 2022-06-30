package com.alone.sell.common.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信支付 的配置项
 *
 * @author Chri61
 * @Date 2022/1/10 0010
 */
@Component
public class WechatPayConfig {

    @Autowired
    private WechatAcountConfig wechatAcountConfig;

    @Bean
    public BestPayServiceImpl bestPayService() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAcountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(wechatAcountConfig.getMpAppSecret());
        wxPayH5Config.setMchId(wechatAcountConfig.getMchId());
        wxPayH5Config.setMchKey(wechatAcountConfig.getMchKey());
        wxPayH5Config.setKeyPath(wechatAcountConfig.getKeyPath());
        wxPayH5Config.setNotifyUrl(wechatAcountConfig.getNotifyUrl());

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;
    }
}
