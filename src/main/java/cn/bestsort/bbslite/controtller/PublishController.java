package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.mapper.TopicMapper;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.QuestionService;
import cn.bestsort.bbslite.service.TopicService;
import cn.bestsort.bbslite.vo.PublishVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @ClassName PublishController
 * @Description 提问控制器,用于修改问题/发布问题
 * @Author bestsort
 * @Date 19-8-26 下午7:53
 * @Version 1.0
 */

@Controller
public class PublishController {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TopicService topicService;
    @GetMapping("/publish")
    public String publish(Model model){
        return "publish";
    }

    @ResponseBody
    @GetMapping("/getPublishInfo")
    public ResultDto getPublishInfo(@RequestParam(name = "id",required = false) Long id,
                                    HttpSession session){
        User user = (User)session.getAttribute("user");
        ResultDto resultDto;
        /*if(!questionService.getQuestionDetail(id).getCreator().equals(user.getId())){
            resultDto = new ResultDto().errorOf(CustomizeErrorCodeEnum.USER_ERROR);
        }
        else {
 */         resultDto = new ResultDto().okOf();
        PublishVo publishVo = new PublishVo();
        publishVo.setTopics(topicService.getAll());
        if (id != null) {
            publishVo.setQuestion(questionService.getQuestionDetail(id));
        }
        resultDto.addMsg("publishInfo",publishVo);
        //}
        return resultDto;
    }

    /**
     * TODO 部分字段为空时须进行前端校验
     **/
    @ResponseBody
    @PostMapping("/publish")
    public ResultDto doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam("topic") String topic,
            HttpSession session){

        User user = (User)session.getAttribute("user");
        if(user == null){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setUserAvatarUrl(user.getAvatarUrl());
        question.setUserName(user.getName());
        question.setTopic(topic);
        if (id != null) {
            question.setId(id);
        }
        question.setTopic(topic);
        Long newId = questionService.createOrUpdate(question);
        if(newId == null){
            newId = id;
        }
        return new ResultDto().okOf().addMsg("id",newId);
    }
}
