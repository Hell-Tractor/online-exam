package edu.nine14.exam.controller;

import edu.nine14.annotation.AuthenticationLevel;
import edu.nine14.annotation.AuthenticationLevelType;
import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.choiceEntity.ExamChoice;
import edu.nine14.exam.service.ProfessionService;
import edu.nine14.exam.service.QuestionDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExamController {
    @Autowired
    private QuestionDirectionService questionDirectionService;

    @Autowired
    private ProfessionService professionService;

    /**
     * 实现根据要求抽取随机题目组卷
     * @param examChoice 前端的json数据
     * @return
     */
    @RequestMapping(path = "/api/student/dashboard/createPaper", method = {RequestMethod.POST})
    @AuthenticationLevel(AuthenticationLevelType.USER)
    public Object createPaper(@RequestBody ExamChoice examChoice) {
        String profession=examChoice.getProfession();
        boolean has_direction=examChoice.isHas_direction();
        Integer single_choice_num=examChoice.getSingle_choice_num();
        Integer multiple_choice_num=examChoice.getMultiple_choice_num();
        Integer true_false_num=examChoice.getTrue_false_num();
        Integer short_answer_num=examChoice.getShort_answer_num();

        Object professionID = professionService.findProfessionID(profession);

        List<Object> single_question = new ArrayList<>();
        List<Object> multiple_question = new ArrayList<>();
        List<Object> true_false_question = new ArrayList<>();
        List<Object> short_answer_question = new ArrayList<>();

        if(single_choice_num==0&&multiple_choice_num==0&&true_false_num==0&&short_answer_num==0)
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR,"各题型题目数量不能均为0");

        if(single_choice_num<0||multiple_choice_num<0||true_false_num<0||short_answer_num<0)
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR,"题目数量不能为负");

        if (!has_direction) {
            if (single_choice_num >= 0) {
                List<Object> questionList = (List<Object>)questionDirectionService.questionForProfession((Integer) professionID, "1");
                Integer max_num = questionList.size();
                try {
                    if (single_choice_num > max_num)
                        throw new Exception("输入单选题题目数量过大");
                    else {
                        single_question=questionDirectionService.returnQuestion(single_choice_num,max_num,questionList);
                    }
                } catch (Exception e) {
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if (multiple_choice_num >= 0) {
                List<Object> questionList = (List<Object>)questionDirectionService.questionForProfession((Integer) professionID, "2");
                Integer max_num = questionList.size();
                try {
                    if (multiple_choice_num > max_num)
                        throw new Exception("输入多选题题目数量过大");
                    else {
                        multiple_question=questionDirectionService.returnQuestion(multiple_choice_num,max_num,questionList);
                    }
                } catch (Exception e) {
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if (true_false_num >= 0) {
                List<Object> questionList = (List<Object>)questionDirectionService.questionForProfession((Integer) professionID, "3");
                Integer max_num = questionList.size();
                try {
                    if (true_false_num > max_num)
                        throw new Exception("输入是非题题目数量过大");
                    else {
                        true_false_question=questionDirectionService.returnQuestion(true_false_num,max_num,questionList);
                    }
                } catch (Exception e) {
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if (short_answer_num >= 0) {
                List<Object> questionList = (List<Object>)questionDirectionService.questionForProfession((Integer) professionID, "4");
                Integer max_num = questionList.size();
                try {
                    if (short_answer_num > max_num)
                        throw new Exception("输入简答题题目数量过大");
                    else {
                        short_answer_question=questionDirectionService.returnQuestion(short_answer_num,max_num,questionList);
                    }
                } catch (Exception e) {
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }

            List<Object> final_question = new ArrayList<>();
            final_question.addAll(single_question);
            final_question.addAll(multiple_question);
            final_question.addAll(true_false_question);
            final_question.addAll(short_answer_question);

            return ApiResult.ok(final_question);
        } else {
            ArrayList<String> direction=examChoice.getDirections();

            String direction1,direction2;
            try {
                if (direction.isEmpty())
                    throw new Exception("选择的专业方向不能为空");
                else if (direction.size() == 1)
                    throw new Exception("选择的专业方向数量少于二");
                else {
                    direction1 = direction.get(0);
                    direction2 = direction.get(1);
                }
            }catch(Exception e){
                return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
            }


            if (single_choice_num >= 0) {
                List<Object> questionList = (List<Object>)questionDirectionService.questionForDirection((Integer) professionID, "1", direction1, direction2);
                Integer max_num = questionList.size();
                try {
                    if (single_choice_num > max_num)
                        throw new Exception("输入单选题题目数量过大");
                    else {
                        single_question=questionDirectionService.returnQuestion(single_choice_num,max_num,questionList);
                    }
                } catch (Exception e) {
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if (multiple_choice_num >= 0) {
                List<Object> questionList = (List<Object>)questionDirectionService.questionForDirection((Integer) professionID, "2", direction1, direction2);
                Integer max_num = questionList.size();
                try {
                    if (multiple_choice_num > max_num)
                        throw new Exception("输入多选题题目数量过大");
                    else {
                        multiple_question=questionDirectionService.returnQuestion(multiple_choice_num,max_num,questionList);
                    }
                } catch (Exception e) {
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if (true_false_num >= 0) {
                List<Object> questionList = (List<Object>)questionDirectionService.questionForDirection((Integer) professionID, "3", direction1, direction2);
                Integer max_num = questionList.size();
                try {
                    if (true_false_num > max_num)
                        throw new Exception("输入是非题题目数量过大");
                    else {
                        true_false_question=questionDirectionService.returnQuestion(true_false_num,max_num,questionList);
                    }
                } catch (Exception e) {
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if (short_answer_num >= 0) {
                List<Object> questionList = (List<Object>)questionDirectionService.questionForDirection((Integer) professionID, "4", direction1, direction2);
                Integer max_num = questionList.size();
                try {
                    if (short_answer_num > max_num)
                        throw new Exception("输入简答题题目数量过大");
                    else {
                        short_answer_question=questionDirectionService.returnQuestion(short_answer_num,max_num,questionList);
                    }
                } catch (Exception e) {
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }

            List<Object> final_question = new ArrayList<>();
            final_question.addAll(single_question);
            final_question.addAll(multiple_question);
            final_question.addAll(true_false_question);
            final_question.addAll(short_answer_question);
            
            return ApiResult.ok(final_question);
        }
    }
}
