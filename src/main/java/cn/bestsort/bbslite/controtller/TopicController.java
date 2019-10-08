package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.pojo.model.Topic;
import cn.bestsort.bbslite.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TopicController {
    @Autowired
    private TopicService topicService;


    @GetMapping("/topic")
    public String topic(Model model){
        List<Topic> topics = topicService.getAll();
        model.addAttribute("topics",topics);
        return "topic";
    }


    @GetMapping("/topic/{id}")
    public String comments(@PathVariable(name="id") Long id,
                           @RequestParam(name="page",defaultValue = "1") Integer page,
                           @RequestParam(name="size",defaultValue = "10") Integer size,
                           Model model){
        Topic topic = topicService.getById(id);
        return "topic";
    }

}
