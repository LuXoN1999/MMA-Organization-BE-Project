package com.example.MMA.Organization.persistence.repository.division;

import com.example.MMA.Organization.persistence.entity.division.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DivisionRepository extends JpaRepository<Division,Long> {

}
