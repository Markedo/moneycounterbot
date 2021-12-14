package com.tgbotyms.moneycounterbot.model;

/*
This class contains validation error messages
 */

public abstract class ErrorMessages {
    static final String NotNullMsg = "Значение не может быть пустым, пожалуйста проверьте введенные данные и повторите операцию.";
    static final String SumLessThanZero = "Значение не может быть меньше нуля, пожалуйста проверьте введенные данные и повторите операцию.";
    static final String DaysLessThanOne = "Количество дней не может быть менее 1 (одного). Пожалуйста проверьте ввод и повторите операцию.";
}
