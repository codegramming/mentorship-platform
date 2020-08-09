package com.tayfurunal.mentorship.mapper;

import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.dto.TopicDto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicDto toDto(Topic topic);

    Topic toEntity(TopicDto topicDto);
}
