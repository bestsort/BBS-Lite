package cn.bestsort.bbslite.pojo.model;

import lombok.Data;

/**
 * @author bestsort
 */
@Data
public class Comment {
    protected Long id;
    protected Long commentById;
    protected String content;
    protected Long gmtModified;
    protected Long gmtCreate;
}
