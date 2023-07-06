package buddy.springbodeum.repository;

import buddy.springbodeum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User newUser);

    boolean existsByUserId(String userId);
}
