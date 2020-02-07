package ru.telegram;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import ru.message.MessageEntity;
import ru.message.MessageRepository;

@Component
public class MessageHelper {
	
	/**
	 * Логер для помощника бота.
	 */
	private Logger log = Logger.getLogger(Bot.class);
	
	/**
	 * Приветственное сообщение бота.
	 */
	public static String WELCOME_MESSAGE = "Hello! \n"
			+ "I'm Telegram Java Bot! \n"
			+ "My commands is: \n"
			+ "/register your_name (only name without middle name or last name)\n"
			+ "/users get users list \n"
			+ "/getSong get my favorite song";
	public static String NOT_SUCH_COMMAND = "Sorry! Not such command in my list yet!";
	public static String BEGIN_COMAND_SYMBOL = "/";
	public static String WHITESPACE = " ";
	
	@Value("#{'${command.list}'.split(',')}")
	public List<String> commands;
	
	@Autowired
	private MessageRepository messageRepo;
	
	public void proccessMessage(Bot bot, Message message) {
		if (message.hasText() && !message.getText().contains(BEGIN_COMAND_SYMBOL)) {
			SendMessage answer = new SendMessage();
			answer.setText(WELCOME_MESSAGE);
			answer.setChatId(message.getChatId());
			try {
				bot.execute(answer);
			} catch(Exception e) {
				log.error(e);
			}
			return;
		}
		//надо split сообщение по пробелам и смотреть что там
		//это хардкод для пробы mp3
		String messageText = message.getText();
		String[] splitMessage = messageText.split(WHITESPACE);
		
		
		//если хотят музыку
		if (splitMessage[0].equals("/getSong") && splitMessage.length == 1) {
			List<MessageEntity> messages = messageRepo.findAll();
			StringBuilder listOfSongs = new StringBuilder("");
			for (MessageEntity messageEntity : messages) {
				listOfSongs.append(messageEntity.getSongName() + " \n");
			}
			SendMessage answer = new SendMessage();
			if (listOfSongs.length() > 0) {
				answer.setText(listOfSongs.toString());
			} else {
				//добавить в строковые ресурсы
				answer.setText("Пока нет песен\n");
			}
			answer.setChatId(message.getChatId());
			try {
				bot.execute(answer);
			} catch (Exception e) {
				log.error(e);
			}
		}
		
		//если хотят определенную песню
		if  (splitMessage[0].equals("/getSong") && splitMessage.length > 1) {
			String songName = splitMessage[1];
			List<MessageEntity> messagesByName = messageRepo.findBySongName(songName);
			SendAudio sendAudio = new SendAudio();
			if (!messagesByName.isEmpty()) {
				MessageEntity oldMessage = messagesByName.get(0);
				sendAudio.setAudio(oldMessage.getFileId());
				sendAudio.setTitle(oldMessage.getSongName());
				sendAudio.setChatId(message.getChatId());
			} else {
				try {
					File file = new File(songName + ".mp3");
					if (file.exists()) {
						System.out.println("File exist");
						sendAudio.setNewAudio(songName, new FileInputStream(file));
						sendAudio.setTitle(songName);
						sendAudio.setChatId(message.getChatId());
					}
				} catch (FileNotFoundException e) {
					log.error(e);
				}
			}
			try {
				if (StringUtils.hasText(sendAudio.getChatId())) {
					Message sendedMessage = bot.sendAudio(sendAudio);
					if (messagesByName.isEmpty()) {
						MessageEntity newMessage = new MessageEntity();
						newMessage.setPath(splitMessage[0]);
						newMessage.setFileId(sendedMessage.getAudio().getFileId());
						newMessage.setMessageId(sendedMessage.getAudio().getFileId());
						newMessage.setSongName(songName);
						messageRepo.save(newMessage);
					}
				}
			} catch(Exception e) {
				log.error(e);
			}
			return;
		}
	}
}
