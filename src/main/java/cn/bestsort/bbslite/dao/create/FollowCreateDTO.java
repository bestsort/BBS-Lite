package cn.bestsort.bbslite.dao.create;

import lombok.Data;

/**
 * @ClassName FollowCreateDTO
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-28 下午8:07
 * @Version 1.0
 */

@Data
public class FollowCreateDTO {
    private Long followBy;
    private Long followTo;
    private Byte type;
}
