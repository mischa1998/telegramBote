package ru.telegram;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {
	/**
	 * Приветственное сообщение бота.
	 */
	public static String WELCOME_MESSAGE = "Hello! \n"
			+ "I'm Telegram Java Bot! \n"
			+ "My commands is: \n"
			+ "/register your_name (only name without middle name or last name)\n"
			+ "/users get users list";
	public static String NOT_SUCH_COMMAND = "Sorry! Not such command in my list yet!";
	public static String BEGIN_COMAND_SYMBOL = "/";
	public static String WHITESPACE = " ";
	
	@Value("#{'${command.list}'.split(',')}")
	public List<String> commands;
	
	public String proccessMessage(String message) {
		if(!message.contains(BEGIN_COMAND_SYMBOL)) {
			return message;
		}
		//надо split сообщение по пробелам и смотреть что там
		if(!commands.contains(message.substring(message.indexOf(BEGIN_COMAND_SYMBOL),
				Math.min(message.indexOf(WHITESPACE), message.length())))) {
			return NOT_SUCH_COMMAND;
		}
		return WELCOME_MESSAGE;
	}
}
