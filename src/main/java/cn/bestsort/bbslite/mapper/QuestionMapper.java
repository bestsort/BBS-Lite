package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

/**
 * @ClassName QuestionMapper
 * @Description TODO
 * @Author bestsort
 * @Date 19-8-27 下午7:38
 * @Version 1.0
 */

@Mapper
public interface QuestionMapper {

    /**
     * @param question
     */
    @Insert("INSERT INTO question " +
            "(title,description,gmt_create,gmt_modified,creator,tag)" +
            " VALUES " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    /**
     * @param offset
     * @param size
     * @return 问题列表
     */
    @Select("SELECT * FROM question ORDER BY gmt_create DESC LIMIT #{offset},#{size} ")
    List<Question> list(Integer offset, Integer size);

    /**
     * @return 问题总数
     */
    @Select("SELECT COUNT(1) FROM question")
    Integer count();

    @Select("SELECT * FROM question WHERE creator=#{userId} ORDER BY gmt_create DESC LIMIT #{offset},#{size} ")
    List<Question> listByUserId(Integer userId, Integer offset, Integer size);

    @Select("SELECT count(1) FROM question WHERE creator=#{userId}")
    Integer countByUserId(Integer userId);

    @Select("SELECT * FROM question WHERE id=#{id}")
    Question getById(Integer id);

    @Update("UPDATE question SET " +
            "title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} " +
            "WHERE id = #{id}")
    void update(Question question);
}
