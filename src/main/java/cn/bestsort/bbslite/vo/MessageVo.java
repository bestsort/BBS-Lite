package cn.bestsort.bbslite.vo;

import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.pojo.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @author bestsort
 */
@Builder
@Getter
public class MessageVo {
    User sendBy;
    long sendToId;
    FunctionItem item;
    String title;
    long gmtCreate;
    boolean isRead;
}
