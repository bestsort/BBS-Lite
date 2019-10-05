package cn.bestsort.bbslite.controtller.post;

import cn.bestsort.bbslite.pojo.dto.ResultDto;
import cn.bestsort.bbslite.pojo.vo.FollowVo;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.service.FollowService;
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
public class FollowPostController {
    @Autowired
    FollowService followService;

    @ResponseBody
    @RequestMapping(value = "/follow",method = RequestMethod.POST)
    public Object follow(FollowVo followCreateDTO,
                         HttpSession session){

        if(session.getAttribute("user") == null){
            return  ResultDto.errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }

        followService.insertOrUpdate(followCreateDTO);
        return ResultDto.okOf();
    }
}
