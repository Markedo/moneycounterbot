package com.tgbotyms.moneycounterbot.service.tgbot;

/**
 * List of commands of Bot.
 */

public enum BotCommands {
    START("/start"),
    INFO("О каналe"),
    ADDINCOME("Внести доход"),
    ADDEXPENSE("Внести расход"),
    SETPERIOD("Изменить число дней"),
    SHOWBALANCE("Показать баланс"),
    DELETEPROFILE("Удалить профиль");

    private final String title;

    BotCommands(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
