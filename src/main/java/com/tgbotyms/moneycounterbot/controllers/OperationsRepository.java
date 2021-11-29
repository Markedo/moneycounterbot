package com.tgbotyms.moneycounterbot.controllers;

import com.tgbotyms.moneycounterbot.model.Operation;
import com.tgbotyms.moneycounterbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationsRepository extends JpaRepository<Operation, Long> {
    void deleteAllBytgUserId(long tgUserId);
}
