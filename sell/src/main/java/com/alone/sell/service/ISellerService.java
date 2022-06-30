package com.alone.sell.service;

import com.alone.sell.dataobject.SellerInfo;

/**
 * @author Chri61
 * @Date 2022/3/27 0027
 */
public interface ISellerService {

    SellerInfo findSellerInfoByOpenid(String openid);

}
