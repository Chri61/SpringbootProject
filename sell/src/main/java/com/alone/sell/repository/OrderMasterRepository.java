package com.alone.sell.repository;

import com.alone.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Chri61
 * @Date 2021/12/30 0030
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    //根据买家openid来查
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);


}
