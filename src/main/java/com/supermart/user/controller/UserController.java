package com.supermart.user.controller;

import com.supermart.user.dto.*;
import com.supermart.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("id/{id}")
    public UserResponse getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @GetMapping("first-name/{first-name}")
    public List<UserResponse> getUsersByFirstName(@PathVariable("first-name") String firstName){
        return userService.getUsersByFirstName(firstName);
    }

    @GetMapping("last-name/{last-name}")
    public List<UserResponse> getUsersByLastName(@PathVariable("last-name") String lastName){
        return userService.getUsersByLastName(lastName);
    }

    @GetMapping("email/{email}")
    public UserResponse getUserByEmail(@PathVariable("email") String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("user-name/{user-name}")
    public UserResponse getUserByUserName(@PathVariable("user-name") String userName){
        return userService.getUserByUserName(userName);
    }

    @GetMapping("/validity/{id}")
    public boolean checkUserValidity(@PathVariable("id") Integer id){
        return userService.checkUserValidity(id);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        return userService.registerUser(registerUserRequest);
    }

    @PatchMapping("/update/password")
    public String updatePassword(UpdatePasswordRequest updatePasswordRequest){
        return userService.updatePassword(updatePasswordRequest);
    }

    @PatchMapping("/update/user-name")
    public String updateUsername(UpdateUsernameRequest updateUsernameRequest){
        return userService.updateUsername(updateUsernameRequest);
    }

    @PatchMapping("/update/user-name")
    public String updateEmail(UpdateEmailRequest updateEmailRequest){
        return userService.updateEmail(updateEmailRequest);
    }

    @DeleteMapping
    public String removeUser(Integer userId){
        return userService.removeUser(userId);
    }



}
