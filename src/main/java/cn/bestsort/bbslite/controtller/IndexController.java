package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.QuestionQueryDto;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.service.QuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    private QuestionService questionService;

    /**
     * @Description 获取问题列表
     * @param sort
     * @param search
     * @param topic
     * @param tag
     * @param size
     * @param page
     * @param categoryVal
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/loadQuestionList")
    public ResultDto getQuestionList(@RequestParam(name = "sortby",defaultValue = "ALL") String sort,
                                     @RequestParam(name = "search",required = false) String search,
                                     @RequestParam(name = "topic",defaultValue = "0") Integer topic,
                                     @RequestParam(name = "tag",required = false) String tag,
                                     @RequestParam(name = "size",defaultValue = "10") Integer size,
                                     @RequestParam(name = "page",defaultValue = "1") Integer page,
                                     @RequestParam(name = "category",defaultValue = "0") Integer categoryVal,
                                     HttpServletRequest request){
        QuestionQueryDto queryDto = QuestionQueryDto.builder()
                .search(search).category(categoryVal).pageNo(page)
                .tag(tag).pageSize(size).topic(topic).build();
        PageInfo<Question> pageInfo = questionService.getPageBySearch(queryDto);
        return new ResultDto().okOf().addMsg("page",pageInfo);
    }


    @RequestMapping("/")
    public String index(@RequestParam(value = "tag", required = false) String tag,
                        @RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "category", defaultValue = "0") String category,
                        Map<String, Object> map) {
        map.put("tag", tag);
        map.put("search", search);
        map.put("category", category);
        return "index";
    }
}