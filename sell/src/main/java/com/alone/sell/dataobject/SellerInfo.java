package com.alone.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chri61
 * @Date 2022/3/27 0027
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;
    private String password;
    private String openid;

}
