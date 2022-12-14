package com.example.MMA.Organization.persistence.repository.fighter;

import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FighterRepository extends JpaRepository<Fighter, Long> {
    @Query(
            value = "SELECT * FROM fighter f WHERE f.id <> ?#{[0]}",
            nativeQuery = true
    )
    List<Fighter> getAllFightersExceptSpecified(Long fighterIdToIgnore);

}
