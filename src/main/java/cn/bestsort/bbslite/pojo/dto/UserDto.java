package cn.bestsort.bbslite.pojo.dto;

import cn.bestsort.bbslite.pojo.vo.UserCreateVo;
import lombok.Data;

@Data
public class UserDto {
    UserCreateVo userCreateDTO;
    String bio;
    String avatarUrl;
    String token;
}
