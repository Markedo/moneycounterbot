package com.tgbotyms.moneycounterbot.view.tgbot;

import com.tgbotyms.moneycounterbot.controllers.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.ArrayList;

/*
This class provides main functionality of Telegram Bot
 */

@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Autowired
    Operations operations;

    @Override
    public void onUpdateReceived(Update update) {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                SendMessage newsm = new SendMessage();
                newsm.setChatId(message.getChatId().toString());

                if (update.getMessage().getReplyToMessage() == null) {
                    operations.UserCheckAndAdd(message.getChatId());//This section provides initial reply
                    switch (message.getText()) {
                        case (BotCommand.Start):
                            operations.UserCheckAndAdd(message.getChatId());
                            newsm.setText(BotCommand.RespDefault);
                            newsm.setReplyMarkup(getKeyboard());
                            break;
                        case (BotCommand.KeyAddIncome):
                            newsm.setText(BotCommand.RespAddIncome);
                            newsm.setReplyToMessageId(message.getMessageId());
                            newsm.setReplyMarkup(new ForceReplyKeyboard());
                            break;
                        case (BotCommand.KeyAddExpense):
                            newsm.setText(BotCommand.RespAddExpense);
                            newsm.setReplyToMessageId(message.getMessageId());
                            newsm.setReplyMarkup(new ForceReplyKeyboard());
                            break;
                        case (BotCommand.KeyShowBalance):
                            BigDecimal balance = operations.calculateCurrentBalance(message.getChatId());
                            int DaysLeft = operations.getDaysLeft(message.getChatId());
                            newsm.setText(BotCommand.RespShowBalance + balance + BotCommand.RespShowDaysLeft + DaysLeft);
                            newsm.setReplyMarkup(getKeyboard());
                            break;
                        case (BotCommand.KeySetPeriod):
                            newsm.setText(BotCommand.RespSetPeriod);
                            newsm.setReplyMarkup(new ForceReplyKeyboard());
                            break;
                        case (BotCommand.KeyDeleteProfile):
                            newsm.setText(BotCommand.RespDeleteProfile + " \"" + BotCommand.RespDeleteConfirm+ "\"");
                            newsm.setReplyMarkup(new ForceReplyKeyboard());
                            break;
                        default:
                            newsm.setText(BotCommand.RespDefault);
                            newsm.setReplyMarkup(getKeyboard());
                            break;
                    }

                }
                else { //This section handle the selected action
                   switch (message.getReplyToMessage().getText()) {
                       case(BotCommand.RespAddIncome):
                           BigDecimal income = new BigDecimal(message.getText());
                           operations.addIncome(message.getChatId(), income);
                           newsm.setText(BotCommand.StatIncomeAdded);
                           newsm.setReplyMarkup(getKeyboard());
                           break;
                       case (BotCommand.RespAddExpense):
                           BigDecimal expense = new BigDecimal(message.getText());
                           operations.addExpense(message.getChatId(), expense);
                           newsm.setText(BotCommand.StatExpenseAdded);
                           newsm.setReplyMarkup(getKeyboard());
                           break;
                       case (BotCommand.RespSetPeriod):
                           operations.changePeriod(message.getChatId(), Integer.parseInt(message.getText()));
                           newsm.setText(BotCommand.StatPeriodChanged);
                           newsm.setReplyMarkup(getKeyboard());
                           break;
                       case (BotCommand.RespDeleteProfile+ " \"" + BotCommand.RespDeleteConfirm+ "\""):
                           if(message.getText().equals(BotCommand.RespDeleteConfirm)) {
                               operations.deleteUserData(message.getChatId());
                               newsm.setText(BotCommand.StatProfileDeleted);
                               newsm.setReplyMarkup(getKeyboard());
                           }
                           else {
                               newsm.setText(BotCommand.StatProfileDeleteError);
                               newsm.setReplyMarkup(getKeyboard());
                           }
                           break;
                       default:
                           newsm.setText(BotCommand.BotCommandError);
                           newsm.setReplyMarkup(getKeyboard());
                           break;
                   }
                }

                try {
                    execute(newsm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }


    private ReplyKeyboardMarkup getKeyboard() { //This section forms bot commands keyboard for users

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(BotCommand.KeyAddIncome);
        keyboardFirstRow.add(BotCommand.KeyAddExpense);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(BotCommand.KeySetPeriod);
        keyboardSecondRow.add(BotCommand.KeyShowBalance);
        keyboardSecondRow.add(BotCommand.KeyDeleteProfile);

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

}
