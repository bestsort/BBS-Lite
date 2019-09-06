package cn.bestsort.bbslite.model;

import lombok.Data;

/**
 * @ClassName
 * @Description TODO
 * @Author bestsort
 * @Date 19-8-26 下午5:17
 * @Version 1.0
 */

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
