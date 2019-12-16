package cn.bestsort.bbslite.mapper.ext;

/**
 * @author bestsort
 */
public interface TopicExtMapper {
    int updateCountWithVal(Byte id,Long val);
    int updateFollowCount(Byte id,Long val);
}
