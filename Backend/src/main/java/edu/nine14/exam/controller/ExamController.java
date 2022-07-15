package edu.nine14.exam.controller;

import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.entity.QuestionDirection;
import edu.nine14.exam.service.ProfessionService;
import edu.nine14.exam.service.QuestionDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ExamController {
    @Autowired
    //private QuestionService questionService;
    private QuestionDirectionService questionDirectionService;

    @Autowired
    private ProfessionService professionService;

    /*@RequestMapping(path = "/createPaper",method = { RequestMethod.GET })
    @ResponseBody
    //@AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object chooseQuestions() {
        return questionDirectionService.questionForDirection((Integer)professionService.findProfessionID("毛概"),"1","经济","生态");
    }*/

    @RequestMapping(path = "/createPaper",method = { RequestMethod.GET })
    @ResponseBody
    public Object createPaper(@RequestParam("profession") String profession,
                              @RequestParam("has_direction") String has_direction,
                              @RequestParam("direction1") String direction1,
                              @RequestParam("direction2") String direction2,
                              @RequestParam("single_choice_num") Integer single_choice_num,
                              @RequestParam("multiple_choice_num") Integer multiple_choice_num,
                              @RequestParam("true_false_num") Integer true_false_num,
                              @RequestParam("short_answer_num") Integer short_answer_num){
        Object professionID= professionService.findProfessionID(profession);

        List<Object> single_question=new ArrayList<>();
        List<Object> multiple_question=new ArrayList<>();
        List<Object> true_false_question=new ArrayList<>();
        List<Object> short_answer_question=new ArrayList<>();

        if(has_direction.equals("NO")){
            if(single_choice_num>=0){
                Object questionList=questionDirectionService.questionForProfession((Integer)professionID,"1");
                Integer max_num= ((List<Object>) questionList).size();
                try{
                    if(single_choice_num>max_num)
                        throw new Exception("输入题目数量过大");
                    else{
                        Random random=new Random();
                        Integer[] question_num=new Integer[single_choice_num];
                        Integer next_question;
                        for(int i=0;i<single_choice_num;i++){
                            next_question=random.nextInt();
                            int j;
                            for(j=0;j<i;j++){
                                if(question_num[j]==next_question)
                                    break;
                            }
                            if(j==i)
                                question_num[i]=next_question;
                            else
                                i--;
                        }

                        for(int i=0;i<single_choice_num;i++){
                            single_question.add(((List<Object>) questionList).get(question_num[i]));
                        }
                    }
                }catch (Exception e){
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if(multiple_choice_num>=0){
                Object questionList=questionDirectionService.questionForProfession((Integer)professionID,"2");
                Integer max_num= ((List<Object>) questionList).size();
                try{
                    if(multiple_choice_num>max_num)
                        throw new Exception("输入题目数量过大");
                    else{
                        Random random=new Random();
                        Integer[] question_num=new Integer[multiple_choice_num];
                        Integer next_question;
                        for(int i=0;i<multiple_choice_num;i++){
                            next_question=random.nextInt();
                            int j;
                            for(j=0;j<i;j++){
                                if(question_num[j]==next_question)
                                    break;
                            }
                            if(j==i)
                                question_num[i]=next_question;
                            else
                                i--;
                        }

                        for(int i=0;i<single_choice_num;i++){
                            multiple_question.add(((List<Object>) questionList).get(question_num[i]));
                        }
                    }
                }catch (Exception e){
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if(true_false_num>=0){
                Object questionList=questionDirectionService.questionForProfession((Integer)professionID,"3");
                Integer max_num= ((List<Object>) questionList).size();
                try{
                    if(true_false_num>max_num)
                        throw new Exception("输入题目数量过大");
                    else{
                        Random random=new Random();
                        Integer[] question_num=new Integer[true_false_num];
                        Integer next_question;
                        for(int i=0;i<true_false_num;i++){
                            next_question=random.nextInt();
                            int j;
                            for(j=0;j<i;j++){
                                if(question_num[j]==next_question)
                                    break;
                            }
                            if(j==i)
                                question_num[i]=next_question;
                            else
                                i--;
                        }

                        for(int i=0;i<true_false_num;i++){
                            true_false_question.add(((List<Object>) questionList).get(question_num[i]));
                        }
                    }
                }catch (Exception e){
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if(short_answer_num>=0){
                Object questionList=questionDirectionService.questionForProfession((Integer)professionID,"4");
                Integer max_num= ((List<Object>) questionList).size();
                try{
                    if(short_answer_num>max_num)
                        throw new Exception("输入题目数量过大");
                    else{
                        Random random=new Random();
                        Integer[] question_num=new Integer[short_answer_num];
                        Integer next_question;
                        for(int i=0;i<short_answer_num;i++){
                            next_question=random.nextInt();
                            int j;
                            for(j=0;j<i;j++){
                                if(question_num[j]==next_question)
                                    break;
                            }
                            if(j==i)
                                question_num[i]=next_question;
                            else
                                i--;
                        }

                        for(int i=0;i<short_answer_num;i++){
                            short_answer_question.add(((List<Object>) questionList).get(question_num[i]));
                        }
                    }
                }catch (Exception e){
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }

            List<Object> final_question=new ArrayList<>();
            final_question.addAll(single_question);
            final_question.addAll(multiple_question);
            final_question.addAll(true_false_question);
            final_question.addAll(short_answer_question);

            String[] final_question_array=final_question.toArray(new String[final_question.size()]);

            return ApiResult.ok(final_question_array);
        }
        else{
            if(single_choice_num>=0){
                Object questionList=questionDirectionService.questionForDirection((Integer)professionID,"1",direction1,direction2);
                Integer max_num= ((List<Object>) questionList).size();
                try{
                    if(single_choice_num>max_num)
                        throw new Exception("输入题目数量过大");
                    else {
                        Random random = new Random();
                        Integer[] question_num = new Integer[single_choice_num];
                        Integer next_question;
                        for (int i = 0; i < single_choice_num; i++) {
                            next_question = random.nextInt();
                            int j;
                            for (j = 0; j < i; j++) {
                                if (question_num[j] == next_question)
                                    break;
                            }
                            if (j == i)
                                question_num[i] = next_question;
                            else
                                i--;
                        }
                        for(int i=0;i<single_choice_num;i++){
                            single_question.add(((List<Object>) questionList).get(question_num[i]));
                        }
                    }
                }catch (Exception e){
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if(multiple_choice_num>=0){
                Object questionList=questionDirectionService.questionForDirection((Integer)professionID,"2",direction1,direction2);
                Integer max_num= ((List<Object>) questionList).size();
                try{
                    if(multiple_choice_num>max_num)
                        throw new Exception("输入题目数量过大");
                    else{
                        Random random=new Random();
                        Integer[] question_num=new Integer[multiple_choice_num];
                        Integer next_question;
                        for(int i=0;i<multiple_choice_num;i++){
                            next_question=random.nextInt();
                            int j;
                            for(j=0;j<i;j++){
                                if(question_num[j]==next_question)
                                    break;
                            }
                            if(j==i)
                                question_num[i]=next_question;
                            else
                                i--;
                        }

                        for(int i=0;i<single_choice_num;i++){
                            multiple_question.add(((List<Object>) questionList).get(question_num[i]));
                        }
                    }
                }catch (Exception e){
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if(true_false_num>=0){
                Object questionList=questionDirectionService.questionForDirection((Integer)professionID,"3",direction1,direction2);
                Integer max_num= ((List<Object>) questionList).size();
                try{
                    if(true_false_num>max_num)
                        throw new Exception("输入题目数量过大");
                    else{
                        Random random=new Random();
                        Integer[] question_num=new Integer[true_false_num];
                        Integer next_question;
                        for(int i=0;i<true_false_num;i++){
                            next_question=random.nextInt();
                            int j;
                            for(j=0;j<i;j++){
                                if(question_num[j]==next_question)
                                    break;
                            }
                            if(j==i)
                                question_num[i]=next_question;
                            else
                                i--;
                        }

                        for(int i=0;i<true_false_num;i++){
                            true_false_question.add(((List<Object>) questionList).get(question_num[i]));
                        }
                    }
                }catch (Exception e){
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
            if(short_answer_num>=0){
                Object questionList=questionDirectionService.questionForDirection((Integer)professionID,"4",direction1,direction2);
                Integer max_num= ((List<Object>) questionList).size();
                try{
                    if(short_answer_num>max_num)
                        throw new Exception("输入题目数量过大");
                    else{
                        Random random=new Random();
                        Integer[] question_num=new Integer[short_answer_num];
                        Integer next_question;
                        for(int i=0;i<short_answer_num;i++){
                            next_question=random.nextInt();
                            int j;
                            for(j=0;j<i;j++){
                                if(question_num[j]==next_question)
                                    break;
                            }
                            if(j==i)
                                question_num[i]=next_question;
                            else
                                i--;
                        }

                        for(int i=0;i<short_answer_num;i++){
                            short_answer_question.add(((List<Object>) questionList).get(question_num[i]));
                        }
                    }
                }catch (Exception e){
                    return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }

            List<Object> final_question=new ArrayList<>();
            final_question.addAll(single_question);
            final_question.addAll(multiple_question);
            final_question.addAll(true_false_question);
            final_question.addAll(short_answer_question);

            String[] final_question_array=final_question.toArray(new String[final_question.size()]);

            return ApiResult.ok(final_question_array);
        }
    }
}