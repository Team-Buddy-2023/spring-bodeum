package buddy.springbodeum.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    @Override
    List<Character> findAll();
}
