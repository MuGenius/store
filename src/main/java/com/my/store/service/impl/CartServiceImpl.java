package com.my.store.service.impl;

import com.my.store.entity.Cart;
import com.my.store.mapper.CartMapper;
import com.my.store.mapper.ProductMapper;
import com.my.store.service.ICartService;
import com.my.store.service.ex.InsertException;
import com.my.store.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid,
                          Integer pid,
                          Integer amount,
                          String username) {
        // 查询当前要添加的商品是否在表中已存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        if (result == null) { // 表示这个商品从未添加到购物车中，则进行添加
            // 创建cart对象
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            cart.setPrice(productMapper.findProductById(pid).getPrice());
            // 四项日志
            cart.setCreated_user(username);
            cart.setCreated_time(date);
            cart.setModified_user(username);
            cart.setModified_time(date);

            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入数据时产生未知异常");
            }
        } else { // 表示当前商品在购物车中已存在，则更新num值
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if (rows != 1) {
                throw new UpdateException("更新数据时产生未知异常");
            }
        }
    }
}
