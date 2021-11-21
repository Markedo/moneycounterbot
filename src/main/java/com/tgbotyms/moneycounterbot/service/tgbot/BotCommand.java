package com.tgbotyms.moneycounterbot.service.tgbot;

import lombok.Getter;

/*
This class contains list of available bot commands
 */

@Getter
public abstract class BotCommand {
    public static final String START = "/start";
    public static final String INFO = "Информация";
    public static final String ADDINCOME = "Внести доход";
    public static final String ADDEXPENSE = "Внести расход";
    public static final String SETPERIOD = "Изменить число дней";
    public static final String SHOWBALANCE = "Показать баланс";
    public static final String DELETEPROFILE = "Удалить профиль";

}
