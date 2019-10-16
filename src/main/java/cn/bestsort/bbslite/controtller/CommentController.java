package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.vo.CommentVo;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.CommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    @GetMapping("/loadComment")
    public ResultDto get(@RequestParam(name = "id") Long id,
                         HttpSession session){
        User user = (User)session.getAttribute("user");
        PageInfo<CommentVo> commentDtos = commentService.listByQuestionId(id,1,5);
        List<CommentVo> list = commentDtos.getList();
        for (Object vo : list){

        }
        return new ResultDto().okOf()
                .addMsg("comments",commentDtos.getList());
    }


    @ResponseBody
    @PostMapping("/commitComment")
    public ResultDto commitComment(@RequestParam(name = "questionId") Long questionId,
                                   @RequestParam(name = "content") String content,

                                   HttpSession session){

        return null;
    }
}