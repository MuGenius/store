package com.my.store.mapper;

import com.my.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/* 收货地址持久层接口 */
public interface AddressMapper {

    /**
     * 插入用户收货地址
     *
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户id统计收货地址数量
     *
     * @param uid 用户uid
     * @return 当前用户收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户id查询用户收货地址数据
     *
     * @param uid 用户id
     * @return 收货地址数据
     */
    List<Address> findAddressByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     *
     * @param aid 收货地址aid
     * @return 收货地址数据，如果没有找到则返回null
     */
    Address findAddressByAid(Integer aid);

    /**
     * 根据用户的uid修改用户收货地址为非默认
     *
     * @param uid 用户uid
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);


    Integer updateDefaultByAid(@Param("aid") Integer aid,
                               @Param("modified_user") String modifiedUser,
                               @Param("modified_time") Date modifiedTime);

    /**
     * 根据收货地址id删除收货地址
     *
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer deleteAddressByAid(Integer aid);

    /**
     * 根据用户uid查询当前用户最后一次被修改的收货地址
     *
     * @param uid 用户uid
     * @return 收货地址数据
     */
    Address findLastModifiedAddress(Integer uid);
}
