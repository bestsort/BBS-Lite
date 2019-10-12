package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.CommentDto;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.pojo.model.Comment;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.CommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/loadComment",method = RequestMethod.GET)
    public ResultDto get(@RequestParam(name = "id") Long id){
        PageInfo<CommentDto> commentDtos = commentService.listByQuestionId(id,1,5);
        return new ResultDto().okOf();
    }

    @RequestMapping(value = "/comment/{id}",method = RequestMethod.POST)
    public ResultDto comments(@PathVariable(name="id") Long id){
        return null;
    }
}