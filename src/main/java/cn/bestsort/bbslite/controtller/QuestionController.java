package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.dto.CommentDto;
import cn.bestsort.bbslite.pojo.dto.QuestionDto;
import cn.bestsort.bbslite.pojo.dto.ResultDto;
import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.service.CommentService;
import cn.bestsort.bbslite.service.FollowService;
import cn.bestsort.bbslite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private CommentService commentService;

    @Autowired
    FollowService followService;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model){
        QuestionDto questionDTO = questionService.getById(id);
        //增加阅读数
        Question question = new Question();
        question.setId(id);
        questionExtMapper.incView(question);
        List<CommentDto> comments = commentService.listByQuestionId(id);
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionDTO);
        return "question";
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/question/{id}",method = RequestMethod.POST)
    public Object post(@PathVariable(name = "id") Long id){

        return ResultDto.okOf();
    }
}
