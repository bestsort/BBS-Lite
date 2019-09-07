package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.QuestionDTO;
import cn.bestsort.bbslite.mapper.QuestionMapper;
import cn.bestsort.bbslite.model.Question;
import cn.bestsort.bbslite.model.User;
import cn.bestsort.bbslite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName PublishController
 * @Description TODO
 * @Author bestsort
 * @Date 19-8-26 下午7:53
 * @Version 1.0
 */

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){

        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle())
                .addAttribute("tag",question.getTag())
                .addAttribute("description",question.getDescription())
                .addAttribute("id",question.getId());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Integer id,
            HttpServletRequest request,
            Model model){

        System.out.println(title);
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
                 .addAttribute("description",description);
        User user = (User)request.getSession().getAttribute("user");;


        if(user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
