package com.alone.sell.controller.manage;

import com.alone.sell.common.CookieConts;
import com.alone.sell.common.RedisConts;
import com.alone.sell.common.config.ProjectConfig;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.common.utils.CookieUtil;
import com.alone.sell.dataobject.SellerInfo;
import com.alone.sell.service.ISellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户相关
 *
 * @author Chri61
 * @Date 2022/3/27 0027
 */
@Slf4j
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private ISellerService iSellerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProjectConfig projectConfig;


    @GetMapping("login")
    public ModelAndView login(String openid, Map<String, Object> map, HttpServletResponse response) {
        log.info("【卖家登录页】用户登录，openid={}", openid);
        //1.openid去和数据库做匹配
        SellerInfo userinfo = iSellerService.findSellerInfoByOpenid(openid);
        if (userinfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        //2.设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConts.EXPIRE;
        //key，value，时间，时间单位
        stringRedisTemplate.opsForValue().set(String.format(RedisConts.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //3.设置token至cookie
        CookieUtil.set(response, CookieConts.TOKEN, token, expire);

        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("redirect:" + projectConfig.getSell() + "/sell/seller/order/list");
    }


    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        //1. 查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConts.TOKEN);
        if (cookie != null) {
            //2. 清除redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConts.TOKEN_PREFIX, cookie.getValue()));
            //3. 清除cookie
            CookieUtil.set(response, CookieConts.TOKEN, null, 0);
            map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/success", map);
        }
        return null;
    }

}
