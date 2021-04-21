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

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static UserDto mapToDto(final UserEntity userEntity) {
        return new UserDto(userEntity.getId(), userEntity.getNickname(), userEntity.getStatus(), userEntity.getAvatar(), userEntity.getUpdated());
    }

    private static List<UserDto> mapToDto(final List<UserEntity> userEntities) {
        final List<UserDto> users = new ArrayList<>();
        for (final UserEntity userEntity : userEntities) {
            users.add(mapToDto(userEntity));
        }
        return users;
    }

    public List<UserDto> getAllUsers() {
        return mapToDto(userRepository.findAll());
    }

    public UserDto getUserById(final int id) {
        return mapToDto(userRepository.getOne(id));
    }

    public UserDto createUser(CreateUserDto userDto) {
        final UserEntity userEntity = userRepository.save(new UserEntity(userDto.getNickname(), userDto.getStatus(), userDto.getAvatar(), userDto.getUpdated()));
        System.out.println("Create user with ID " + userEntity.getId());
        return mapToDto(userEntity);
    }

    public UserDto updateUser(final int id, UpdateUserDto userDto) {
        final UserEntity userEntity = userRepository.getOne(id);
        if (userDto.getNickname() != null) {
            userEntity.setNickname(userDto.getNickname());
        }
        if (userDto.getStatus() != null) {
            userEntity.setStatus(userDto.getStatus());
        }
        if (userDto.getAvatar() != null) {
            userEntity.setAvatar(userDto.getAvatar());
        }
        System.out.println("Update user with ID " + userEntity.getId());
        return mapToDto(userRepository.save(userEntity));
    }

    public void deleteUserById(final int id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
}
