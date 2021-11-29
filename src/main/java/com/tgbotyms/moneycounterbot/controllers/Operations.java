package com.tgbotyms.moneycounterbot.controllers;

import com.tgbotyms.moneycounterbot.model.Operation;
import com.tgbotyms.moneycounterbot.model.OperationTypes;
import com.tgbotyms.moneycounterbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/*
This class contains all commands performed by the program
*/

@Service
@Transactional
@Validated
public class Operations {

    @Autowired
    OperationsRepository operationsRepository;

    @Autowired
    UserRepository userRepository;

    public void UserCheckAndAdd(long tgUserId) {
        if(userRepository.findBytgUserId(tgUserId) == null) {
            userRepository.save(new User(tgUserId));
        }
    }

        public void addIncome(long tgUserId, BigDecimal income) {
            Date operationDate = Calendar.getInstance().getTime();
            //Saving the operation
            Operation operation = new Operation(tgUserId, OperationTypes.INCOME,income, operationDate);
            operationsRepository.save(operation);
            //Updating user information
            User user = userRepository.findBytgUserId(tgUserId);
            user.setCurrentBalance(user.getCurrentBalance().add(income));
            userRepository.save(user);
        }

    public void addExpense(long tgUserId, BigDecimal expense) {
        //Saving the operation
        Operation operation = new Operation(tgUserId, OperationTypes.EXPENSE,expense, Calendar.getInstance().getTime());
        operationsRepository.save(operation);
        //Updating user information
        User user = userRepository.findBytgUserId(tgUserId);
        user.setCurrentBalance(user.getCurrentBalance().subtract(expense));
        userRepository.save(user);
    }

    private void daysLeftActualisation(long tgUserId) {
        User user = userRepository.findBytgUserId(tgUserId);
        LocalDate calculationDate = user.getCalculationDate();
        LocalDate currentDate = LocalDate.now();
        int daysBetweenDates = (int) ChronoUnit.DAYS.between(calculationDate, currentDate);
        System.out.println("Days Between: " + daysBetweenDates);
        if(daysBetweenDates > 0){
            System.out.println("Days more than 0");
            if(daysBetweenDates <= user.getDaysLeft()){
                System.out.println("Calculation started");
                user.setDaysLeft(user.getDaysLeft() - daysBetweenDates);
                user.setCalculationDate(LocalDate.now());
                userRepository.save(user);
            }
            else {
                System.out.println("Days reset");
                user.setDaysLeft(user.getDaysDivision());
            }
        }
    }

    public BigDecimal calculateCurrentBalance(long tgUserId) {
        daysLeftActualisation(tgUserId);
        User user = userRepository.findBytgUserId(tgUserId);
        System.out.println(user.getCurrentBalance().divide(new BigDecimal(user.getDaysLeft()), 2, RoundingMode.HALF_UP));
        return user.getCurrentBalance().divide(new BigDecimal(user.getDaysLeft()), 2, RoundingMode.HALF_UP);
    }

    public int getDaysLeft(long tgUserId) {
        //daysLeftActualisation(tgUserId); //Actualisation is not necessary since this method is used after {@link #calculateCurrentBalance(long tgUserId)}
        return userRepository.findBytgUserId(tgUserId).getDaysLeft();
    }

    public void changePeriod(long tgUserId, int days) {
        User user = userRepository.findBytgUserId(tgUserId);
        user.setDaysDivision(days);
        user.setDaysLeft(days);
        userRepository.save(user);
    }

    public void deleteUserData(long tgUserId) {
        userRepository.deleteBytgUserId(tgUserId);
       operationsRepository.deleteAllBytgUserId(tgUserId);
    }
}
