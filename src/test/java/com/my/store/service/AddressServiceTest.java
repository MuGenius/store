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
public class AddressServiceTest {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setPhone("11111111111");
        address.setName("JAVA");
        addressService.addNewAddress(12, "admin", address);
    }

    @Test
    public void getAddressByUid() {
        System.out.println(addressService.getAddressByUid(12));
    }

    @Test
    public void setDefault() {
        addressService.setDefault(4, 12, "张三");
    }

    @Test
    public void deleteAddress() {
        addressService.deleteAddress(2, 12, "test03");
    }
}
