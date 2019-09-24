package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.*;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.model.User;
import cn.bestsort.bbslite.model.UserExample;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @ClassName SignUpController
 * @Description TODO 用户注册
 * @Author bestsort
 * @Date 19-9-23 下午8:13
 * @Version 1.0
 */

@Controller
public class SignUpController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/sign-up")
    public String signUp(){
        return "sign-up";
    }

    @ResponseBody
    @RequestMapping(value = "/sign-up",method = RequestMethod.POST)
    public Object post(@RequestBody UserCreateDTO userCreateDTO,
                       HttpServletRequest request) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(userCreateDTO.getAccountId());

        if (! userMapper.selectByExample(userExample).isEmpty()){
            throw new CustomizeException(CustomizeErrorCodeEnum.USER_EXITED);
        }

        User user = new User();
        BeanUtils.copyProperties(userCreateDTO,user);

        userMapper.insertSelective(user);
        return ResultDTO.okOf();
    }
}