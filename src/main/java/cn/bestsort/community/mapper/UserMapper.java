package cn.bestsort.community.mapper;

import cn.bestsort.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author bestsort
 */
@Mapper
public interface UserMapper {
    /**
     * @param user
     * @Description 将用户插入到数据库中
     */
    @Insert("INSERT INTO user " +
            "(account_id,name,token,gmt_create,gmt_modified,avatar_url) " +
            "VALUES (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    /**
     * @param token
     * @return
     */
    @Select("SELECT * FROM user WHERE token=#{token}")
    User findByToken(String token);

    /**
     * @param id
     * @return
     */
    @Select("SELECT * FROM user WHERE id=#{id}")
    User findById(Integer id);

    @Select("SELECT * FROM user WHERE account_id=#{accountId}")
    User findByAccount(String accountId);

    @Update("UPDATE user SET " +
            "gmt_modified=#{gmtModified} ,name=#{name},token=#{token},avatar_url=#{avatarUrl}" +
            "WHERE account_id=#{accountId}")
    void update(User dbUser);
}