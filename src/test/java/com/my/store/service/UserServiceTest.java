package com.my.store.service;

import com.my.store.entity.User;
import com.my.store.mapper.UserMapper;
import com.my.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest: 当前类是测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith: 表示启动这个单元测试类
@RunWith(SpringRunner.class)
public class UserServiceTest {

    /**
     * 单元测试方法：可以单独独立运行，不用启动整个项目，提高代码测试效率
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */

    //idea有检测功能，接口是不能直接创建Bean的（动态代理技术解决）
    @Autowired
    private IUserService userService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("test02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
    }

    @Test
    public void login() {
        User user = userService.login("test02", "123");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(9, "ly02", "123", "111");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(5));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("13333333333");
        user.setEmail("ly@qq.com");
        user.setGender(2);
        userService.changeInfo(5, "ly01", user);
    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(1, "/upload/test.png", "admin");
    }
}
