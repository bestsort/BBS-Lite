package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.PagInationDTO;
import cn.bestsort.bbslite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author bestsort
 * @Date 19-8-22 上午5:53
 * @Version 1.0
 */

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",defaultValue = "") String search){
        PagInationDTO pagination = questionService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}