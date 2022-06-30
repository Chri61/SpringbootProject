package com.alone.sell.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信账号相关的信息
 *
 * @author Chri61
 * @Date 2022/3/20 0022
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAcountConfig {

    //公众号里面的
    private String mpAppId;
    private String mpAppSecret;

    //开放平台里面的
    private String openAppID;
    private String openAppSecret;

    //商户号
    private String mchId;
    //商户密钥
    private String mchKey;
    //商户证书路径
    private String keyPath;
    //微信支付异步通知地址
    private String notifyUrl;

    //模板
    private OrderStatusTemplate templateId;
}

