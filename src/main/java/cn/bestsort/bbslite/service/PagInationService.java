package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.QuestionExample;
import cn.bestsort.bbslite.pojo.vo.PagInationVo;
import cn.bestsort.bbslite.pojo.vo.QuestionInfoVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
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
    @Resource
    private UserService userService;
    @Resource
    private CountService countService;

    public PagInationVo getPagInationList(int page, int size, int type, Object key){
        List<Question> questions;
        PagInationVo result = new PagInationVo();
        questions = questionService.listByRowBounds(key, page, size, type);
        int totalCount;
        if(type == QuestionService.SEARCH) {
            totalCount = questions.size();
        }
        else {
            totalCount = (int)questionService.countByType(type,key);
        }
        result.setPagination(totalCount,page,size);

        List<QuestionInfoVo> questionInfoVos = new LinkedList<>();
        for(Question i:questions){
            QuestionInfoVo buf = new QuestionInfoVo();
            buf.setQuestion(i);
            buf.setUser(userService.getById(i.getCreator()));
            buf.setQuestionCount(countService.getQuestionCountById(i.getId()));
            questionInfoVos.add(buf);
        }
        result.setQuestions(questionInfoVos);
        return result;
    }
}
