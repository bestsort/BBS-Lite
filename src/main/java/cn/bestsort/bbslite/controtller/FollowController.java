package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.pojo.model.Follow;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

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

    @ResponseBody
    @PostMapping("/follow")
    public ResultDto follow(
            @RequestParam("followTo") Long id,
            @RequestParam("followType") String typeEnum,
            HttpSession session){

        if(session.getAttribute("user") == null){
            return  new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        User user = (User)session.getAttribute("user");
        Follow follow = new Follow();
        follow.setFollowTo(id);
        follow.setFollowBy(user.getId());
        follow.setType(Objects.requireNonNull(FunctionItem.getCode(FunctionItem.getItem(typeEnum))));
        if(1==followService.insertOrUpdate(follow)){
            return new ResultDto().okOf();
        }
        return new ResultDto().errorOf(CustomizeErrorCodeEnum.SYS_ERROR);
    }
}
