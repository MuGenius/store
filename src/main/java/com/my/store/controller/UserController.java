package com.my.store.controller;

import com.my.store.entity.User;
import com.my.store.service.IUserService;
import com.my.store.service.ex.InsertException;
import com.my.store.service.ex.UsernameDuplicatedException;
import com.my.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@Controller
@RestController  // @Controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 1.接收数据方式：请求处理方法的参数列表设置为pojo类型来接收前端的数据
     * SpringBoot会将前端url地址中的参数名和pojo类的属性名进行比较，如果这两个名称相同，则会将值注入到pojo类中对应的属性上
     */
    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    /*@RequestMapping("/reg")
    //@ResponseBody  //表示此方法的响应结果以Json格式进行数据的响应给前端
    public JsonResult<Void> reg(User user) {
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMsg("注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMsg("用户名已被使用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMsg("注册时产生未知异常");
        }
        return result;
    }*/

    /**
     * 2.接收数据方式：请求处理方法的参数列表设置为非pojo类型
     * SpringBoot会直接将请求的参数名和方法的参数名直接进行比较，如果名称相同则自动完成值的依赖注入
     */
    @RequestMapping("/login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session) {
        User data = userService.login(username, password);
        //完成session对象数据的绑定
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }
}
