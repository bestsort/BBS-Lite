package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.pojo.dto.CommentCreateDto;
import cn.bestsort.bbslite.pojo.dto.ResultDto;
import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @ClassName CommentController
 * @Description 评论控制器,用于处理评论(以及对问题的回复)提交
 * @Author bestsort
 * @Date 19-9-13 下午3:51
 * @Version 1.0
 */
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(CommentCreateDto commentCreateDTO,
                       HttpSession session){

        User user = (User)session.getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO,comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentService.insert(comment);
        return ResultDto.okOf();
    }

    @RequestMapping(value = "/comment/{id}",method = RequestMethod.POST)
    public ResultDto comments(@PathVariable(name="id") Long id){
        return null;
    }
}