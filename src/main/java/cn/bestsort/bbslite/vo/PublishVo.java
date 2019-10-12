package cn.bestsort.bbslite.vo;

import cn.bestsort.bbslite.pojo.model.Question;
import cn.bestsort.bbslite.pojo.model.Topic;
import lombok.Data;

import java.util.List;

/**
 * @ClassName PublishVo
 * @Description TODO
 * @Author bestsort
 * @Date 2019/10/12 上午10:08
 * @Version 1.0
 */

@Data
public class PublishVo {
    private Question question;
    private List<Topic> topics;
}
