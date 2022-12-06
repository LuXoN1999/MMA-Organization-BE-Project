package com.example.MMA.Organization.rest.service.division;

import com.example.MMA.Organization.common.DivisionChecker;
import com.example.MMA.Organization.common.InputChecker;
import com.example.MMA.Organization.persistence.entity.division.Division;
import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import com.example.MMA.Organization.persistence.repository.division.DivisionRepository;
import com.example.MMA.Organization.rest.dto.division.DivisionDTO;
import com.example.MMA.Organization.rest.dto.division.DivisionMapperImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DivisionService {

    private final DivisionRepository divisionRepository;

    private final DivisionMapperImpl divisionMapper;

    @Autowired
    public DivisionService(DivisionRepository divisionRepository, DivisionMapperImpl divisionMapper) {
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
    }


    public String addNewDivision(Division divisionToAdd){
        if(!DivisionChecker.nameIsValid(divisionToAdd.getName())){
            return "Invalid division name. Please make sure that division name is longer than 3 characters and isn't numeric only.";
        }
        if(!DivisionChecker.weightsAreValid(divisionToAdd.getMinWeight(),divisionToAdd.getMaxWeight())){
            return "Invalid weight input. Please make sure the weight inputs make sense.";
        }
        if(!DivisionChecker.maxNumberOfFightersIsValid(divisionToAdd.getMaxNumberOfFighters())){
            return "Invalid max. number of fighters input. Please make sure the max. number of fighters input makes sense.";
        }

        if(InputChecker.divisionNameIsTaken(divisionToAdd,divisionRepository.findAll())){
            return "Name " + divisionToAdd.getName() + " is already taken. Please try with a different name.";
        }
        Division divisionWithMatchedWeight = InputChecker.weightRangeOverlapped(divisionToAdd,divisionRepository.findAll());
        if(divisionWithMatchedWeight != null){
            return "Division weight range matches weight range of a different division. Please adjust your division weight range." +
                    "\nWeight range of the matched division("+ divisionWithMatchedWeight.getName() + "): " +
                    "[" + divisionWithMatchedWeight.getMinWeight() + ", " + divisionWithMatchedWeight.getMaxWeight() + "]kg.";
        }

        divisionRepository.save(divisionToAdd);
        return "Division successfully added:\n " + divisionToAdd;
    }

    public List<DivisionDTO> getAllDivisions() {
        return divisionRepository.findAll()
                .stream()
                .map(element -> divisionMapper.toDTO(element))
                .collect(Collectors.toList());
    }

    
    public List<Fighter> getAllFightersInDivision(Long divisionId){
        boolean divisionExists = divisionRepository.existsById(divisionId);
        if(divisionExists){
            return divisionRepository.findById(divisionId).get().getFighters();
        }
        return null;
    }

    public String deleteDivision(Long divisionId) {
        boolean divisionExists = divisionRepository.existsById(divisionId);
        if(divisionExists){
            Division chosenDivision = divisionRepository.findById(divisionId).get();
            for (Fighter fighter : chosenDivision.getFighters()) {
                fighter.leaveDivision();
            }
            divisionRepository.deleteById(divisionId);
            return "Division with id "+ divisionId + " successfully deleted.";
        }
        return "Division with id " + divisionId + " does not exist!";
    }

    @Transactional
    public String updateDivision(Long divisionId, String newName, Double newMinWeight, Double newMaxWeight, Integer newMaxNumberOfFighters){
        boolean divisionExists = divisionRepository.existsById(divisionId);
        if(divisionExists){
            Division chosenDivision = divisionRepository.findById(divisionId).get();

            String oldName = chosenDivision.getName();
            Double oldMinWeight = chosenDivision.getMinWeight();
            Double oldMaxWeight = chosenDivision.getMaxWeight();
            Integer oldMaxNumberOfFighters = chosenDivision.getMaxNumberOfFighters();

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

            return "Division with id " + divisionId + " successfully updated.\n" +
                    "\tName: [" + oldName + "] -> [" + newName + "]\n" +
                    "\tMinWeight: [" + oldMinWeight + "] -> [" + newMinWeight + "]\n" +
                    "\tMaxWeight: [" + oldMaxWeight + "] -> [" + newMaxWeight+ "]\n" +
                    "\tMaxNumberOfFighters: [" + oldMaxNumberOfFighters + "] -> [" + newMaxNumberOfFighters + "]";
        }
        return "Division with id " + divisionId + " does not exist!";
    }

}
