package com.alone.sell.controller;

import com.alone.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试用官方的网页授权获取openid
 * <p>
 * 第一步：用户同意授权，获取code
 * 第二步：通过code换取网页授权access_token
 *
 * @author Chri61
 * @Date 2022/3/20 0020
 */
@Slf4j
@RestController
@RequestMapping("weixin")
public class WeiXinController {


    @GetMapping("auth")
    public ResultVO auth(@RequestParam("code") String code) {
        log.info("进入了微信测试方法：code={}", code);

        //微信公众号平台测试号管理里面的
        String appID = "wx097a95dfbb31f2b1";
        String appsecret = "0076681044a0dee6aeef043428460885";

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appID +
                "&secret=" + appsecret +
                "&code=" + code + "&grant_type=authorization_code";

        //发起一个网络请求
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);
        log.info("使用code获取openid：{}", forObject);
        return ResultVO.success(forObject);
    }

}
