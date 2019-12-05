package cn.bestsort.bbslite.vo;

import lombok.Data;

/**
 * TODO
 * @author bestsort
 * @date 19-9-28 下午8:07
 * @version 1.0
 */

@Data
public class FollowVo {
    private Long followBy;
    private Long followTo;
    private String type;
}
