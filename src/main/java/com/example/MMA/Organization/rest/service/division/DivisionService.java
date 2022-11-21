package com.example.MMA.Organization.rest.service.division;

import com.example.MMA.Organization.persistence.entity.division.Division;
import com.example.MMA.Organization.persistence.repository.division.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DivisionService {
    private DivisionRepository divisionRepository;

    @Autowired
    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public List<Division> getAllDivisions(){ return divisionRepository.findAll(); }
    public void addNewDivision(Division divisionToAdd){ divisionRepository.save(divisionToAdd); }
    public void deleteDivision(Long divisionId){ divisionRepository.deleteById(divisionId); }
    @Transactional
    public void updateDivision(Long divisionId, String newName, Double newMinWeight, Double newMaxWeight, Integer newMaxNumberOfFighters){
        Division chosenDivision = divisionRepository.findById(divisionId).orElseThrow(()-> new IllegalStateException("Division with id(" + divisionId + ") is not found."));

        if(newName != null && newName.length() > 0 && !Objects.equals(chosenDivision.getName(),newName)){
            chosenDivision.setName(newName);
        }
        if(newMinWeight != null && newMinWeight >= 0){
            chosenDivision.setMinWeight(newMinWeight);
        }
        if(newMaxWeight != null && newMaxWeight >= 0){
            chosenDivision.setMaxWeight(newMaxWeight);
        }
        if(newMaxNumberOfFighters != null && newMaxNumberOfFighters >= 0) {
            chosenDivision.setMaxNumberOfFighters(newMaxNumberOfFighters);
        }
    }

}
