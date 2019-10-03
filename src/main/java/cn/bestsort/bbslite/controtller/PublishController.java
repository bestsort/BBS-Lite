package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.pojo.dto.QuestionDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.mapper.TopicMapper;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.Topic;
import cn.bestsort.bbslite.pojo.model.TopicExample;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.PagInationService;
import cn.bestsort.bbslite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private QuestionMapper questionMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String publish(Model model){
        topicAttribute(model);
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model,
                       HttpServletRequest request){

        QuestionDto question = questionService.getByQuestionId(id);
        User user = (User)request.getSession().getAttribute("user");
        if(!question.getCreator().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCodeEnum.USER_ERROR);
        }

        topicAttribute(model);
        model.addAttribute("title",question.getTitle())
                .addAttribute("tag",question.getTag())
                .addAttribute("description",question.getDescription())
                .addAttribute("id",question.getId())
                .addAttribute("topic",question.getTopic());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id,
            @RequestParam("topic") String topic,
            HttpServletRequest request,
            Model model){
        boolean isNull = false;
        if(tag == null || "".equals(tag)){
            model.addAttribute("error","标签不能为空");
            isNull = true;
        }
        if(description == null || "".equals(description)){
            model.addAttribute("error","问题描述不能为空");
            isNull = true;
        }
        if(title == null || "".equals(title)){
            model.addAttribute("error","标题不能为空");
            isNull = true;
        }
        model.addAttribute("title",title)
                 .addAttribute("tag",tag)
                 .addAttribute("description",description)
                 .addAttribute(topic);
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error", CustomizeErrorCodeEnum.NO_LOGIN);
            return "publish";
        }

        if(!isNull) {

            String defaltTopic = "加入话题";
            Question question = new Question();
            question.setTitle(title);
            question.setTag(tag);
            question.setDescription(description);
            question.setCreator(user.getId());
            question.setId(id);
            if (!defaltTopic.equals(topic)){
                question.setTopic(topic);
            }
            questionService.createOrUpdate(question);
        }
        return "redirect:/";
    }
    private void topicAttribute(Model model){
        List<Topic> topics = topicMapper.selectByExample(new TopicExample());
        model.addAttribute("topic_info",topics);
    }
}
