package cn.bestsort.bbslite.exception;

public enum CustomizeErrorCodeEnum  implements CustomizeErrorCodeInterface{



    QUESTION_NOT_FOUND("问题不见了,换一个试试?"),
    OTHER("出 BUG 啦,要不杀个程序员祭天吧?");
    private String message;
    @Override
    public String getMessage(){
        return message;
    }
    CustomizeErrorCodeEnum(String message){
        this.message=message;
    }
}
