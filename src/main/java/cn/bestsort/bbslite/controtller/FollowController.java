package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.pojo.model.Follow;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.FollowService;
import cn.bestsort.bbslite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @ClassName FollowController
 * @Description 获取关于用户/问题/话题的收藏请求
 * @Author bestsort
 * @Date 19-9-29 下午4:43
 * @Version 1.0
 */


@Controller
public class FollowController {
    @Autowired
    FollowService followService;
    @Autowired
    QuestionService questionService;
    @ResponseBody
    @PostMapping("/followQuestion")
    public ResultDto follow(
            @RequestParam("id") Long id,
            @RequestParam("isActive") Boolean isActive,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        boolean active = followService.setFollowCount(id, user.getId(), FunctionItem.QUESTION, isActive);
        questionService.incQuestionFollow(id,isActive?-1L:1L);
        return new ResultDto().okOf()
                .addMsg("isActive", active);
    }
}
