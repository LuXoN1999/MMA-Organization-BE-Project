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
import java.util.List;
import java.util.Objects;
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
            errorResponse.append("ERROR: Invalid fighter name. Please make sure that the fighter name is longer than 1 characters and doesn't contain numbers.\n");
        }

        if(!FighterChecker.surnameIsValid(fighterToAdd.getSurname())){
            errorResponse.append("ERROR: Invalid fighter surname. Please make sure that the fighter surname is longer than 1 characters and isn't numeric only.\n");
        }

        if(!FighterChecker.nicknameIsValid(fighterToAdd.getNickname())){
            errorResponse.append("ERROR: Invalid fighter nickname. Please make sure that fighter's nickname is longer than 2 characters and isn't numeric only.\n");
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
    public String updateFighter(Long fighterId,
                              String newName,
                              String newSurname,
                              String newNickname,
                              LocalDate newDateOfBirth,
                              String newNationality,
                              Double newWeightInKg,
                              Double newHeightInCm
                              ){
        boolean fighterExists = fighterRepository.existsById(fighterId);
        if(fighterExists) {
            Fighter chosenFighter = fighterRepository.findById(fighterId).get();
            String oldName = chosenFighter.getName();
            String oldSurname = chosenFighter.getSurname();
            String oldNickname = chosenFighter.getNickname();
            LocalDate oldDateOfBirth = chosenFighter.getDateOfBirth();
            String oldNationality = chosenFighter.getNationality();
            Double oldWeightInKg = chosenFighter.getWeightInKg();
            Double oldHeightInCm = chosenFighter.getHeightInCm();
            if (newName != null && newName.length() > 0 && !Objects.equals(chosenFighter.getName(), newName)) {
                chosenFighter.setName(newName);
            }
            if (newSurname != null && newSurname.length() > 0 && !Objects.equals(chosenFighter.getSurname(), newSurname)) {
                chosenFighter.setSurname(newSurname);
            }
            if (newNickname != null && newNickname.length() > 0 && !Objects.equals(chosenFighter.getNickname(), newNickname)) {
                chosenFighter.setNickname(newNickname);
            }
            if (newDateOfBirth != null && !Objects.equals(chosenFighter.getDateOfBirth(), newDateOfBirth)) {
                chosenFighter.setDateOfBirth(newDateOfBirth);
            }
            if (newNationality != null && newNationality.length() > 0 && !Objects.equals(chosenFighter.getNationality(), newNationality)) {
                chosenFighter.setNationality(newNationality);
            }
            if (newWeightInKg != null && newWeightInKg > 0) {
                chosenFighter.setWeightInKg(newWeightInKg);
            }
            if (newHeightInCm != null && newHeightInCm > 0) {
                chosenFighter.setHeightInCm(newHeightInCm);
            }
            return "Fighter with id " + fighterId + " successfully updated.\n" +
                    "\tName: [" + oldName + "] -> [" + newName + "]\n" +
                    "\tSurname: [" + oldSurname + "] -> [" + newSurname + "]\n" +
                    "\tNickname: [" + oldNickname + "] -> [" + newNickname + "]\n" +
                    "\tDate of Birth: [" + oldDateOfBirth + "] -> [" + newDateOfBirth + "]\n" +
                    "\tNationality: [" + oldNationality + "] -> [" + newNationality + "]\n" +
                    "\tWeight(kg): [" + oldWeightInKg + "] -> [" + newWeightInKg + "]\n" +
                    "\tHeight(cm): [" + oldHeightInCm + "] -> [" + newHeightInCm + "]";
        }
        return "Fighter with id " + fighterId + " does not exist!";
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
