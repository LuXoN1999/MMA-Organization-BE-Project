package com.example.MMA.Organization.rest.service.fighter;

import com.example.MMA.Organization.common.FighterChecker;
import com.example.MMA.Organization.common.InputChecker;
import com.example.MMA.Organization.persistence.entity.division.Division;
import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import com.example.MMA.Organization.persistence.repository.division.DivisionRepository;
import com.example.MMA.Organization.persistence.repository.fighter.FighterRepository;
import com.example.MMA.Organization.rest.dto.fighter.FighterDTO;
import com.example.MMA.Organization.rest.dto.fighter.FighterMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FighterService {

    private final FighterRepository fighterRepository;
    private final DivisionRepository divisionRepository;
    private final FighterMapperImpl fighterMapper;

    @Autowired
    public FighterService(FighterRepository fighterRepository, DivisionRepository divisionRepository, FighterMapperImpl fighterMapper) {
        this.fighterRepository = fighterRepository;
        this.divisionRepository = divisionRepository;
        this.fighterMapper = fighterMapper;
    }

    public StringBuilder addNewFighter(Fighter fighterToAdd) {
        List<Fighter> allFighters = fighterRepository.findAll();

        StringBuilder errorResponse = new StringBuilder();

        if(!FighterChecker.nameIsValid(fighterToAdd.getName())){
            errorResponse.append("ERROR: Invalid fighter name. Please make sure that the fighter name is at least 2 characters long and doesn't contain numbers.\n");
        }

        if(!FighterChecker.surnameIsValid(fighterToAdd.getSurname())){
            errorResponse.append("ERROR: Invalid fighter surname. Please make sure that the fighter surname is at least 2 characters long and doesn't contain numbers.\n");
        }

        if(!FighterChecker.nicknameIsValid(fighterToAdd.getNickname())){
            errorResponse.append("ERROR: Invalid fighter nickname. Please make sure that fighter's nickname is at least 2 characters long and isn't numeric only.\n");
        }
        if (InputChecker.fighterNicknameIsTaken(fighterToAdd.getNickname(), allFighters)) {
            errorResponse.append("PROBLEM: Nickname " + fighterToAdd.getNickname() + " is already taken. Please try with a different nickname.\n");
        }

        if(FighterChecker.fighterAgeIsValid(fighterToAdd.getDateOfBirth())){
            if (FighterChecker.fighterIsAMinor(fighterToAdd.getDateOfBirth())) {
                errorResponse.append("PROBLEM: Minors can not compete in professional MMA organizations.\n");
            }
        }else{
            errorResponse.append("ERROR: Invalid fighter age input. Age of the fighter can not be zero or less.");
        }

        if (!FighterChecker.weightIsValid(fighterToAdd.getWeightInKg())) {
            errorResponse.append("ERROR: Weight input is not valid. Please make sure fighter's weight makes sense.\n");
        }

        if (!FighterChecker.heightIsValid(fighterToAdd.getHeightInCm())) {
            errorResponse.append("ERROR: Height input is not valid. Please make sure fighter's height makes sense.\n");
        }

        if (!errorResponse.isEmpty()) {
            return errorResponse;
        }

        fighterRepository.save(fighterToAdd);
        return new StringBuilder("Fighter successfully added:\n " + fighterToAdd);
    }

    public List<FighterDTO> getAllFighters(){
        return fighterRepository.findAll()
                .stream()
                .map(element -> fighterMapper.toDTO(element))
                .collect(Collectors.toList());
    }


    public String deleteFighter(Long fighterId){
        boolean fighterExists = fighterRepository.existsById(fighterId);
        if(fighterExists){
            fighterRepository.deleteById(fighterId);
            return "Fighter with id "+ fighterId + " successfully deleted.";
        }
        return "Fighter with id " + fighterId + " does not exist!";
    }


    @Transactional
    public StringBuilder updateFighter(Long fighterId,
                              String newName,
                              String newSurname,
                              String newNickname,
                              LocalDate newDateOfBirth,
                              String newNationality,
                              Double newWeightInKg,
                              Double newHeightInCm
                              )
    {

        List<Object> requestParams = Arrays.asList(newName, newSurname, newNickname, newDateOfBirth, newNationality, newWeightInKg, newHeightInCm);
        if(InputChecker.allRequestParametersAreNull(requestParams)){
            return new StringBuilder("No request parameters passed.");
        }

        boolean fighterExists = fighterRepository.existsById(fighterId);
        if(fighterExists) {
            StringBuilder errorResponse = new StringBuilder();
            StringBuilder successResponse = new StringBuilder("Division with id " + fighterId + " successfully updated.\n");

            Fighter chosenFighter = fighterRepository.findById(fighterId).get();
            List<Fighter> restOfFighters = fighterRepository.getAllFightersExceptSpecified(fighterId);

            String oldName = chosenFighter.getName();
            String oldSurname = chosenFighter.getSurname();
            String oldNickname = chosenFighter.getNickname();
            LocalDate oldDateOfBirth = chosenFighter.getDateOfBirth();
            String oldNationality = chosenFighter.getNationality();
            Double oldWeightInKg = chosenFighter.getWeightInKg();
            Double oldHeightInCm = chosenFighter.getHeightInCm();

            if(newName != null){
                if(FighterChecker.nameIsValid(newName)){
                    chosenFighter.setName(newName);
                    successResponse.append("\tName: [" + oldName + "] -> [" + newName + "]\n");
                } else{
                    errorResponse.append("ERROR: Invalid fighter name. Please make sure that the fighter name is at least 2 characters long and doesn't contain numbers.\n");                }
            }

            if(newSurname != null){
                if(FighterChecker.surnameIsValid(newSurname)){
                    chosenFighter.setSurname(newSurname);
                    successResponse.append("\tSurname: [" + oldSurname + "] -> [" + newSurname + "]\n");
                } else{
                    errorResponse.append("ERROR: Invalid fighter surname. Please make sure that the fighter surname is at least 2 characters long and doesn't contain numbers.\n");                }
            }

            if(newNickname != null) {
                if (FighterChecker.nicknameIsValid(newNickname)) {
                    if (!InputChecker.fighterNicknameIsTaken(newNickname, restOfFighters)) {
                        chosenFighter.setNickname(newNickname);
                        successResponse.append("\tNickname: [" + oldNickname + "] -> [" + newNickname + "]\n");
                    } else {
                        errorResponse.append("PROBLEM: Nickname " + newNickname + " is already taken. Please try with a different nickname.\n");
                    }
                } else {
                    errorResponse.append("ERROR: Invalid fighter nickname. Please make sure that fighter's nickname is at least 2 characters long and isn't numeric only.\n");
                }
            }

            if(newDateOfBirth != null){
                if(FighterChecker.fighterAgeIsValid(newDateOfBirth)){
                    if(!FighterChecker.fighterIsAMinor(newDateOfBirth)){
                        chosenFighter.setDateOfBirth(newDateOfBirth);
                        successResponse.append("\tDate of Birth: [" + oldDateOfBirth + "] -> [" + newDateOfBirth + "]\n");
                    } else{
                        errorResponse.append("PROBLEM: Minors can not compete in professional MMA organizations.\n");
                    }
                } else{
                    errorResponse.append("ERROR: Invalid fighter age input. Age of the fighter can not be zero or less.");
                }
            }

            if(newNationality != null){
                chosenFighter.setNationality(newNationality);
                successResponse.append("\tNationality: [" + oldNationality + "] -> [" + newNationality + "]\n");
            }

            if(newWeightInKg != null){
                if(FighterChecker.weightIsValid(newWeightInKg)){
                    chosenFighter.setWeightInKg(newWeightInKg);
                    successResponse.append( "\tWeight(kg): [" + oldWeightInKg + "] -> [" + newWeightInKg + "]\n");
                } else{
                    errorResponse.append("ERROR: Weight input is not valid. Please make sure fighter's weight makes sense.\n");
                }
            }

            if(newHeightInCm != null){
                if(FighterChecker.heightIsValid(newHeightInCm)){
                    chosenFighter.setHeightInCm(newHeightInCm);
                    successResponse.append("\tHeight(cm): [" + oldHeightInCm + "] -> [" + newHeightInCm + "]");
                } else{
                    errorResponse.append("ERROR: Height input is not valid. Please make sure fighter's height makes sense.\n");
                }
            }

            if(errorResponse.length() != 0){
                return errorResponse;
            }
            return successResponse;
        }
        return new StringBuilder("Fighter with id " + fighterId + " does not exist!");
    }

    @Transactional
    public void addFighterToDivision(Long divisionId,Long fighterId){
        boolean divisionExists = divisionRepository.existsById(divisionId);
        boolean fighterExists = fighterRepository.existsById(fighterId);
        if(divisionExists && fighterExists){
            Division chosenDivision = divisionRepository.findById(divisionId).get();
            Fighter chosenFighter = fighterRepository.findById(fighterId).get();
            chosenFighter.assignDivision(chosenDivision);
        }
    }
}
