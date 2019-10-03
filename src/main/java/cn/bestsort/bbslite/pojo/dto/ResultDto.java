package cn.bestsort.bbslite.pojo.dto;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import lombok.Data;

/**
 * @ClassName ResultDto
 * @Description 结果集,根据构造/参数返回一个 错误代码+错误提示 的对象
 * @Author bestsort
 * @Date 19-9-13 下午4:46
 * @Version 1.0
 */
@Data
public class ResultDto {
    private Integer code;
    private String message;
    public static ResultDto errorOf(Integer code, String message){
        ResultDto resultDTO = new ResultDto();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDto errorOf(CustomizeErrorCodeEnum errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getName());
    }
    public static ResultDto okOf(){
        ResultDto resultDTO = new ResultDto();
        resultDTO.setMessage("成功");
        resultDTO.setCode(200);
        return resultDTO;
    }

    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }
}
