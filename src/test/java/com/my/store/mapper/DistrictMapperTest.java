package com.my.store.mapper;

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
public class DistrictMapperTest {

    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent() {
        List<District> districtList = districtMapper.findByParent("420000");
        for (District d : districtList) {
            System.out.println(d);
        }
    }

    @Test
    public void findNameByCode() {
        System.out.println(districtMapper.findNameByCode("420000"));
    }
}
