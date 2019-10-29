package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.MessageEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @GetMapping("/message")
    public ResultDto getMessage(@RequestParam("type") MessageEnum type,
                                HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            throw new CustomizeException(CustomizeErrorCodeEnum.USER_ERROR);
        }
        List message = messageService.getListByUser(user.getId(),type);
        return new ResultDto().okOf().addMsg("messges",message);
    }
}
