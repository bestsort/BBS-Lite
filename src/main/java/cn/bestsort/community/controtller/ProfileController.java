package cn.bestsort.community.controtller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName ProfileController
 * @Description TODO
 * @Author bestsort
 * @Date 19-8-30 下午6:21
 * @Version 1.0
 */

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String profile(@PathVariable(name = "action") String action,
                          Model model){
        if ("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
        }
        return "profile";
    }
}
