package com.my.store.mapper;

import com.my.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//@SpringBootTest: 当前类是测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith: 表示启动这个单元测试类
@RunWith(SpringRunner.class)
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(12);
        address.setPhone("12333333333");
        address.setName("java");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        System.out.println(addressMapper.countByUid(12));
    }

    @Test
    public void findByUid() {
        System.out.println(addressMapper.findAddressByUid(12));
    }

    @Test
    public void findAddressByAid() {
        System.out.println(addressMapper.findAddressByAid(5));
    }

    @Test
    public void updateNonDefault() {
        addressMapper.updateNonDefault(12);
    }

    @Test
    public void updateDefaultByAid() {
        addressMapper.updateDefaultByAid(5, "小明", new Date());
    }

    @Test
    public void deleteAddressByAid() {
        addressMapper.deleteAddressByAid(1);
    }

    @Test
    public void findLastModifiedAddress() {
        System.out.println(addressMapper.findLastModifiedAddress(12));
    }
}
