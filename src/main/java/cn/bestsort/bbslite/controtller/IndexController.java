package cn.bestsort.bbslite.controtller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页面控制器,可处理搜索结果
 * @author bestsort
 * @date 19-8-22 上午5:53
 * @version 1.0
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}