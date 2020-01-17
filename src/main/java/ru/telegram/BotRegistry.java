package ru.telegram;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Component
public class BotRegistry {
	@Autowired
	private Bot bot;
	@PostConstruct
	public void registry() throws TelegramApiRequestException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		RequestConfig requestConfig = RequestConfig.custom().setProxy(new HttpHost("198.98.55.168", 8080)).build(); 
		bot.getOptions().setRequestConfig(requestConfig);
        telegramBotsApi.registerBot(bot);
	}
}