package cn.bestsort.bbslite.mapper.ext;

import cn.bestsort.bbslite.pojo.model.User;


/**
 * @author bestsort
 */
public interface UserExtMapper {

    User selectSimpleInfoById(Long id);
    Long insertUserExt(User user);
}
