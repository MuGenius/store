package com.my.store.service.impl;

import com.my.store.entity.User;
import com.my.store.mapper.UserMapper;
import com.my.store.service.IUserService;
import com.my.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块业务层实现类
 */
@Service  //将当前类的对象交给Spring管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {

    @Autowired
    //@Resource
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        //调用findByName()判断用户是否被注册过
        User result = userMapper.findByName(username);
        //判断结果集是否为null，否，则抛出异常
        if (result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        //密码加密处理 -- MD5
        //串(盐值) + pwd + 串 --> MD5算法进行加密
        String oldPwd = user.getPassword();
        //获取盐值(随机生成)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //调用方法进行加密处理
        String md5Pwd = getMD5Pwd(oldPwd, salt);
        //将加密后的密码设置到user对象中，以及盐值
        user.setSalt(salt);
        user.setPassword(md5Pwd);

        //补全数据：is_delete = 0
        user.setIs_delete(0);
        //补全数据：4个日志字段信息
        user.setCreated_user(user.getUsername());
        user.setModified_user(user.getUsername());
        Date date = new Date();
        user.setCreated_time(date);
        user.setModified_time(date);

        //执行注册业务功能的实现（rows == 1）
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("注册过程中产生了未知异常");
        }
    }

    @Override
    public User login(String username, String password) {
        // 根据用户名称来查询用户的数据是否存在，如果不在则抛出异常
        User result = userMapper.findByName(username);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 检测用户密码是否匹配
        // 1.先获取数据库中的加密之后的密码
        String oldPwd = result.getPassword();
        // 2.和用户传递过来的密码进行比较
        // 2.1 先获取盐值 -- 注册时所自动产生的盐值
        String salt = result.getSalt();
        // 2.2 将用户密码按照相同的MD5算法的规则进行加密
        String newMD5Pwd = getMD5Pwd(password, salt);
        // 3.将密码进行比较
        if (!newMD5Pwd.equals(oldPwd)) {
            throw new PasswordNotMatchException("密码错误");
        }

        // 判断is_delete字段的值是否为1，表示为已删除
        if (result.getIs_delete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        // 调用mapper层的findByName查询用户数据，提升系统的性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        // 将当前用户的数据返回，返回的数据是为了辅助其他页面做数据展示使用
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIs_delete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 原始密码和数据库中的密码进行比较
        String oldMD5Password = getMD5Pwd(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMD5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        // 设置新密码
        String newMD5Password = getMD5Pwd(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMD5Password, username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    /* MD5算法加密 */
    private String getMD5Pwd(String pwd, String salt) {
        // MD5加密算法方法的调用(加密三次)
        for (int i = 0; i < 3; i++) {
            pwd = DigestUtils.md5DigestAsHex((salt + pwd + salt).getBytes()).toUpperCase();
        }
        // 返回加密后的密码
        return pwd;
    }
}
