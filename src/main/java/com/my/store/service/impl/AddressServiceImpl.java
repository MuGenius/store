package com.my.store.service.impl;

import com.my.store.controller.ex.AddressCountLimitException;
import com.my.store.entity.Address;
import com.my.store.mapper.AddressMapper;
import com.my.store.service.IAddressService;
import com.my.store.service.IDistrictService;
import com.my.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* 新增收货地址的实现类*/
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        // 调用收货地址统计方法
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用户收货地址超出上限");
        }

        // 对address对象中的数据进行补全：省市区
        String provinceNmae = districtService.getNameByCode(address.getProvinceCode());
        String cityNmae = districtService.getNameByCode(address.getCityCode());
        String areaNmae = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceNmae);
        address.setCityName(cityNmae);
        address.setAreaName(areaNmae);

        //uid、isDefault
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0; // 1 - 默认，0 - 不默认
        address.setIsDefault(isDefault);

        // 补全四项日志
        address.setCreated_user(username);
        address.setModified_user(username);
        address.setCreated_time(new Date());
        address.setModified_time(new Date());

        // 插入收货地址
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("新增地址时产生未知异常");
        }
    }

    @Override
    public List<Address> getAddressByUid(Integer uid) {
        List<Address> list = addressMapper.findAddressByUid(uid);

        for (Address address : list) {
            //address.setAid(null);
            //address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setCreated_user(null);
            address.setCreated_time(null);
            address.setModified_user(null);
            address.setModified_time(null);
        }
        return list;
        /*List<Address> result = addressMapper.findAddressByUid(uid);
        List<Address> list = new ArrayList<>();

        for (Address res : result) {
            Address address = new Address();
            address.setTag(res.getTag());
            address.setName(res.getName());
            address.setProvinceName(res.getProvinceName());
            address.setCityName(res.getCityName());
            address.setAreaName(res.getAreaName());
            address.setAddress(res.getAddress());
            address.setPhone(res.getPhone());
            address.setZip(res.getZip());

            list.add(address);
        }
        return list;*/
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findAddressByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        // 检测当前获取的收货地址数据的归属
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        // 先将所有收货地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
        // 将用户选中的地址设置为默认地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
    }

    @Override
    public void deleteAddress(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findAddressByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        // 检测当前获取的收货地址数据的归属
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer rows = addressMapper.deleteAddressByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除时产生未知异常");
        }

        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            // 直接终止程序
            return;
        }

        if (result.getIsDefault() == 0) {
            return;
        }

        // 将这条数据中is_default值设置为1
        Address address = addressMapper.findLastModifiedAddress(uid);
        rows = addressMapper.updateDefaultByAid(
                address.getAid(), username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }
}
