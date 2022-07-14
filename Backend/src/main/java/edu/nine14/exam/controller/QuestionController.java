package edu.nine14.exam.controller;

import edu.nine14.annotation.AuthenticationLevel;
import edu.nine14.annotation.AuthenticationLevelType;
import edu.nine14.annotation.CurrentUser;
import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.entity.Direction;
import edu.nine14.exam.entity.Question;
import edu.nine14.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     * 获取所有题目信息
     * 需要ADMIN权限
     * @return 返回所有题目信息
     */
    @RequestMapping(path = "/api/admin/question/page",method = { RequestMethod.GET })
   // @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectAllQuestion(){
        try {
            return ApiResult.ok(questionService.getAllQuestion());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 查询某一类型的题目
     * @param type 题目类型
     * @return
     */
    @RequestMapping(path = "/api/admin/question/page/{type}",method = { RequestMethod.GET })
    // @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectByType(@PathVariable("type") String type){
        try {
            return ApiResult.ok(questionService.selectByType(type));
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 删除题目
     * @param id 题目的id
     * @return
     */
    @RequestMapping(path = "/api/admin/question/delete/{id}")
    // @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object deleteQuestionById(@PathVariable("id") Integer id){
        try {
            questionService.deleteQuestion(id);
            return ApiResult.ok("delete question success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 根据题目Id查询相应题目
     * @param id 题目Id
     * @return
     */
    @RequestMapping(path = "/api/admin/question/select/{id}")
    // @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectQuestionById(@PathVariable("id") Integer id){
        try {
            return ApiResult.ok(questionService.selectById(id));
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 编辑题目
     * @return
     */
    @PostMapping("/api/admin/question/edit")
    // @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object editQuestion(@RequestParam("id") Integer id,
                               @RequestParam("direction_id") Direction directionID,
                               @RequestParam("body") String body,
                               @RequestParam("answer") String answer,
                               @RequestParam("type") String type,
                               @RequestParam("selection") String selection){
        try {
            return ApiResult.ok(questionService.getAllQuestion());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 添加题目
     * @param question
     * @return
     */
    @RequestMapping(path = "/api/admin/question/add")
    // @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object addQuestion(@RequestBody Question question){
        try {
            questionService.addQuestion(question);
            return ApiResult.ok("add question success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
