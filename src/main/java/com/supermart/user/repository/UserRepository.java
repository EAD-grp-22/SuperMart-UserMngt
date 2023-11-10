package com.supermart.user.repository;

import com.supermart.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    public List<User> findUsersByFirstName(String firstName);
    public List<User> findUsersByLastName(String lastName);
}
