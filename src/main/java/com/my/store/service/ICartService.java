package com.my.store.service;

/* 购物车业务层接口 */
public interface ICartService {

    /**
     * 将商品添加到购物车中
     *
     * @param uid      用户id
     * @param pid      商品id
     * @param amount   新增的数量
     * @param username 修改者
     */
    void addToCart(Integer uid,
                   Integer pid,
                   Integer amount,
                   String username);
}
