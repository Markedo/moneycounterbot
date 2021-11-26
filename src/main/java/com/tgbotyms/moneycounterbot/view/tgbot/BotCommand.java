package com.tgbotyms.moneycounterbot.view.tgbot;

import lombok.Getter;

/*
This class contains list of available bot commands
 */

@Getter
public abstract class BotCommand {
    //Keyboard keys titles
    public static final String INFO = "Информация";
    public static final String KeyAddIncome = "Внести доход";
    public static final String KeyAddExpense = "Внести расход";
    public static final String KeySetPeriod = "Изменить число дней";
    public static final String KeyShowBalance = "Показать баланс";
    public static final String KeyDeleteProfile = "Удалить профиль";

    //Response texts
    public static final String RespAddIncome = "Пожалуйста введите доход";
    public static final String RespAddExpense = "Пожалуйста введите расход (без минуса и иных знаков, только число)";
    public static final String RespShowBalance = "Доступный баланс на день: ";
    public static final String RespShowDaysLeft = ", осталось дней: ";
    public static final String RespSetPeriod = "Введите количество дней, на которые следует разделить бюджет";
    public static final String RespDeleteProfile = "Вы уверены, что хотите удалить все данные? Восстановление будет невозможно. Если Вы согласны, наберите (без кавычек): ";
    public static final String RespDefault = "Приветствую! Выберите комманду для продолжения";
    public static final String RespDeleteConfirm = "Удалить";

    //Status texts
    public static final String StatIncomeAdded = "Доход учтён";
    public static final String StatExpenseAdded = "Расход учтён";
    public static final String StatPeriodChanged = "Период изменён";
    public static final String StatProfileDeleted= "Профиль удалён успешно";
    public static final String StatProfileDeleteError = "Подтверждение на удаление не получено. Процедура удаления профиля прервана. Попробуйте начать процедуру заново и проверьте правильность ввода ключевого слова.";


    //Other
    public static final String BotCommandError = "Комманда не распознана";
    public static final String Start = "/start";
}
