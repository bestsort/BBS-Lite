package cn.bestsort.bbslite.vo;

import lombok.Data;

/**
 * TODO
 * @author bestsort
 * @date 2019/10/24 下午3:28
 * @version 1.0
 */
@Data
public class MailVo {
    public String sendTo;
    public String sendText;
    public String sendSubject;
    /**
     *  最多重复发送3次
     */
    public int times = 3;
}
