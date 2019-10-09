package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.QuestionQueryDto;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.service.QuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @Description 获取问题列表(可根据搜索内容/话题/分类筛选结果)
     * @param sort 排序关键字
     * @param search 搜索关键字(根据正则搜索标题)
     * @param topic 话题
     * @param tag 标签(根据正则搜索标签)
     * @param size 页面大小(一页所容纳的问题数)
     * @param page 第几页
     * @param categoryVal 话题分类
     * @return 根据条件筛选并进行分页后的问题列表
     */
    @ResponseBody
    @GetMapping("/loadQuestionList")
    public ResultDto getQuestionList(@RequestParam(name = "sortby",defaultValue = "ALL") String sort,
                                     @RequestParam(name = "search",required = false) String search,
                                     @RequestParam(name = "topic",defaultValue = "0") Integer topic,
                                     @RequestParam(name = "tag",required = false) String tag,
                                     @RequestParam(name = "pageSize",defaultValue = "10") Integer size,
                                     @RequestParam(name = "pageNo",defaultValue = "1") Integer page,
                                     @RequestParam(name = "category",defaultValue = "0") Integer categoryVal){
        QuestionQueryDto queryDto = QuestionQueryDto.builder()
                .search(search).category(categoryVal).pageNo(page)
                .tag(tag).pageSize(size).topic(topic).build();
        PageInfo<Question> pageInfo = questionService.getPageBySearch(queryDto);
        return new ResultDto().okOf().addMsg("page",pageInfo);
    }


    @RequestMapping("/")
    public String index() {
        return "index";
    }
}