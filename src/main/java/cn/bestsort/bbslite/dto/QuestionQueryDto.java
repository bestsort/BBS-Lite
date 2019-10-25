package cn.bestsort.bbslite.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @ClassName QuestionQueryDto
 * @Description
 * @Author bestsort
 * @Date 2019/10/7 下午12:03
 * @Version 1.0
 */

@Builder
@Getter
public class QuestionQueryDto {
    private String search;
    private Integer topic;
    private String tag;
    private long beginTime;
    private long endTime;

    private Integer category;
    private Integer pageSize;
    private Integer pageNo;
}
