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
    private Integer page;
    private Integer startPage;
    private Integer pageLenth;
    private Integer focusPage;
    private Integer totalPage;
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        totalPage = totalCount/size + (totalCount%size==0? 0 : 1);
        Integer pagination = 3;
        //页面跳转按钮起点为 page-3
        this.page = page;
        startPage = Math.max(page-pagination,1);
        //页面跳转按钮终点为 page+3
        pageLenth = Math.min(page-startPage+pagination,totalPage-startPage);
        if(startPage == 1 && pageLenth != pagination * 2){
            focusPage = startPage+pageLenth-pagination;
        }else if(pageLenth != pagination*2 && startPage != 1){
            focusPage = Math.min(startPage+pagination,totalPage);
        }
        else{
            focusPage = startPage+pageLenth/2;
        }
        System.out.println("pageFcus=="+focusPage);
        System.out.println("pageLenth=="+pageLenth);
        System.out.println("pageStart=="+startPage);
        System.out.println("total==" + totalPage);
        System.out.println("page=="+page);
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
