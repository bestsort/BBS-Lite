package cn.bestsort.bbslite.dto;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果集,根据构造/参数返回一个 错误代码+错误提示 的对象
 * @author bestsort
 * @date 19-9-13 下午4:46
 * @version 1.0
 */
@Data
public class ResultDto {
    private Integer code;
    private String message;
    private Map<String,Object> extend=new HashMap<>(2);
    public ResultDto errorOf(Integer code, String message){
        ResultDto resultDTO = new ResultDto();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public ResultDto errorOf(CustomizeErrorCodeEnum errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getName());
    }
    public ResultDto okOf(){
        ResultDto resultDTO = new ResultDto();
        resultDTO.setMessage("成功");
        resultDTO.setCode(200);
        return resultDTO;
    }
    public ResultDto okOf(Integer code,String message){
        ResultDto resultDTO = new ResultDto();
        resultDTO.setMessage(message);
        resultDTO.setCode(code);
        return resultDTO;
    }
    public ResultDto addMsg(String key, Object value){
        this.extend.put(key,value);
        return this;
    }
    @Override
    public String toString(){
        return "ResultDto{" +
                "code='" + code + '\'' +
                ",message='" + message + '\''+
                ",extend=" + extend + '}';
    }
    public ResultDto errorOf(CustomizeException e) {
        return new ResultDto().errorOf(e.getCode(),e.getMessage());
    }
}
