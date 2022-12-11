package com.my.store.service;

import com.my.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest: 当前类是测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith: 表示启动这个单元测试类
@RunWith(SpringRunner.class)
public class CartServiceTest {

    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        // 更新
        cartService.addToCart(12, 10000010, 2, "test03");
        // 新增
        cartService.addToCart(12, 10000011, 10, "test03");
    }
}
