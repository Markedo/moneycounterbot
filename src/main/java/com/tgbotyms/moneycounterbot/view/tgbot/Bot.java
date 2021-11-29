package com.tgbotyms.moneycounterbot.view.tgbot;

import com.tgbotyms.moneycounterbot.controllers.Operations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
@Slf4j
@Component
@Validated
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
                log.debug("A new message was received from User " + update.getMessage().getChatId());
                Message message = update.getMessage();
                SendMessage newsm = new SendMessage();
                newsm.setChatId(message.getChatId().toString());

                if (update.getMessage().getReplyToMessage() == null) {
                    //The section below provides reply to initial message from users and shows them a menu in other cases
                    operations.UserCheckAndAdd(message.getChatId());
                    switch (message.getText()) {
                        case (BotCommand.Start):
                            operations.UserCheckAndAdd(message.getChatId());
                            newsm.setText(BotCommand.RespDefault);
                            newsm.setReplyMarkup(getKeyboard());
                            log.debug("User " + message.getChatId() + "chosen command: " + BotCommand.Start);
                            break;
                        case (BotCommand.KeyAddIncome):
                            newsm.setText(BotCommand.RespAddIncome);
                            newsm.setReplyToMessageId(message.getMessageId());
                            newsm.setReplyMarkup(new ForceReplyKeyboard());
                            log.debug("User " + message.getChatId() + "chosen command: " + BotCommand.KeyAddIncome);
                            break;
                        case (BotCommand.KeyAddExpense):
                            newsm.setText(BotCommand.RespAddExpense);
                            newsm.setReplyToMessageId(message.getMessageId());
                            newsm.setReplyMarkup(new ForceReplyKeyboard());
                            log.debug("User " + message.getChatId() + "chosen command: " + BotCommand.KeyAddExpense);
                            break;
                        case (BotCommand.KeyShowBalance):
                            BigDecimal balance = operations.calculateCurrentBalance(message.getChatId());
                            int DaysLeft = operations.getDaysLeft(message.getChatId());
                            newsm.setText(BotCommand.RespShowBalance + balance + BotCommand.RespShowDaysLeft + DaysLeft);
                            newsm.setReplyMarkup(getKeyboard());
                            log.debug("User " + message.getChatId() + "chosen command: " + BotCommand.KeyShowBalance);
                            break;
                        case (BotCommand.KeySetPeriod):
                            newsm.setText(BotCommand.RespSetPeriod);
                            newsm.setReplyMarkup(new ForceReplyKeyboard());
                            log.debug("User " + message.getChatId() + "chosen command: " + BotCommand.KeySetPeriod);
                            break;
                        case (BotCommand.KeyDeleteProfile):
                            newsm.setText(BotCommand.RespDeleteProfile + " \"" + BotCommand.RespDeleteConfirm+ "\"");
                            newsm.setReplyMarkup(new ForceReplyKeyboard());
                            log.debug("User " + message.getChatId() + "chosen command: " + BotCommand.KeyDeleteProfile);
                            break;
                        default:
                            newsm.setText(BotCommand.RespDefault);
                            newsm.setReplyMarkup(getKeyboard());
                            log.debug("User " + message.getChatId() + "command  not recognized");
                            break;
                    }

                }
                //The section below handle the selected action
                else {
                   switch (message.getReplyToMessage().getText()) {
                       case(BotCommand.RespAddIncome):
                          try {
                           log.debug("Income add command started");
                           operations.addIncome(message.getChatId(), new BigDecimal(message.getText()));
                           newsm.setText(BotCommand.StatIncomeAdded);
                           newsm.setReplyMarkup(getKeyboard());
                           log.debug(BotCommand.RespAddIncome + " command by User" + message.getChatId() + " finished successfully");
                       }
                        catch (Exception e) {
                               //newsm.setText(e.getMessage());
                               log.debug(e.getMessage());
                               log.debug(e.getCause().getMessage());
                               log.debug(e.getCause().getCause().getMessage());
                         }
                           break;
                       case (BotCommand.RespAddExpense):
                           BigDecimal expense = new BigDecimal(message.getText());
                           operations.addExpense(message.getChatId(), expense);
                           newsm.setText(BotCommand.StatExpenseAdded);
                           newsm.setReplyMarkup(getKeyboard());
                           log.debug(BotCommand.RespAddIncome + " command by User" + message.getChatId() + " finished successfully");
                           break;
                       case (BotCommand.RespSetPeriod):
                           operations.changePeriod(message.getChatId(), Integer.parseInt(message.getText()));
                           newsm.setText(BotCommand.StatPeriodChanged);
                           newsm.setReplyMarkup(getKeyboard());
                           log.debug(BotCommand.RespSetPeriod + " command by User" + message.getChatId() + " finished successfully");
                           break;
                       case (BotCommand.RespDeleteProfile+ " \"" + BotCommand.RespDeleteConfirm+ "\""):
                           if(message.getText().equals(BotCommand.RespDeleteConfirm)) {
                               operations.deleteUserData(message.getChatId());
                               newsm.setText(BotCommand.StatProfileDeleted);
                               newsm.setReplyMarkup(getKeyboard());
                               log.debug(BotCommand.RespDeleteProfile+ " command by User" + message.getChatId() + " finished successfully");
                           }
                           else {
                               newsm.setText(BotCommand.StatProfileDeleteError);
                               newsm.setReplyMarkup(getKeyboard());
                               log.debug(BotCommand.RespDeleteProfile + " command by User" + message.getChatId() + " was not performed due to an error in the keyword for delete");
                           }
                           break;
                       default:
                           newsm.setText(BotCommand.BotCommandError);
                           newsm.setReplyMarkup(getKeyboard());
                           log.debug("Command by User" + message.getChatId() + " not recognized");
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

    //The section below forms bot commands keyboard for users
    private ReplyKeyboardMarkup getKeyboard() {

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
