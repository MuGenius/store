package com.my.store.mapper;

import com.my.store.entity.User;
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
public class UserMapperTest {

    /**
     * 单元测试方法：可以单独独立运行，不用启动整个项目，提高代码测试效率
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */

    //idea有检测功能，接口是不能直接创建Bean的（动态代理技术解决）
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("my");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByName() {
        User user = userMapper.findByName("my");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(8, "111", "管理员", new Date());
    }

    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(8));
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(5);
        user.setPhone("15555555555");
        user.setEmail("ly01@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid() {
        userMapper.updateAvatarByUid(1, "/upload/avatar.png", "my", new Date());
    }
}
