package com.supermart.user.repository;

import com.supermart.user.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount,Integer> {
    public Optional<UserAccount> findUserAccountByEmail(String email);
    public Optional<UserAccount> findUserAccountByUserName(String userName);
}
