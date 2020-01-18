package ru.TelegramBote;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ru.user.UserEntity;
import ru.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBaseTest {
	
	@Autowired
	UserRepository userRepo;
	
	private UserEntity user1 = new UserEntity();
	private UserEntity user2 = new UserEntity();
	
	@After
	public void after() {
		userRepo.deleteAll();
	}
	
	@Before
	public void before() {
		user1.setUserId((long)1);
		user1.setChatId((long)11);
		user1.setNeedReindex(false);
		userRepo.saveAndFlush(user1);
		user2.setUserId((long)2);
		user2.setChatId((long)12);
		user2.setNeedReindex(false);
		userRepo.saveAndFlush(user2);
	}
	
	@Test
	public void testUserRepository() {
		Assert.assertTrue(userRepo.count() == 2);
		UserEntity user = userRepo.findById(user1.getUserId()).orElse(null);
		Assert.assertTrue(user.getChatId() == user1.getChatId());
		user = userRepo.findById(user2.getUserId()).orElse(null);
		Assert.assertTrue(user.getChatId() == user2.getChatId());
	}
}
