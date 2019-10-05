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
    public static int SEARCH = 1;
    public static int TOPIC = 2;
    public static int USER = 3;
    public static int ALL = 4;

    public PagInationVo getPagInationList(long page, long size, long type, Object key){
        List<Question> questions;
        PagInationVo result = new PagInationVo();
        QuestionExample example = new QuestionExample();
        Long totalCount;
        if(type == SEARCH){
            questions = questionService.listBySearch(key.toString());
            totalCount = (long)questions.size();
            page = Math.min(totalCount /size + (totalCount %size==0? 0 : 1),page);
            page = Math.max(page,1);
        }else {
            if (type == TOPIC){
                example.createCriteria().andTopicEqualTo(key.toString());
            }
            else if (type == USER){
                example.createCriteria().andCreatorEqualTo((long)key);
            }
            totalCount = questionService.countByExample(example);
            //限制访问合法
            page = Math.min(totalCount /size + (totalCount %size==0? 0L : 1L),page);
            page = Math.max(page,1);
            long offset = size * (page - 1);

            questions = questionService.listByRowBounds(example,(int)offset,(int)size);
            totalCount = (long)questions.size();
        }
        result.setPagination(totalCount.intValue(),(int)page,(int)size);
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
