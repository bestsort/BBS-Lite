package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.pojo.model.Topic;

public interface TopicExtMapper {
    int updateCountWithVal(Byte id,Long val);
    int updateFollowCount(Byte id,Long val);
}
