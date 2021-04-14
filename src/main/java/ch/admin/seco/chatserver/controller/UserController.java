package ch.admin.seco.chatserver.controller;

import ch.admin.seco.chatserver.dto.users.CreateUserDto;
import ch.admin.seco.chatserver.dto.users.UpdateUserDto;
import ch.admin.seco.chatserver.dto.users.UserDto;
import ch.admin.seco.chatserver.service.UserService;
import ch.admin.seco.chatserver.websocket.WebSocketController;
import org.springframework.web.bind.annotation.*;

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
    public UserDto createUser(@RequestBody CreateUserDto userDto) {
        webSocketController.sendPayload("user_added", userService.createUser(userDto));
        return null;
    }

    @PutMapping("{id}")
    public UserDto updateUser(@PathVariable final int id, @RequestBody UpdateUserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable final int id) {
        userService.deleteUserById(id);
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }
}
