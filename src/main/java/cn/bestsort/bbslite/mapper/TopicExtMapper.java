package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.pojo.model.Topic;

public interface TopicExtMapper {
    int incQuestion(Topic record);
    int updateFollowCount(Topic record);
}
