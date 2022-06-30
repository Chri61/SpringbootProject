package com.alone.sell.service.Impl;

import com.alone.sell.dataobject.SellerInfo;
import com.alone.sell.repository.SellerInfoRepository;
import com.alone.sell.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chri61
 * @Date 2022/3/27 0027
 */
@Service
public class SellerServiceImpl implements ISellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
