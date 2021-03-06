package ch.admin.seco.chatserver.controller;

import ch.admin.seco.chatserver.dto.users.CreateUserDto;
import ch.admin.seco.chatserver.dto.users.UpdateUserDto;
import ch.admin.seco.chatserver.dto.users.UserDto;
import ch.admin.seco.chatserver.dto.users.status.Status;
import ch.admin.seco.chatserver.service.UserService;
import ch.admin.seco.chatserver.websocket.WebSocketController;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final WebSocketController webSocketController;


    public UserController(final UserService userService, final WebSocketController webSocketController) {
        this.userService = userService;
        this.webSocketController = webSocketController;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable final int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDto createUser(@RequestBody CreateUserDto createUserDto) {
        UserDto userDto = userService.createUser(createUserDto);
        webSocketController.sendPayload("user_added", userDto);
        return userDto;
    }

    @PutMapping("{id}")
    public UserDto updateUser(@PathVariable final int id, @RequestBody UpdateUserDto updateUserDto) {
        UserDto userDto = userService.updateUser(id, updateUserDto);
        webSocketController.sendPayload("user_updated", userDto);
        return userDto;
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable final int id) {
        userService.deleteUserById(id);
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @PostMapping("updateStatus")
    public void updateStatus()
    {
        userService.updateStatus();
    }
}
