package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.PagInationDTO;
import cn.bestsort.bbslite.dto.ResultDTO;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.mapper.TopicMapper;
import cn.bestsort.bbslite.model.Topic;
import cn.bestsort.bbslite.model.TopicExample;
import cn.bestsort.bbslite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TopicController {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    QuestionService questionService;
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
}
