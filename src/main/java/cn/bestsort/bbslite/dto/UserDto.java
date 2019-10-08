package cn.bestsort.bbslite.dto;

import cn.bestsort.bbslite.vo.UserCreateVo;
import lombok.Data;

/**
 * @author bestsort
 */
@Data
public class UserDto {
    UserCreateVo userCreateDTO;
    String bio;
    String avatarUrl;
    String token;
}
