package edu.nine14.exam.controller;

import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.bean.User;
import edu.nine14.exam.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
public class Login {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Object login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        Optional<User> result = userRepository.findById(username);
        if (result.isEmpty()) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, "用户不存在");
        }

        if (!result.get().getPassword().equals(password)) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, "用户名或密码错误");
        }

        //todo: Generate token

        return ApiResult.ok("登录成功");
    }
}
