package cn.bestsort.bbslite.exception;

/**
 * @Author bestsort
 * @Description 用户错误代码 接口,用于返回指定的错误提示信息
 */
public interface CustomizeErrorCodeInterface {
    String getMessage();
    Integer getCode();
}
