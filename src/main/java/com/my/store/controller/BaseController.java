package com.my.store.controller;

import com.my.store.service.ex.*;
import com.my.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/* 控制层类的基类 */
public class BaseController {

    // 操作成功的状态码
    public static final int OK = 200;

    // 当项目中产生了异常，会被统一拦截到此方法中，这个方法此时充当的是，请求处理方法，这个方法的返回值就是需要传递给前端的数据
    // 自动将异常对象传递给此方法的参数列表上
    @ExceptionHandler(ServiceException.class)  //用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMsg("用户名已被使用");
        } else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMsg("用户数据不存在");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMsg("密码错误");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMsg("注册时产生未知异常");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMsg("更新数据时产生未知异常");
        }
        return result;
    }

    /**
     * 获取session中的uid
     *
     * @param session session对象
     * @return 当前登录用户的用户uid
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前登录用户的username
     *
     * @param session session对象
     * @return 当前登录用户的用户名
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
