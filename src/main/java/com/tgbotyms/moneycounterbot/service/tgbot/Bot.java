package com.tgbotyms.moneycounterbot.service.tgbot;

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

import java.util.ArrayList;

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


    @Override
    public void onUpdateReceived(Update update) {
            if (update.hasMessage() /*&& (update.getMessage().getReplyToMessage() == null)*/) {
                Message message = update.getMessage();
                System.out.println(message.getText() + " | " + message.getChatId() + " | " + message.getChat().getUserName());
                SendMessage newsm = new SendMessage();
                newsm.setChatId(message.getChatId().toString());

                if(message.getText().equals(BotCommand.ADDINCOME) /*|| message.getReplyToMessage().getText().equals(BotCommand.ADDINCOME)*/) {
                    if(message.getReplyToMessage().getText() == null) {
                        newsm.setText("Пожалуйста введите доход");
                        newsm.setReplyToMessageId(message.getMessageId());
                        newsm.setReplyMarkup(new ForceReplyKeyboard());
                    }
                    else {
                        System.out.println(message.getText());
                        newsm.setText("Доход учтён");
                        newsm.setReplyMarkup(new ForceReplyKeyboard());
                    }
                }
                try {
                    execute(newsm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                /*
                switch (message.getText()) {
                    case (BotCommand.ADDINCOME):
                                newsm.setText("Пожалуйста введите доход");
                                newsm.setReplyToMessageId(message.getMessageId());
                                newsm.setReplyMarkup(new ForceReplyKeyboard());
                        break;
                    case (BotCommand.ADDEXPENSE):
                        newsm.setText("Пожалуйста введите расход (без минуса и иных знаков, только число)");
                        newsm.setReplyToMessageId(message.getMessageId());;
                        newsm.setReplyMarkup(new ForceReplyKeyboard());
                        break;
                    case (BotCommand.SHOWBALANCE):
                        newsm.setText("Текущий баланс: ");
                        newsm.setReplyMarkup(getKeyboard());
                        break;
                    case (BotCommand.SETPERIOD):
                        newsm.setText("Период изменён");
                        newsm.setReplyMarkup(new ForceReplyKeyboard());
                        break;
                    case (BotCommand.DELETEPROFILE):
                        newsm.setText("Вы уверены, что хотите удалить все данные? Восстановление будет невозможно");
                        newsm.setReplyMarkup(new ForceReplyKeyboard());
                        break;
                    default:
                        newsm.setText("Приветствую! Выберите комманду для продолжения");
                        newsm.setReplyMarkup(getKeyboard());
                        break;
                }

                try {
                    execute(newsm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                */
            }
            /*
            else {
                }
                try {
                    SendMessage newsm = new SendMessage();
                    newsm.setChatId(update.getMessage().getChatId().toString());
                    newsm.setText("Приветствую! Выберите комманду для начала");
                    newsm.setReplyMarkup(getKeyboard());
                    execute(newsm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

             */

        }


    private ReplyKeyboardMarkup getKeyboard() {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(BotCommand.ADDINCOME);
        keyboardFirstRow.add(BotCommand.ADDEXPENSE);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(BotCommands.SHOWBALANCE.getTitle());
        keyboardSecondRow.add(BotCommands.SETPERIOD.getTitle());
        keyboardSecondRow.add(BotCommands.DELETEPROFILE.getTitle());

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

}
