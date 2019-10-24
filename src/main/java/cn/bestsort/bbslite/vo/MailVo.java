package cn.bestsort.bbslite.vo;

import lombok.Data;

/**
 * @ClassName MailVo
 * @Description TODO
 * @Author bestsort
 * @Date 2019/10/24 下午3:28
 * @Version 1.0
 */
@Data
public class MailVo {
    public String sendTo;
    public String sendText;
    public String sendSubject;
    /* 最多重复发送3次 */
    public int times = 3;
}
