package cn.bestsort.community.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName PagInationDTO
 * @Description TODO
 * @Author bestsort
 * @Date 19-8-29 下午10:52
 * @Version 1.0
 */

@Data
public class PagInationDTO {
    @Autowired
    private List<QuestionDTO> questions;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer startPage;
    private Integer pageLenth;
    private Integer focusPage;
    private Integer totalPage;
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        totalPage = Math.max(1,totalCount/size + (totalCount%size==0? 0 : 1));
        Integer pagination = Math.min(3,Math.max(1,totalPage-1));
        //页面跳转按钮起点为 page-3
        startPage = Math.max(page-pagination,1);
        //页面跳转按钮终点为 page+3
        pageLenth = Math.max(0,Math.min(page-startPage+pagination,totalPage-startPage));
        focusPage = page;
        focusPage = Math.min(totalPage,focusPage);
        focusPage = Math.max(1,focusPage);
        //是否显示上一页按钮
        showPrevious = page!=1;
        //是否显示下一页按钮
        showNext = !page.equals(totalPage);
        //是否显示第一页按钮
        showFirstPage = startPage!=1;
        //是否展示最后一页
        showEndPage = (pageLenth+startPage)!=totalPage;

    }
}
