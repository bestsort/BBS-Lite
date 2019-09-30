package cn.bestsort.bbslite.exception;

import cn.bestsort.bbslite.enums.MessageInterface;

/**
 * @ClassName CustomizeException
 * @Description
 * @Author bestsort
 * @Date 19-9-10 下午3:30
 * @Version 1.0
 */

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(MessageInterface errorCode){
        this.message = errorCode.getName();
        this.code = errorCode.getCode();
    }
    @Override
    public String getMessage(){
        return message;
    }
    public Integer getCode(){return code;}
}
