package com.my.store.controller;

import com.my.store.controller.ex.*;
import com.my.store.entity.User;
import com.my.store.service.IUserService;
import com.my.store.service.ex.InsertException;
import com.my.store.service.ex.UsernameDuplicatedException;
import com.my.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @RequestMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // user对象中有四部分数据：username，phone，email，gender
        // uid数据需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }


    // 上传文件的最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    // 限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile接口是由SpringMVC提供的接口，这个接口包装了获取任何文件类型的数据
     * SpringBoot整合了SpringMVC，只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数
     * 然后SpringBoot自动将传递给服务的文件数据赋值给这个参数
     *
     * @param session
     * @param file
     * @return
     * @RequestParam -- 将请求中的参数注入到请求处理方法的某个参数上，如果名称不一致，则可使用@RequestParam注解进行标记和映射
     */
    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file) {
        // 判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        // 判断文件大小是否超出限制
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        // 判断文件类型是否符合要求
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        // 文件上传的存放路径 -- ../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");
        System.out.println(parent);
        // File对象指向这个路径，判断是否存在
        File dir = new File(parent);
        if (!dir.exists()) { //目录不存在
            dir.mkdirs(); // 创建目录
        }
        // 获取这个文件名称，UUID工具生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename=" + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename); // 此时是空文件
        // 将参数file中的数据写入到这个空文件中
        try {
            file.transferTo(dest); // 将file文件中的数据写入到dest文件中
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 返回头像路径
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid, avatar, username);
        // 返回用户头像的路径给前端页面，用于头像展示
        return new JsonResult<>(OK, avatar);
    }
}
