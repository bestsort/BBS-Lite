package cn.bestsort.bbslite.vo;

import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.Topic;
import lombok.Data;

import java.util.List;

/**
 * TODO
 * @author bestsort
 * @date 2019/10/12 上午10:08
 * @version 1.0
 */

@Data
public class PublishVo {
    private Article article;
    private List<Topic> topics;
}
