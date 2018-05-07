package pinto.cleo.userdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pinto.cleo.userdemo.common.dto.ResponseDTO;
import pinto.cleo.userdemo.dto.UserDTO;
import pinto.cleo.userdemo.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Created by cleo on 5/5/18.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
       this.userService =  userService;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable("id") @NotEmpty Integer id){
        return userService.getUser(id);
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("id") @NotEmpty Integer id, @Valid @RequestBody UserDTO user){
        userService.updateUser(id, user);
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<String> createUser(@Valid @RequestBody UserDTO user){
        Integer id = userService.createUser(user);
        return new ResponseDTO<>(String.format("User successfully created. User id is: %s", id));
    }
}
