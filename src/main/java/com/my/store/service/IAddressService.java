package com.my.store.service;

import com.my.store.entity.Address;

import java.util.List;

/* 收货地址业务层接口 */
public interface IAddressService {

    void addNewAddress(Integer uid, String username, Address address);

    List<Address> getAddressByUid(Integer uid);

    /**
     * 修改某个用户的某条收货地址为默认收货地址
     *
     * @param aid      地址aid
     * @param uid      用户uid
     * @param username 修改人
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除用户选中的收货地址
     *
     * @param aid      收货地址id
     * @param uid      用户id
     * @param username 用户名
     */
    void deleteAddress(Integer aid, Integer uid, String username);
}
