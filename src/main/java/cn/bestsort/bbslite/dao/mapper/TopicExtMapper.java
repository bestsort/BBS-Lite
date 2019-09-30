package cn.bestsort.bbslite.dao.mapper;

import cn.bestsort.bbslite.bean.model.Topic;

public interface TopicExtMapper {
    int incQuestion(Topic record);
    int updateFollowCount(Topic record);
}
