package com.tgbotyms.moneycounterbot.controllers;

import com.tgbotyms.moneycounterbot.model.Operation;
import com.tgbotyms.moneycounterbot.model.OperationTypes;
import com.tgbotyms.moneycounterbot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/*
This class contains all commands performed by the program
*/

@Service
@Slf4j
public class Operations {

    @Autowired
    OperationsRepository operationsRepository;

    @Autowired
    UserRepository userRepository;

    public void UserCheckAndAdd(long tgUserId) {
        if(userRepository.findBytgUserId(tgUserId) == null) {
            userRepository.save(new User(tgUserId));
            log.info("A new user with " + tgUserId + " has been added");
        }
    }
        @Transactional
        public void addIncome(long tgUserId, BigDecimal income) {
            //Saving the operation
            Operation operation = new Operation(tgUserId, OperationTypes.INCOME, income, Calendar.getInstance().getTime());
            operationsRepository.save(operation);
            log.debug("addIncome operation has been conducted by the user" + tgUserId);
            //Updating user information
            User user = userRepository.findBytgUserId(tgUserId);
            user.setCurrentBalance(user.getCurrentBalance().add(income));
            userRepository.save(user);
            log.debug("User " + tgUserId + " information has been updated after addIncome operation");
        }

    public void addExpense(long tgUserId, BigDecimal expense) {
        //Saving the operation
        Operation operation = new Operation(tgUserId, OperationTypes.EXPENSE,expense, Calendar.getInstance().getTime());
        operationsRepository.save(operation);
        log.debug("addExpense operation has been conducted by the user" + tgUserId);
        //Updating user information
        User user = userRepository.findBytgUserId(tgUserId);
        user.setCurrentBalance(user.getCurrentBalance().subtract(expense));
        userRepository.save(user);
        log.debug("User " + tgUserId + " information has been updated after addExpense operation");
    }

    private void daysLeftActualisation(long tgUserId) {
        User user = userRepository.findBytgUserId(tgUserId);
        LocalDate calculationDate = user.getCalculationDate();
        LocalDate currentDate = LocalDate.now();
        int daysBetweenDates = (int) ChronoUnit.DAYS.between(calculationDate, currentDate);
        log.debug("Days Between calculated. Current value is: " + daysBetweenDates);
        if(daysBetweenDates > 0){
            log.debug("More than 0 days were passed since the last calculation");
            if(daysBetweenDates <= user.getDaysLeft()){
                log.debug("Days left calculation has been started");
                user.setDaysLeft(user.getDaysLeft() - daysBetweenDates);
                user.setCalculationDate(LocalDate.now());
                userRepository.save(user);
            }
            else {
                user.setDaysLeft(user.getDaysDivision());
                log.debug("Days counter has been reset");
            }
        }
    }

    public BigDecimal calculateCurrentBalance(long tgUserId) {
        daysLeftActualisation(tgUserId);
        User user = userRepository.findBytgUserId(tgUserId);
        log.debug("Current balance for user " + tgUserId + "initiated. Current balance is: " + (user.getCurrentBalance().divide(new BigDecimal(user.getDaysLeft()), 2, RoundingMode.HALF_UP)).toString());
        return user.getCurrentBalance().divide(new BigDecimal(user.getDaysLeft()), 2, RoundingMode.HALF_UP);
    }

    public int getDaysLeft(long tgUserId) {
        //daysLeftActualisation(tgUserId); //Actualisation is not necessary since this method is used after {@link #calculateCurrentBalance(long tgUserId)}
        log.debug("getDaysLeft function was activated");
        return userRepository.findBytgUserId(tgUserId).getDaysLeft();

    }

    public void changePeriod(long tgUserId, int days) {
        User user = userRepository.findBytgUserId(tgUserId);
        user.setDaysDivision(days);
        user.setDaysLeft(days);
        userRepository.save(user);
        log.debug("changePeriod function was activated");
    }

    public void deleteUserData(long tgUserId) {
        userRepository.deleteBytgUserId(tgUserId);
        operationsRepository.deleteAllBytgUserId(tgUserId);
        log.info("User "+ tgUserId + "was deleted");
    }
}
