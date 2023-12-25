package buddy.springbodeum.character;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findAll();
    Character save(Character characters);

    @Override
    void deleteAllInBatch();
    Character findCharacterById(Long characterId);
}