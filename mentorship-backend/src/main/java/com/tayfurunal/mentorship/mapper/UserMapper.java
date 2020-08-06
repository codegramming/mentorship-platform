package com.tayfurunal.mentorship.mapper;

import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.dto.UserDto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
