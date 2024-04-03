package buddy.springbodeum.chat;


import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.user.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUser(User byUserId);
    void deleteByFluffy(Fluffy fluffy);
}