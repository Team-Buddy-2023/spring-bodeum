package buddy.springbodeum.user;

import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.user.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    Optional<User> findByUserId(Long userId);

    User findByKakaoId(Long kakaoIdLong);

    boolean existsByNickname(String nickname);

    List<User> findByFavoriteFluffy(Fluffy fluffy);
}