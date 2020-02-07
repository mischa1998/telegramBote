package ru.message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>{
	public List<MessageEntity> findByPath(final String path);
	public List<MessageEntity> findBySongName(final String songName);
}
