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


    public StringBuilder addNewDivision(Division divisionToAdd) {
        StringBuilder errorResponse = new StringBuilder();
        if (!DivisionChecker.nameIsValid(divisionToAdd.getName())) {
            errorResponse.append("ERROR: Invalid division name. Please make sure that division name is longer than 3 characters and isn't numeric only.\n");
        }
        if (InputChecker.divisionNameIsTaken(divisionToAdd.getName(), divisionRepository.findAll())) {
            errorResponse.append("PROBLEM: Name " + divisionToAdd.getName() + " is already taken. Please try with a different name.\n");
        }

        if (DivisionChecker.weightsAreValid(divisionToAdd.getMinWeight(), divisionToAdd.getMaxWeight())) {
            Division divisionWithMatchedWeight = InputChecker.weightRangeOverlapped(divisionToAdd.getMinWeight(),divisionToAdd.getMaxWeight(), divisionRepository.findAll());
            if (divisionWithMatchedWeight != null) {
                errorResponse.append("PROBLEM: Division weight range matches weight range of a different division. Please adjust your division weight range." +
                        "\n\tWeight range of the matched division(" + divisionWithMatchedWeight.getName() + "): " +
                        "[" + divisionWithMatchedWeight.getMinWeight() + ", " + divisionWithMatchedWeight.getMaxWeight() + "]kg.\n");
            }
        } else {
            errorResponse.append("ERROR: Invalid weight input. Please make sure the weight inputs make sense.\n");
        }

        if (!DivisionChecker.maxNumberOfFightersIsValid(divisionToAdd.getMaxNumberOfFighters())) {
            errorResponse.append("ERROR: Invalid max. number of fighters input. Please make sure the max. number of fighters input makes sense.\n");
        }
        if (errorResponse.length() != 0) {
            return errorResponse;
        }
        divisionRepository.save(divisionToAdd);
        return new StringBuilder("Division successfully added:\n " + divisionToAdd);
    }

    public List<DivisionDTO> getAllDivisions() {
        return divisionRepository.findAll()
                .stream()
                .map(element -> divisionMapper.toDTO(element))
                .collect(Collectors.toList());
    }


    public List<Fighter> getAllFightersInDivision(Long divisionId) {
        boolean divisionExists = divisionRepository.existsById(divisionId);
        if (divisionExists) {
            return divisionRepository.findById(divisionId).get().getFighters();
        }
        return null;
    }

    public String deleteDivision(Long divisionId) {
        boolean divisionExists = divisionRepository.existsById(divisionId);
        if (divisionExists) {
            Division chosenDivision = divisionRepository.findById(divisionId).get();
            for (Fighter fighter : chosenDivision.getFighters()) {
                fighter.leaveDivision();
            }
            divisionRepository.deleteById(divisionId);
            return "Division with id " + divisionId + " successfully deleted.";
        }
        return "Division with id " + divisionId + " does not exist!";
    }

    @Transactional
    public StringBuilder updateDivision(Long divisionId, String newName, Double newMinWeight, Double newMaxWeight, Integer newMaxNumberOfFighters) {
        if(newName == null && newMinWeight == null && newMaxWeight == null && newMaxNumberOfFighters == null){
            return new StringBuilder("No request parameters passed.");
        }

        boolean divisionExists = divisionRepository.existsById(divisionId);
        if (divisionExists) {
            StringBuilder errorResponse = new StringBuilder();
            StringBuilder successResponse = new StringBuilder("Division with id " + divisionId + " successfully updated.\n");

            Division chosenDivision = divisionRepository.findById(divisionId).get();
            List<Division> restOfDivision = divisionRepository.getAllDivisionsExceptSpecified(divisionId);

            String oldName = chosenDivision.getName();
            Double oldMinWeight = chosenDivision.getMinWeight();
            Double oldMaxWeight = chosenDivision.getMaxWeight();
            Integer oldMaxNumberOfFighters = chosenDivision.getMaxNumberOfFighters();

            if (newName != null) {
                if (DivisionChecker.nameIsValid(newName)) {
                    if (!InputChecker.divisionNameIsTaken(newName, restOfDivision)) {
                        chosenDivision.setName(newName);
                        successResponse.append("\tName: [" + oldName + "] -> [" + newName + "]\n");

                    } else {
                        errorResponse.append("PROBLEM: Name " + newName + " is already taken. Please try with a different name.\n");
                    }
                } else {
                    errorResponse.append("ERROR: Invalid division name. Please make sure that division name is longer than 3 characters and isn't numeric only.\n");
                }
            }

            if(newMinWeight != null && newMaxWeight != null){
                if(DivisionChecker.weightsAreValid(newMinWeight,newMaxWeight)){
                    Division divisionWithMatchedWeight = InputChecker.weightRangeOverlapped(newMinWeight,newMaxWeight,restOfDivision);
                    if(divisionWithMatchedWeight == null){
                        chosenDivision.setMinWeight(newMinWeight);
                        chosenDivision.setMaxWeight(newMaxWeight);
                        successResponse.append("\tMinWeight: [" + oldMinWeight + "] -> [" + newMinWeight + "]\n");
                        successResponse.append("\tMaxWeight: [" + oldMaxWeight + "] -> [" + newMaxWeight + "]\n");
                    } else{
                        errorResponse.append("PROBLEM: Division weight range matches weight range of a different division. Please adjust your division weight range." +
                                "\n\tWeight range of the matched division(" + divisionWithMatchedWeight.getName() + "): " +
                                "[" + divisionWithMatchedWeight.getMinWeight() + ", " + divisionWithMatchedWeight.getMaxWeight() + "]kg.\n");
                    }
                } else{
                    errorResponse.append("ERROR: Invalid weight input. Please make sure the weight inputs make sense.\n");
                }
            } else if(newMinWeight != null || newMaxWeight != null){
                errorResponse.append("ERROR: Weights must be both present or both not present.\n");
            }

            if(newMaxNumberOfFighters != null){
                if(DivisionChecker.maxNumberOfFightersIsValid(newMaxNumberOfFighters)) {
                    chosenDivision.setMaxNumberOfFighters(newMaxNumberOfFighters);
                    successResponse.append("\tMaxNumberOfFighters: [" + oldMaxNumberOfFighters + "] -> [" + newMaxNumberOfFighters + "]");
                } else{
                    errorResponse.append("ERROR: Invalid max. number of fighters input. Please make sure the max. number of fighters input makes sense.\n");
                }
            }

            if(errorResponse.length() != 0){
                return errorResponse;
            }

            return successResponse;

        } else {
            return new StringBuilder("Division with id " + divisionId + " does not exist.");
        }
    }

}
