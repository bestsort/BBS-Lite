package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.CommentCreateDTO;
import cn.bestsort.bbslite.dto.GithubUser;
import cn.bestsort.bbslite.dto.ResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName SignUpController
 * @Description TODO 用户注册
 * @Author bestsort
 * @Date 19-9-23 下午8:13
 * @Version 1.0
 */

@Controller
public class SignUpController {

    @ResponseBody
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public Object signUp(@RequestBody GithubUser githubUser,
                         HttpServletRequest request) {
        return ResultDTO.okOf();
    }
}