package cn.bestsort.bbslite.util.advice;

import cn.bestsort.bbslite.dao.dto.ResultDTO;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName CustomizeExceptionHandler
 * @Description 根据传入的异常返回不同的错误提示并决定是否跳转到错误页面
 * @Author bestsort
 * @Date 19-9-10 下午2:40
 * @Version 1.0
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e,
                  Model model,
                  HttpServletRequest request,
                  HttpServletResponse response) {
        String contentType = request.getContentType();
        String type = "application/json";
        //获取错误信息
        //如果为 JSON 则返回至当前页面(问题发布页面/评论页面会用到)
        if(type.equals(contentType)){
            ResultDTO result;
            if(e instanceof CustomizeException){
                result =  ResultDTO.errorOf((CustomizeException) e);
            }
            else{
                result =  ResultDTO.errorOf(CustomizeErrorCodeEnum.SYS_ERROR);
            }
            try {
                response.setContentType(type);
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(result));
                writer.close();
            }
            catch (IOException ignored){}
            return null;
        }
        //否则跳转至错误信息页面(error.html)
        else{
            model.addAttribute("message",
                e instanceof CustomizeException?
                e.getMessage():
                CustomizeErrorCodeEnum.SYS_ERROR.getMessage());
            return new ModelAndView("error");
        }
    }
}