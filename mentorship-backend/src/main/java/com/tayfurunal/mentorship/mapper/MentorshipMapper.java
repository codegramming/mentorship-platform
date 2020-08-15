package com.tayfurunal.mentorship.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MentorshipMapper {

    MentorshipMapper INSTANCE = Mappers.getMapper(MentorshipMapper.class);

    MentorshipDto toDto(Mentorship mentorship);

    Mentorship toEntity(MentorshipDto dto);
}
