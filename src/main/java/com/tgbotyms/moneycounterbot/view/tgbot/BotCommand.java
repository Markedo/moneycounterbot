package com.tgbotyms.moneycounterbot.view.tgbot;

import lombok.Getter;

/*
This class contains list of available bot commands and responses
 */

@Getter
public abstract class BotCommand {
    //Keyboard keys titles
    static final String INFO = "Информация";
    static final String KeyAddIncome = "Внести доход";
    static final String KeyAddExpense = "Внести расход";
    static final String KeySetPeriod = "Изменить число дней";
    static final String KeyShowBalance = "Показать баланс";
    static final String KeyDeleteProfile = "Удалить профиль";

    //Response texts
    static final String RespAddIncome = "Пожалуйста введите доход";
    static final String RespAddExpense = "Пожалуйста введите расход (без минуса и иных знаков, только число)";
    static final String RespShowBalance = "Доступный баланс на день: ";
    static final String RespShowDaysLeft = ", осталось дней: ";
    static final String RespSetPeriod = "Введите количество дней, на которые следует разделить бюджет";
    static final String RespDeleteProfile = "Вы уверены, что хотите удалить все данные? Восстановление будет невозможно. Если Вы согласны, наберите (без кавычек): ";
    static final String RespDefault = "Приветствую! Выберите комманду для продолжения";
    static final String RespDeleteConfirm = "Удалить";

    //Status texts
    static final String StatIncomeAdded = "Доход учтён";
    static final String StatExpenseAdded = "Расход учтён";
    static final String StatPeriodChanged = "Период изменён";
    static final String StatProfileDeleted= "Профиль удалён успешно";
    static final String StatProfileDeleteError = "Подтверждение на удаление не получено. Процедура удаления профиля прервана. Попробуйте начать процедуру заново и проверьте правильность ввода ключевого слова.";


    //Other
    static final String BotCommandError = "Комманда не распознана";
    static final String Start = "/start";
}
