package com.alone.sell.common.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信公众号平台 配置项
 *
 * @author Chri61
 * @Date 2022/3/20 0021
 */
@Component
public class WechatMpConfig {

    @Autowired
    private WechatAcountConfig wechatAcountConfig;

    public WxMpService wxMpService() {
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    //获取配置
    private WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(wechatAcountConfig.getMpAppId());
        wxMpDefaultConfig.setSecret(wechatAcountConfig.getMpAppSecret());
        return wxMpDefaultConfig;
    }

}
