package ru.telegram;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Audio;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {
	
	@Autowired
	private MessageHelper messageHelper;
	
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
			this.bot = bot;
		}
		
		@Override
		public void run() {
			log.info("Start thread for new message");
			//пока поддерживаются только текстовые сообщения

			
			messageHelper.proccessMessage(bot, message);
			log.info("chatId " + message.getChatId());
			/*
			SendMessage answer = new SendMessage();
			//answer.enableMarkdown(true);
			answer.setText(answerString);
			answer.setChatId(message.getChatId());
			*/
			
			/* удалить сообщение можно так
			DeleteMessage delete = new DeleteMessage();
			delete.setChatId("11");
			delete.setMessageId(12);
			execute(delete);
			*/
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
