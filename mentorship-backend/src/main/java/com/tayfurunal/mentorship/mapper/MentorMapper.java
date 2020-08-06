package com.tayfurunal.mentorship.mapper;

import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.dto.MentorDto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MentorMapper {

    MentorMapper INSTANCE = Mappers.getMapper(MentorMapper.class);

    MentorDto toDto(Mentor mentor);

    Mentor toEntity(MentorDto dto);
}
