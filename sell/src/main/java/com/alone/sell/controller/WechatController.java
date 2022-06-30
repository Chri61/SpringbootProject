package com.alone.sell.controller;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.config.ProjectConfig;
import com.alone.sell.common.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 使用第三方SDK获取openid
 *
 * @author Chri61
 * @Date 2022/3/20 0020
 */
@Slf4j
@Controller
@RequestMapping("wechat")
public class WechatController {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private ProjectConfig projectConfig;


    @GetMapping("authorize")
    public String authorize(String returnUrl) throws UnsupportedEncodingException {
        //1.配置。已经在WachatMpConfig里面配置完成
        //2.调用方法
        //用户授权完成后的重定向链接
        String url = projectConfig.getWechatMpAuthorize() + "/sell/wechat/userInfo";
        String redirectURI = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl, "UTF-8"));
        log.info("【微信网页授权】重定向：{}" + redirectURI);
        //重定向
        return "redirect:" + redirectURI;
    }

    @GetMapping("userInfo")
    public String userInfo(String code, String state) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("【微信网页授权】", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openid={}", openId);
        return "redirect:" + state + "?openid=" + openId;
    }


    @GetMapping("qrAuthorize")
    public String qrAuthorize(String returnUrl) throws UnsupportedEncodingException {
        String url = projectConfig.getWechatOpenAuthoize() + "/sell/wechat/qr_user_info";
        log.error("【微信扫码登录页】没有公众号，GG了");
        String redirectURI = wxMpService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl, "UTF-8"));
        log.info("【微信网页扫码登录】重定向：{}" + redirectURI);
        //重定向
        return "redirect:" + redirectURI;
    }

    @GetMapping("qrUserInfo")
    public String qrUserInfo(String code, String state) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("【微信网页扫码登录】", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页扫码登录】openid={}", openId);
        return "redirect:" + state + "?openid=" + openId;
    }

}
