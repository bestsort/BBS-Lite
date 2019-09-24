package cn.bestsort.bbslite.dto;

import lombok.Data;

@Data
public class UserDTO {
    UserCreateDTO userCreateDTO;
    String bio;
    String avatarUrl;
    String token;
}
