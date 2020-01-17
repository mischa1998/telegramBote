package ru.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Component
public class Bot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		System.out.println(update.getMessage().getText());
	}

	@Override
	public String getBotUsername() {
		//перенести в константы
		return "MyJavaMichaelBot";
	}

	@Override
	public String getBotToken() {
		return "936253694:AAEUDCqnc_beGeWP27IqV-cUO3PNvejq95g";
	}

}
