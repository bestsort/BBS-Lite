package cn.bestsort.bbslite.advice;

import cn.bestsort.bbslite.exception.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName CustomizeExceptionHandler
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-10 下午2:40
 * @Version 1.0
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {
        if ((e instanceof CustomizeException)) {
            model.addAttribute("message", e.getMessage());
        } else {
            model.addAttribute("message", CustomizeErrorCodeEnum.OTHER.getMessage());
        }
        return new ModelAndView("error");
    }
}