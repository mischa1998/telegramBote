package ru.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserEntity {
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Boolean getNeedReindex() {
		return needReindex;
	}

	public void setNeedReindex(Boolean needReindex) {
		this.needReindex = needReindex;
	}

	@Id
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="chat_id")
	private Long chatId;
	
	@Column(name="need_reindex")
	private Boolean needReindex;
}
