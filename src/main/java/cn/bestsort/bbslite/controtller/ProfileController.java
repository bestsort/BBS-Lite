package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.pojo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ProfileController
 * @Description 个人资料控制器
 * @Author bestsort
 * @Date 19-8-30 下午6:21
 * @Version 1.0
 */

@Controller
public class ProfileController{

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          Model model,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        Map<String,String> item = new HashMap<String, String>(4){{
            put("questions","我的提问");
            put("replies","我的回复");
        }};
        /*PagInationVo pagInationDTO = questionService.getPagInationList(page,size, QuestionService.USER,user.getId());
        model.addAttribute("section",action)
            .addAttribute("sectionName",item.get(action))
            .addAttribute("user",user)
            .addAttribute("pagination",pagInationDTO);*/
        return "profile";
    }
}
