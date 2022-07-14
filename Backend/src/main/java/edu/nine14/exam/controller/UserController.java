package edu.nine14.exam.controller;

import edu.nine14.annotation.AuthenticationLevel;
import edu.nine14.annotation.AuthenticationLevelType;
import edu.nine14.annotation.CurrentUser;
import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录，只能使用POST请求
     * 同样适用于token刷新
     * @param username 用户名
     * @param password 密码
     * @return 生成的token
     */
    @RequestMapping(path = "/login", method = { RequestMethod.POST })
    public Object login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        try {
            String token = userService.login(username, password);
            return ApiResult.ok(token);
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 更新当前用户的密码
     * @param username 从token中解析出的用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否更新成功
     */
    @RequestMapping(path = "/updatePassword", method = { RequestMethod.POST })
    @AuthenticationLevel(AuthenticationLevelType.USER)
    public Object updatePassword(@CurrentUser String username,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword) {
        try {
            userService.updatePassword(username, oldPassword, newPassword);
            return ApiResult.ok("update password success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 获取当前用户的个人信息，适用于所有用户
     * @param username 从token中解析的用户名
     * @return 用户信息
     */
    @RequestMapping(path = "/getUserInfo")
    @AuthenticationLevel(AuthenticationLevelType.USER)
    public Object getUserInfo(@CurrentUser String username) {
        try {
            Map<String, String> userInfo = userService.getUserInformation(username);
            return ApiResult.ok(userInfo);
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 更新当前用户的个人信息，适用于所有用户
     * @param userInfo 新的用户信息
     * @param username 从token中解析的用户名
     * @return 更新是否成功
     */
    @RequestMapping(path = "/updateUserInfo")
    @AuthenticationLevel(AuthenticationLevelType.USER)
    public Object updateUserInfo(@RequestParam("userInfo") Map<String, String> userInfo, @CurrentUser String username) {
        try {
            userService.updateUserInformation(username, userInfo);
            return ApiResult.ok("update success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 注册新用户（非管理员），只能使用POST请求
     * @param username 用户名
     * @param password 密码
     * @return 注册是否成功
     */
    @RequestMapping(path = "/register", method = { RequestMethod.POST })
    public Object register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        try {
            userService.register(username, password, "U");
            return ApiResult.ok("register success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 获取当前所有用户列表
     * 需要ADMIN权限
     * @return 用户列表
     */
    @RequestMapping(path = "/getUserList")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object getUserList() {
        try {
            return ApiResult.ok(userService.getUserList());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 获取指定用户的个人信息（不包括密码）
     * 需要ADMIN权限
     * @param username 用户名
     * @return 用户信息
     */
    @RequestMapping(path = "/getUserInfoAdmin")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object getUserInfoAdmin(@RequestParam("username") String username) {
        try {
            Map<String, String> userInfo = userService.getUserInformation(username);
            return ApiResult.ok(userInfo);
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 删除指定用户
     * 需要ADMIN权限
     * @param username 用户名
     * @return 删除是否成功
     */
    @RequestMapping(path = "/removeUser")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object removeUser(@RequestParam("username") String username) {
        try {
            userService.removeUser(username);
            return ApiResult.ok("remove success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
