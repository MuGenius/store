package com.my.store.service;

import com.my.store.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品id
     * @return 匹配的商品详情，如果没有则返回null
     */
    Product findProductById(Integer id);
}
