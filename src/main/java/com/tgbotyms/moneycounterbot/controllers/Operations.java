package com.tgbotyms.moneycounterbot.controllers;

import com.tgbotyms.moneycounterbot.model.Operation;
import com.tgbotyms.moneycounterbot.model.OperationTypes;
import com.tgbotyms.moneycounterbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Controller
public class Operations {


    OperationsRepository operationsRepository;
    UserRepository userRepository;

    public void UserCheckAndAdd(String tgUserId, BigDecimal balance) {
        Date operationDate = Calendar.getInstance().getTime();
        Operation operation = new Operation(1L, OperationTypes.INCOME, balance, operationDate);
        try {
            operationsRepository.save(operation);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        User newUser = new User();
        newUser.setTgUserId(tgUserId);
        newUser.setCurrentBalance(balance);
        newUser.setDaysDivision(30);
        newUser.setCalculationDate(operationDate);
        try {
            userRepository.save(newUser);
        }
        catch (Exception exc) {System.out.println(exc.getMessage());}
        System.out.println();
    }

        public void addIncome(String tgUserId, BigDecimal income) {
        }
}
