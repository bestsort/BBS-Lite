package cn.bestsort.community.mapper;

import cn.bestsort.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


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
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(Integer offset, Integer size);

    /**
     * @return 问题总数
     */
    @Select("select count(1) from question")
    Integer count();
}
