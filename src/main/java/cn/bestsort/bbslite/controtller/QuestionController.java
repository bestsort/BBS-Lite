package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.bean.model.Follow;
import cn.bestsort.bbslite.bean.model.Question;
import cn.bestsort.bbslite.bean.model.User;
import cn.bestsort.bbslite.dao.create.FollowCreateDTO;
import cn.bestsort.bbslite.dao.dto.CommentDTO;
import cn.bestsort.bbslite.dao.dto.QuestionDTO;
import cn.bestsort.bbslite.dao.dto.ResultDTO;
import cn.bestsort.bbslite.dao.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.dao.mapper.ThumbUpMapper;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FollowEnum;
import cn.bestsort.bbslite.service.CommentService;
import cn.bestsort.bbslite.service.FollowService;
import cn.bestsort.bbslite.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

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
        QuestionDTO questionDTO = questionService.getById(id);
        //增加阅读数
        Question question = new Question();
        question.setId(id);
        questionExtMapper.incView(question);
        List<CommentDTO> comments = commentService.listByQuestionId(id);
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionDTO);
        return "question";
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/question/{id}",method = RequestMethod.POST)
    public Object post(@PathVariable(name = "id") Long id){

        return ResultDTO.okOf();
    }
}
