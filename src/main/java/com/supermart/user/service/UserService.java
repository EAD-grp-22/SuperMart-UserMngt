package com.supermart.user.service;

import com.supermart.user.dto.*;
import com.supermart.user.model.User;
import com.supermart.user.model.UserAccount;
import com.supermart.user.repository.UserAccountRepository;
import com.supermart.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserMapperService userMapperService;
    private final EncryptionService encryptionService;
    public UserResponse getUserById(Integer id){
        Optional<User> user=userRepository.findById(id);
        if(user!=null){
            return userMapperService.mapToUserResponse(user.get());
        } else {
            return null;
        }
    }

    public List<UserResponse> getUsersByFirstName(String firstName){
        List<User> users=userRepository.findUsersByFirstName(firstName);
        if(users!=null && !users.isEmpty()){
            return userMapperService.mapToUserResponseList(users);
        } else {
            return null;
        }
    }

    public List<UserResponse> getUsersByLastName(String lastName){
        List<User> users=userRepository.findUsersByLastName(lastName);
        if(users!=null && !users.isEmpty()){
            return userMapperService.mapToUserResponseList(users);
        } else {
            return null;
        }
    }

    public UserResponse getUserByEmail(String email){
        Optional<UserAccount> userAccount=userAccountRepository.findUserAccountByEmail(email);
        if (userAccount.isPresent()) {
            User user = userAccount.get().getUser();
            return userMapperService.mapToUserResponse(user);
        } else {
            return null;
        }
    }

    public UserResponse getUserByUserName(String userName){
        Optional<UserAccount> userAccount=userAccountRepository.findUserAccountByUserName(userName);
        if (userAccount.isPresent()) {
            User user = userAccount.get().getUser();
            return userMapperService.mapToUserResponse(user);
        } else {
            return null;
        }
    }

    public String registerUser(RegisterUserRequest registerUserRequest){
        if(userAccountRepository.findUserAccountByEmail(registerUserRequest.getEmail()).isPresent()
                || userAccountRepository.findUserAccountByUserName(registerUserRequest.getUserName()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        UserAccount userAccount=UserAccount.builder()
                .userName(registerUserRequest.getUserName())
                .email(registerUserRequest.getEmail())
                .hashedPassword(encryptionService.encryptPassword(registerUserRequest.getPassword()))
                .role(registerUserRequest.getRole())
                .build();
        User user=User.builder()
                .firstName(registerUserRequest.getFirstName())
                .lastName(registerUserRequest.getLastName())
                .build();
        user.setUserAccount(userAccount);
        userAccount.setUser(user);

        userAccountRepository.save(userAccount);
        userRepository.save(user);

        return "User registered successfully";
    }

    public String updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findUserAccountByUserName(updatePasswordRequest.getUserName());
        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();
            if (encryptionService.verifyPassword(updatePasswordRequest.getCurrentPassword(), userAccount.getHashedPassword())) {
                userAccount.setHashedPassword(encryptionService.encryptPassword(updatePasswordRequest.getNewPassword()));
                userAccountRepository.save(userAccount);
                return "Password updated successfully";
            } else {
                throw new RuntimeException("Current password is incorrect");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public String updateUsername(UpdateUsernameRequest updateUsernameRequest) {
        Optional<UserAccount> userAccountOptional =
                userAccountRepository.findUserAccountByUserName(updateUsernameRequest.getCurrentUserName());

        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();

            if (!userAccountRepository.findUserAccountByUserName(updateUsernameRequest.getNewUsername()).isPresent()) {
                userAccount.setUserName(updateUsernameRequest.getNewUsername());
                userAccountRepository.save(userAccount);
                return "Username updated successfully";
            } else {
                throw new RuntimeException("New username is already in use");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }



    public String removeUser(Integer userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return "User removed successfully";
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public String updateEmail(UpdateEmailRequest updateEmailRequest) {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findUserAccountByUserName(updateEmailRequest.getUserName());
        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();

            if (!userAccountRepository.findUserAccountByEmail(updateEmailRequest.getNewEmail()).isPresent()) {
                userAccount.setEmail(updateEmailRequest.getNewEmail());
                userAccountRepository.save(userAccount);
                return "Email updated successfully";
            } else {
                throw new RuntimeException("New email is already in use");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public boolean checkUserValidity(Integer id){
        return userRepository.existsById(id);
    }

}
