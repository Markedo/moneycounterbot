package com.tgbotyms.moneycounterbot.controllers;

import com.tgbotyms.moneycounterbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Long deleteBytgUserId(long tgUserId);
    User findBytgUserId(long tgUserId);
    User findByname(String name);
    User findByemail(String email);
}
