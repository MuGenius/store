package com.my.store.service;

import com.my.store.entity.Address;
import com.my.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@SpringBootTest: 当前类是测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith: 表示启动这个单元测试类
@RunWith(SpringRunner.class)
public class DistrictServiceTest {

    @Autowired
    private IDistrictService districtService;

    @Test
    public void getByParent() {
        List<District> list = districtService.getByParent("86");
        for (District district : list) {
            System.out.println(district);
        }
    }
}
