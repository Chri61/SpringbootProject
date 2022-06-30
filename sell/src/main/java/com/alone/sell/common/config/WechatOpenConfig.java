package com.alone.sell.common.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信开放平台 配置项
 *
 * @author Chri61
 * @Date 2022/3/27 0027
 */
@Component
public class WechatOpenConfig {

    @Autowired
    private WechatAcountConfig wechatAcountConfig;

    @Bean
    public WxMpService wxOpenService() {
        WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(wechatAcountConfig.getOpenAppID());
        wxMpDefaultConfig.setSecret(wechatAcountConfig.getOpenAppSecret());

        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpDefaultConfig);

        return wxMpService;
    }

}
