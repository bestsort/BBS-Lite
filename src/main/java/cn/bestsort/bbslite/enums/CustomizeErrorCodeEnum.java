package cn.bestsort.bbslite.enums;

import cn.bestsort.bbslite.exception.CustomizeErrorCodeInterface;

public enum CustomizeErrorCodeEnum  implements CustomizeErrorCodeInterface {


    COMMENT_NOT_FOUND(6,"你操作的评论不存在了,换一个试试?"),
    TYPE_PARAM_WRONG(5,"评论类型错误或不存在"),
    NO_LOGIN(4,"未登录不能进行评论,请登录"),
    TARGET_PAI_NOT_FOUND(3,"未选中任何问题或回复进行评论"),
    QUESTION_NOT_FOUND(2,"问题不见了,换一个试试?"),
    SYS_ERROR(1,"出 BUG 啦,要不杀个程序员祭天吧?"),

    ;
    private String message;
    private Integer code;

    @Override
    public String getMessage(){
        return message;
    }
    @Override
    public Integer getCode(){
        return code;
    }
    CustomizeErrorCodeEnum(Integer code, String message){
        this.message=message;
        this.code = code;
    }

}
