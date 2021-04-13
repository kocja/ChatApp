package ch.admin.seco.chatserver.service;

import ch.admin.seco.chatserver.data.users.UserEntity;
import ch.admin.seco.chatserver.data.users.UserRepository;
import ch.admin.seco.chatserver.dto.users.CreateUserDto;
import ch.admin.seco.chatserver.dto.users.UpdateUserDto;
import ch.admin.seco.chatserver.dto.users.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository USER_REPOSITORY;

    public UserService(final UserRepository userRepository) {
        this.USER_REPOSITORY = userRepository;
    }

    private static UserDto mapToDto(final UserEntity USER_ENTITY) {
        return new UserDto(USER_ENTITY.getId(), USER_ENTITY.getNickname(), USER_ENTITY.getStatus(), USER_ENTITY.getAvatar());
    }

    private static List<UserDto> mapToDto(final List<UserEntity> userEntities) {
        final List<UserDto> USERS = new ArrayList<>();
        for (final UserEntity USER_ENTITY : userEntities) {
            USERS.add(mapToDto(USER_ENTITY));
        }
        return USERS;
    }

    public List<UserDto> getAllUsers() {
        return mapToDto(USER_REPOSITORY.findAll());
    }

    public UserDto getUserById(final int id) {
        return mapToDto(USER_REPOSITORY.getOne(id));
    }

    public UserDto createUser(CreateUserDto userDto) {
        final UserEntity userEntity = USER_REPOSITORY.save(new UserEntity(userDto.getNickname(), userDto.getStatus(), userDto.getAvatar()));
        return mapToDto(userEntity);
    }

    public UserDto updateUser(final int id, UpdateUserDto userDto) {
        final UserEntity userEntity = USER_REPOSITORY.getOne(id);
        if (userDto.getNickname() != null) {
            userEntity.setNickname(userDto.getNickname());
        }
        if (userDto.getStatus() != null) {
            userEntity.setStatus(userDto.getStatus());
        }
        if (userDto.getAvatar() != null) {
            userEntity.setAvatar(userDto.getAvatar());
        }
        return mapToDto(USER_REPOSITORY.save(userEntity));
    }

    public void removeUser(final int id) {
        USER_REPOSITORY.deleteById(id);
    }
}
