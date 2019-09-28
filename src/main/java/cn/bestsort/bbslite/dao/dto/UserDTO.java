package cn.bestsort.bbslite.dao.dto;

import cn.bestsort.bbslite.dao.create.UserCreateDTO;
import lombok.Data;

@Data
public class UserDTO {
    UserCreateDTO userCreateDTO;
    String bio;
    String avatarUrl;
    String token;
}
