package cn.bestsort.bbslite.enums;

/**
 * @author bestsort
 */

public enum CustomizeErrorCodeEnum  implements MessageInterface {
    COMMENT_NOT_FOUND(404,"你操作的评论不存在了,换一个试试?"),
    TYPE_PARAM_WRONG(404,"评论类型错误或不存在"),
    TARGET_PAI_NOT_FOUND(404,"未选中任何问题或回复进行评论"),
    ARTICLE_NOT_FOUND(404,"问题不见了,换一个试试?"),
    URL_NOT_FOUND(404,"你访问的链接不在了,换一个试试吧"),
    USER_EXITED(403,"该账户已存在"),
    NO_WAY(403,"听话,不要乱跑哟!"),
    ACCOUNT_OR_PASSWORD_ERROR(403,"账号或密码有误, 请检查"),
    NO_LOGIN(401,"你还没有登录呢"),
    USER_ERROR(400,"你请求姿势有问题,换一个试试?"),

    SYS_ERROR(502,"出 BUG 啦,要不杀个程序员祭天吧?")
    ;
    private String name;
    private Integer code;

    @Override
    public String getName(){
        return name;
    }
    @Override
    public Integer getCode(){
        return code;
    }
    CustomizeErrorCodeEnum(Integer code, String message){
        this.name =message;
        this.code = code;
    }

}
