package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.QuestionService;
import cn.bestsort.bbslite.service.ThumbUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName ThumbQuestion
 * @Description
 * @Author bestsort
 * @Date 2019/10/14 下午2:17
 * @Version 1.0
 */

@Controller
public class ThumbUpController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ThumbUpService thumbUpService;
    @ResponseBody
    @PostMapping("/thumbUpQuestion")
    public ResultDto thumbUpQuestion(@RequestParam("id") Long id,
                                     @RequestParam("isActive") Boolean isActive,
                                     HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        boolean active = thumbUpService.setThumbUpCount(id,user.getId(), FunctionItem.QUESTION,isActive);
        questionService.incQuestionLike(id,isActive?-1L:1L);
        return new ResultDto().okOf()
                .addMsg("isActive",active);
    }
}
