package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.bean.model.Follow;
import cn.bestsort.bbslite.bean.model.Topic;
import cn.bestsort.bbslite.bean.model.TopicExample;
import cn.bestsort.bbslite.bean.model.User;
import cn.bestsort.bbslite.dao.create.FollowCreateDTO;
import cn.bestsort.bbslite.dao.dto.PagInationDTO;
import cn.bestsort.bbslite.dao.dto.ResultDTO;
import cn.bestsort.bbslite.dao.mapper.TopicMapper;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.service.FollowService;
import cn.bestsort.bbslite.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TopicController {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    FollowService followService;
    @GetMapping("/topic")
    public String topic(Model model){
        List<Topic> topics = topicMapper.selectByExample(new TopicExample());
        model.addAttribute("topics",topics);
        return "topic";
    }

    @GetMapping("/topic/{id}")
    public String comments(@PathVariable(name="id") Long id,
                           @RequestParam(name="page",defaultValue = "1") Integer page,
                           @RequestParam(name="size",defaultValue = "10") Integer size,
                           Model model){
        Topic topic = topicMapper.selectByPrimaryKey(id);
        PagInationDTO pagInation = questionService.list(page,size,topic.getName());
        model.addAttribute("pagination",pagInation);
        model.addAttribute("topic",topic);
        return "topic";
    }

    @ResponseBody
    @RequestMapping(value = "/topic",method = RequestMethod.POST)
    public Object follow(@RequestBody FollowCreateDTO followCreateDTO,
                         HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return  ResultDTO.errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        Follow follow = new Follow();
        BeanUtils.copyProperties(followCreateDTO,follow);

        followService.insertOrUpdate(follow);
        return ResultDTO.okOf();
    }
}
