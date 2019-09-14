package cn.bestsort.bbslite.exception;

/**
 * @ClassName CustomizeException
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-10 下午3:30
 * @Version 1.0
 */

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(CustomizeErrorCodeInterface errorCode){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }
    @Override
    public String getMessage(){
        return message;
    }
    public Integer getCode(){return code;}
}
