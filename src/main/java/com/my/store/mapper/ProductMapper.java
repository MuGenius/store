package com.my.store.mapper;

import com.my.store.entity.Product;

import java.util.List;

/* 处理商品数据业务层接口 */
public interface ProductMapper {

    /**
     * 查询热销商品前四名
     *
     * @return 热销商品前四名集合
     */
    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品id
     * @return 匹配的商品详情，没有匹配则返回null
     */
    Product findProductById(Integer id);
}
