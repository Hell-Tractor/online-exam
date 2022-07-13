package edu.nine14.exam.controller;

import edu.nine14.annotation.AuthenticationLevel;
import edu.nine14.annotation.AuthenticationLevelType;
import edu.nine14.annotation.CurrentUser;
import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping(path = "/selectQuestion",method = { RequestMethod.GET })
   // @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectQuestion(){
        //return questionService.getAllQuestion();
        try {
            return ApiResult.ok(questionService.getAllQuestion());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
