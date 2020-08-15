package com.tayfurunal.mentorship.mapper;

import com.tayfurunal.mentorship.domain.Mentorship;
import com.tayfurunal.mentorship.dto.MentorshipDto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MentorshipMapper {

    MentorshipMapper INSTANCE = Mappers.getMapper(MentorshipMapper.class);

    MentorshipDto toDto(Mentorship mentorship);

    Mentorship toEntity(MentorshipDto dto);
}
