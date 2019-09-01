package cn.bestsort.community.model;

import lombok.Data;

/**
 * @ClassName Question
 * @Description TODO
 * @Author bestsort
 * @Date 19-8-27 下午7:41
 * @Version 1.0
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    public Question(){}
    public Question(String title,String description,String tag,Integer creator,Integer id){
        this.title = title;
        this.tag = tag;
        this.creator = creator;
        this.id = id;
        this.description = description;
    }
}
