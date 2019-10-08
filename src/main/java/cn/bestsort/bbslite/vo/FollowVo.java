package cn.bestsort.bbslite.vo;

import lombok.Data;

/**
 * @ClassName FollowVO
 * @Description
 * @Author bestsort
 * @Date 19-9-28 下午8:07
 * @Version 1.0
 */

@Data
public class FollowVo {
    private Long followBy;
    private Long followTo;
    private String type;
}
