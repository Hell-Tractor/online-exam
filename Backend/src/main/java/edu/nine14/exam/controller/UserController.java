package edu.nine14.exam.controller;

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
}
