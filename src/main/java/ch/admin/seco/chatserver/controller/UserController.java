package ch.admin.seco.chatserver.controller;

import ch.admin.seco.chatserver.dto.users.CreateUserDto;
import ch.admin.seco.chatserver.dto.users.UpdateUserDto;
import ch.admin.seco.chatserver.dto.users.UserDto;
import ch.admin.seco.chatserver.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserDto getUserById (@PathVariable final int id){
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDto createUser(@RequestBody CreateUserDto userDto){
        return userService.createUser(userDto);
    }

    @PutMapping("{id}")
    public UserDto updateUser(@PathVariable final int id, @RequestBody UpdateUserDto userDto){
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("{id}")
    public void removeUser(@PathVariable final int id){
        userService.removeUser(id);
    }
}
