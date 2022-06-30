package com.alone.sell.aspect;

import com.alone.sell.common.CookieConts;
import com.alone.sell.common.RedisConts;
import com.alone.sell.common.exception.SellAuthorizeException;
import com.alone.sell.common.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * AOP 卖家身份验证
 *
 * @author Chri61
 * @Date 2022/3/30 0030
 */
@Slf4j
@Aspect
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //切点，后面的路径要精确到类里面的方法，并且不包括登录和登出
    @Pointcut("execution(public * com.alone.sell.controller.manage.Seller*.*(..))"
            + " && !execution(public * com.alone.sell.controller.manage.SellerUserController.*(..))")
    public void verify() {
    }


    //在进入这个方法之前的操作
    @Before("verify()")
    public void beforeVerify() {
        //获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //1. 查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConts.TOKEN);
        if (cookie == null) {
            //没有登录
            log.warn("【登录校验】cookie查不到token");
            throw new SellAuthorizeException();
        }
        //去redis里面查
        String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConts.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】redis查不到token");
            throw new SellAuthorizeException();
        }
    }
}
