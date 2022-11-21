package com.example.MMA.Organization.persistence.repository.fighter;

import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FighterRepository extends JpaRepository<Fighter, Long> {

}
