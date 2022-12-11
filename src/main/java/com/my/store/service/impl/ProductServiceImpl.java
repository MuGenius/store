package com.my.store.service.impl;

import com.my.store.entity.Product;
import com.my.store.mapper.ProductMapper;
import com.my.store.service.IProductService;
import com.my.store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> list = productMapper.findHotList();

        for (Product product : list) {
            product.setPriority(null);
            product.setCreated_user(null);
            product.setCreated_time(null);
            product.setModified_user(null);
            product.setModified_time(null);
        }
        return list;
    }

    @Override
    public Product findProductById(Integer id) {
        Product product = productMapper.findProductById(id);
        // 判断数据是否为null
        if (product == null) {
            throw new ProductNotFoundException("商品数据不存在");
        }
        // 将查询结果中的部分属性设置为null
        product.setPriority(null);
        product.setCreated_user(null);
        product.setCreated_time(null);
        product.setModified_user(null);
        product.setModified_time(null);

        return product;
    }
}
