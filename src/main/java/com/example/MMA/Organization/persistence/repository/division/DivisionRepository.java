package com.example.MMA.Organization.persistence.repository.division;

import com.example.MMA.Organization.persistence.entity.division.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DivisionRepository extends JpaRepository<Division,Long> {

    @Query(
            value = "SELECT * FROM division d WHERE d.id <> ?#{[0]}",
            nativeQuery = true
    )
    List<Division> getAllDivisionsExceptSpecified(Long divisionIdToIgnore);
}
