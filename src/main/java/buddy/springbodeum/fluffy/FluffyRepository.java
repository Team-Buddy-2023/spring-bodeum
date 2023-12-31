package buddy.springbodeum.fluffy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FluffyRepository extends JpaRepository<Fluffy, Long> {
    List<Fluffy> findAll();
    Fluffy save(Fluffy fluffy);

    @Override
    void deleteAllInBatch();

    Fluffy findFluffyById(Long fluffyId);

    Fluffy findByName(String name);
}