package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.UserDto;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepo;
import ru.practicum.util.UtilService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UtilService utilService;

    @Transactional
    public UserDto addUser(UserDto dto) {
        User user = UserMapper.toUser(dto);
        userRepo.save(user);
        return UserMapper.toUserDto(user);
    }

    @Transactional
    public void deleteUser(long userId) {
        utilService.getUserIfExist(userId);
        userRepo.deleteById(userId);
    }

    public List<UserDto> getUsersByUserIds(List<Long> ids, int from, int size) {
        PageRequest page = UtilService.toPage(from, size);
        if (ids == null) {
            return UserMapper.toUserDtoList(userRepo.findAll(page).toList());
        }
        return UserMapper.toUserDtoList(userRepo.findByIdIn(ids, page));
    }
}