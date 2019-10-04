package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.pojo.dto.PagInationDto;
import cn.bestsort.bbslite.pojo.dto.QuestionDto;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.TopicExtMapper;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.QuestionExample;
import cn.bestsort.bbslite.pojo.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName QuestionService
 * @Description 问题处理(按条件搜索,所有,根据Id查找等)
 * @Author bestsort
 * @Date C19-8-28 下午6:30
 * @Version 1.0
 */
@Service
public class PagInationService {
    @Resource
    private QuestionService questionService;
    @Autowired
    TopicExtMapper topicExtMapper;

    private Integer totalCount;

    @Resource
    UserService userService;
    public PagInationDto listBySearch(String search, Integer page, Integer size){
        PagInationDto result;
        if(search.isEmpty()){
            totalCount =  questionService.countAll().intValue();
            result = getPagInation(new QuestionExample(),page,size);
        }
        else{
            List<Question> questions = questionService.listBySearch(search);
            totalCount = questions.size();
            result = getPagInation(questions,page,size);
        }
        return result;
    }
    public PagInationDto listByPage(Integer page, Integer size, String topic){
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andTopicEqualTo(topic);
        totalCount = questionService.countAll().intValue();
        return getPagInation(questionExample,page,size);
    }
    public PagInationDto listByUserId(Long userId , Integer page, Integer size) {

        totalCount = questionService.countByUserId(userId).intValue();
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        return getPagInation(example,page,size);
    }

    /**
     * 将问题分页
     */
    @NotNull

    private PagInationDto getPagInation(QuestionExample example, Integer page, Integer size){
        int offset = size * (page - 1);
        //限制访问合法
        page = Math.min(totalCount/size + (totalCount%size==0? 0 : 1),page);
        page = Math.max(page,1);

        List<Question> questions = questionService.listByRowBounds(offset,size);
        return getPagInationDTO(questions, page, size);
    }

    private PagInationDto getPagInation(List<Question> questions, Integer page, Integer size){
        //限制访问合法
        page = Math.min(totalCount/size + (totalCount%size==0? 0 : 1),page);
        page = Math.max(page,1);
        return getPagInationDTO(questions, page, size);
    }

    @NotNull
    private PagInationDto getPagInationDTO(List<Question> questions, Integer page, Integer size) {
        List<QuestionDto> questionDTOList = new ArrayList<>();
        PagInationDto pagInationDTO = new PagInationDto();
        for (Question question : questions) {
            User user = userService.getById(question.getCreator());
            QuestionDto questionDTO = new QuestionDto();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pagInationDTO.setQuestions(questionDTOList);
        pagInationDTO.setPagination(totalCount,page,size);
        return pagInationDTO;
    }
}
