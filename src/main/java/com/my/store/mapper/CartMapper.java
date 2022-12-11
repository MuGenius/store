package com.my.store.mapper;

import com.my.store.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface CartMapper {

    /**
     * 插入购物车数据
     *
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 更新国五车某件商品数量
     *
     * @param cid          购物车数据id
     * @param num          更新的数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(Integer cid,
                           Integer num,
                           @Param("modified_user") String modifiedUser,
                           @Param("modified_time") Date modifiedTime);

    /**
     * 根据用户id和商品id查询购物车中的数据
     *
     * @param uid 用户id
     * @param pid 商品id
     * @return 购物车数据
     */
    Cart findByUidAndPid(Integer uid, Integer pid);
}
