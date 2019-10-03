package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.pojo.dto.PagInationDto;
import cn.bestsort.bbslite.service.PagInationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName IndexController
 * @Description 主页面控制器,可处理搜索结果
 * @Author bestsort
 * @Date 19-8-22 上午5:53
 * @Version 1.0
 */

@Controller
public class IndexController {

    @Autowired
    private PagInationService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "10") Integer size,
                        @RequestParam(name = "search",defaultValue = "") String search){
        PagInationDto pagination = questionService.listBySearch(search,page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}