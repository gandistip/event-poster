package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.UserDto;
import ru.practicum.dto.UserShortDto;
import ru.practicum.model.User;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }

    public List<UserDto> toUserDtoList(List<User> users) {
        return users.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}