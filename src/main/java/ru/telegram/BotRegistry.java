package ru.telegram;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Configuration
public class BotRegistry {
	@Bean
	public Bot bot() throws TelegramApiRequestException {
		ApiContextInitializer.init();
		//"198.98.56.71", 8080
		//"198.98.55.168", 8080
		RequestConfig requestConfig = RequestConfig.custom().setProxy(new HttpHost("198.98.56.71", 8080))
				.setProxyPreferredAuthSchemes(Arrays.asList(new String[] {"SOCKS4"})).build();
		DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
		botOptions.setRequestConfig(requestConfig);
		Bot bot = new Bot(botOptions);
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(bot);
        return bot;
	}
}