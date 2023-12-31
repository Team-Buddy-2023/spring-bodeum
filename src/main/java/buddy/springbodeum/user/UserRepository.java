package buddy.springbodeum.user;

import buddy.springbodeum.user.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    User findByEmail(String email);
    User findByUserId(Long userId);

    User findByKakaoId(Long kakaoIdLong);

    boolean existsByNickname(String nickname);
}