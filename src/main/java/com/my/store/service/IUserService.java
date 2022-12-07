package com.my.store.service;

import com.my.store.entity.User;

/* 用户模块业务层接口 */
public interface IUserService {

    /**
     * 用户注册方法
     *
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录功能
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 当前匹配的用户数据，如果没有则返回null
     */
    User login(String username, String password);

    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);

    /**
     * 根据用户uid查询用户数据
     *
     * @param uid 用户uid
     * @return 用户数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户数据
     *
     * @param uid      用户uid
     * @param username 用户名
     * @param user     用户对象的数据
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 更新用户头像
     *
     * @param uid      用户id
     * @param avatar   用户头像路径
     * @param username 用户名
     */
    void changeAvatar(Integer uid, String avatar, String username);
}
