package buddy.springbodeum.character.repository;

import buddy.springbodeum.character.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findAll();
    Character save(Character characters);
}
