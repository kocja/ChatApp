package ch.admin.seco.chatserver.service;

import ch.admin.seco.chatserver.data.users.UserEntity;
import ch.admin.seco.chatserver.data.users.UserRepository;
import ch.admin.seco.chatserver.dto.users.CreateUserDto;
import ch.admin.seco.chatserver.dto.users.UpdateUserDto;
import ch.admin.seco.chatserver.dto.users.UserDto;
import ch.admin.seco.chatserver.dto.users.status.Status;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void updateStatus() {
        final Map<Status, Collection<UserEntity>> classifiedUsers = classifyByShouldState(userRepository.findAll());

        final Set<Status> keySet = classifiedUsers.keySet();
        for (Status status : keySet) {
            final Collection<UserEntity> users = classifiedUsers.get(status);
            for (final UserEntity user : users) {
                user.setStatus(status);
                userRepository.save(user);
            }
        }
    }

    public Map<Status, Collection<UserEntity>> classifyByShouldState(final Collection<UserEntity> users) {

        final Collection<UserEntity> onlineUsers = new ArrayList<>();
        final Collection<UserEntity> awayUsers = new ArrayList<>();
        final Collection<UserEntity> offlineUsers = new ArrayList<>();

        final Map<Status, Collection<UserEntity>> userClassification = new HashMap<>();

        Instant offline = Instant.now().minus(60, ChronoUnit.MINUTES);
        Instant away = Instant.now().minus(30, ChronoUnit.MINUTES);

        for (UserEntity user : users) {
            Instant userUpdated = user.getUpdated();
            if (userUpdated.isBefore(away)) {
                if (userUpdated.isAfter(offline)) {
                    awayUsers.add(user);
                } else {
                    offlineUsers.add(user);
                }
            } else {
                onlineUsers.add(user);
            }
        }
        userClassification.put(Status.AWAY, awayUsers);
        userClassification.put(Status.ONLINE, onlineUsers);
        userClassification.put(Status.OFFLINE, offlineUsers);
        return userClassification;
    }
}
