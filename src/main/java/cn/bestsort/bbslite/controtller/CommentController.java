package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.CommentDTO;
import cn.bestsort.bbslite.dto.ResultDTO;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.mapper.CommentMapper;
import cn.bestsort.bbslite.model.Comment;
import cn.bestsort.bbslite.model.User;
import cn.bestsort.bbslite.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CommentController
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-13 下午3:51
 * @Version 1.0
 */
@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){


        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setPid(commentDTO.getPid());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}