package buddy.springbodeum.chat;


import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.user.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUser(User byUserId);
}