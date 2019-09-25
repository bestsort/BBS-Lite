package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.mapper.TopicMapper;
import cn.bestsort.bbslite.model.Topic;
import cn.bestsort.bbslite.model.TopicExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TopicController {
    @Autowired
    TopicMapper topicMapper;
    @GetMapping("/topic")
    public String topic(Model model){
        List<Topic> topics = topicMapper.selectByExample(new TopicExample());
        model.addAttribute("topics",topics);
        return "topic";
    }
}
