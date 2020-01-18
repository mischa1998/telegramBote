package ru.telegram;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {
	
	/**
	 * Логер для бота
	 */
	private Logger log = Logger.getLogger(Bot.class);
	
	public Bot(DefaultBotOptions options) {
		super(options);
	}

	@Override
	public void onUpdateReceived(Update update) {
		//log.info("in update method");
		update.getMessage().getFrom().getId();
		new MessageReciever(update.getMessage(), this).start();
	}
	
	private class MessageReciever extends Thread {
		private Message message;
		private Bot bot;
		
		public MessageReciever(Message message, Bot bot) {
			this.message = message;
		}
		
		@Override
		public void run() {
			log.info("Start thread for new message");
			System.out.println(message.getText());
			log.info("chatId " + message.getChatId());
			SendMessage answer = new SendMessage();
			answer.enableMarkdown(true);
			answer.setText("Hi! I'm bot on Java");
			answer.setChatId(message.getChatId());
			try {
				execute(answer);
			} catch (TelegramApiException e) {
				log.error(e);
			}
			log.info("Thread end work");
		}
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
