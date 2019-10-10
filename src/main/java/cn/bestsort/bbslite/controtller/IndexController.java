package cn.bestsort.bbslite.controtller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName IndexController
 * @Description 主页面控制器,可处理搜索结果
 * @Author bestsort
 * @Date 19-8-22 上午5:53
 * @Version 1.0
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}