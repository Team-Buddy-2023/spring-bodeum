package buddy.springbodeum.user;

import buddy.springbodeum.user.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    Optional<User> findByUserId(Long userId);

    User findByKakaoId(Long kakaoIdLong);

    boolean existsByNickname(String nickname);
}