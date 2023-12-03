package buddy.springbodeum.chat;


import buddy.springbodeum.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {


}
