package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.pojo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ProfileController
 * @Description 个人资料控制器
 * @Author bestsort
 * @Date 19-8-30 下午6:21
 * @Version 1.0
 */

@RestController
public class ProfileController{

    @GetMapping("/profile/{action}")
    public ResultDto profile(@PathVariable(name = "action") String action,
                             @RequestParam(name = "id") Long id,
                             @RequestParam(name="page",defaultValue = "1") Integer page,
                             @RequestParam(name="size",defaultValue = "5") Integer size,
                             HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null){
            throw new CustomizeException(CustomizeErrorCodeEnum.USER_ERROR);
        }
        Map<String,String> item = new HashMap<String, String>(4){{
            put("questions","我的提问");
            put("replies","我的回复");
            put("follow","我的收藏");
            put("user","关于我");
        }};

        return null;
    }
}
