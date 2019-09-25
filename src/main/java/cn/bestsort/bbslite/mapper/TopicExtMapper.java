package cn.bestsort.bbslite.mapper;

import cn.bestsort.bbslite.model.Topic;

public interface TopicExtMapper {
    int incQuestion(Topic record);
    int incFollow(Topic record);
}
