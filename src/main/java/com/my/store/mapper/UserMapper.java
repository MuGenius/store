package com.my.store.mapper;

import com.my.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

@Repository
public interface UserMapper {

    /**
     * 插入用户数据
     *
     * @param user
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return 如果找到对应的用户，则返回该用户的数据，否则返回null
     */
    User findByName(String username);

    /**
     * 根据用户的uid来修改用户密码
     *
     * @param uid          用户的uid
     * @param password     用户输入的新密码
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 返回值为受影响的行数
     */
    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);

    /**
     * 根据用户的id查询用户的数据
     *
     * @param uid 用户的uid
     * @return 找到则返回对象，反之则返回null
     */
    User findByUid(Integer uid);

    /**
     * 更新用户信息
     *
     * @param user 用户数据
     * @return 返回值为受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     * @Param("SQL映射文件中#{}占位符的变量名") -- 当SQL语句的占位符和映射的接口方法阐述名不一致时吗，需要将某个参数强行注入到某个占位符变量上
     * 根据用户uid修改用户头像
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modified_user") String modifiedUser,
                              @Param("modified_time") Date modifiedTime);
}
