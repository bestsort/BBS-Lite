package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.QuestionQueryDto;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.FollowService;
import cn.bestsort.bbslite.service.QuestionService;
import cn.bestsort.bbslite.service.ThumbUpService;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.vo.QuestionDetailOptionVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName QuestionController
 * @Description 问题控制器,用于查询问题列表
 * @Author bestsort
 * @Date 19-8-31 下午8:35
 * @Version 1.0
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ThumbUpService thumbUpService;
    @Autowired
    private FollowService followService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id){
        return "question";
    }

    /**
     * @Description 获取问题列表(可根据搜索内容/话题/分类筛选结果)
     * @param sort 排序关键字
     * @param search 搜索关键字(根据正则搜索标题)
     * @param topic 话题
     * @param tag 标签(根据正则搜索标签)
     * @param size 页面大小(一页所容纳的问题数)
     * @param page 第几页
     * @param categoryVal 话题分类
     * @return 根据条件筛选并进行分页后的问题列表
     */
    @ResponseBody
    @GetMapping("/loadQuestionList")
    public ResultDto getQuestionList(@RequestParam(name = "sortby",defaultValue = "ALL") String sort,
                                     @RequestParam(name = "search",required = false) String search,
                                     @RequestParam(name = "topic",defaultValue = "0") Integer topic,
                                     @RequestParam(name = "tag",required = false) String tag,
                                     @RequestParam(name = "pageSize",defaultValue = "10") Integer size,
                                     @RequestParam(name = "pageNo",defaultValue = "1") Integer page,
                                     @RequestParam(name = "category",defaultValue = "0") Integer categoryVal){
        QuestionQueryDto queryDto = QuestionQueryDto.builder()
                .search(search).category(categoryVal).pageNo(page)
                .tag(tag).pageSize(size).topic(topic).build();
        PageInfo<Question> pageInfo = questionService.getPageBySearch(queryDto);
        return new ResultDto().okOf().addMsg("page",pageInfo);
    }

    /**
     * 加载问题详情
     * @param id 问题id
     * @return 问题详情
     */
    @ResponseBody
    @GetMapping("/loadQuestionDetail")
    public ResultDto getQuestionDetail(@RequestParam(name = "id") Long id){
        Question question = questionService.getQuestionDetail(id);
        User user = userService.getSimpleInfoById(question.getCreator());
        return new ResultDto().okOf()
                .addMsg("question",question)
                .addMsg("user",user);
    }

    @ResponseBody
    @GetMapping("/loadQuestionOption")
    public ResultDto getQuestionOption(@RequestParam(name = "questionId") Long questionId,
                                       @RequestParam(name = "userId") Long userId,
                                       HttpSession session){
        QuestionDetailOptionVo questionDetailOptionVo = new QuestionDetailOptionVo();
        User user = (User)session.getAttribute("user");
        if(user != null && user.getId().equals(userId)){
            questionDetailOptionVo.setIsCreator(false);
        }

        if(user != null) {
            questionDetailOptionVo.setIsThumbUpQuestion(
                    thumbUpService.getStatusByUser(questionId, user.getId())
            );
            questionDetailOptionVo.setIsFollowQuestion(
                    followService.getStatusByUser(questionId,user.getId(), FunctionItem.QUESTION)
            );
        }else {
            questionDetailOptionVo.setIsThumbUpQuestion(false);
            questionDetailOptionVo.setIsFollowQuestion(false);
        }
        return new ResultDto().okOf().addMsg("options",questionDetailOptionVo);
    }
}
