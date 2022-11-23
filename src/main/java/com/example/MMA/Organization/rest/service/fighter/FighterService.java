package com.example.MMA.Organization.rest.service.fighter;

import com.example.MMA.Organization.persistence.entity.division.Division;
import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import com.example.MMA.Organization.persistence.repository.division.DivisionRepository;
import com.example.MMA.Organization.persistence.repository.fighter.FighterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
public class FighterService {

    private final FighterRepository fighterRepository;
    private final DivisionRepository divisionRepository;
    @Autowired
    public FighterService(FighterRepository fighterRepository, DivisionRepository divisionRepository) {
        this.fighterRepository = fighterRepository;
        this.divisionRepository = divisionRepository;
    }

    public List<Fighter> getAllFighters(){
        return fighterRepository.findAll();
    }

    public void addNewFighter(Fighter fighterToAdd){
        fighterRepository.save(fighterToAdd);
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

    public void deleteFighter(Long fighterId){
        boolean fighterExists = fighterRepository.existsById(fighterId);
        if(fighterExists){
            fighterRepository.deleteById(fighterId);
        }
    }


    @Transactional
    public void updateFighter(Long fighterId,
                              String newName,
                              String newSurname,
                              String newNickname,
                              LocalDate newDateOfBirth,
                              String newNationality,
                              Double newWeightInKg,
                              Double newHeightInCm
                              ){
        Fighter chosenFighter = fighterRepository.findById(fighterId).orElseThrow(() -> new IllegalStateException("Fighter with id(" + fighterId + ") is not found."));
        if(newName != null && newName.length() > 0 && Objects.equals(chosenFighter.getName(),newName)){
            chosenFighter.setName(newName);
        }
        if(newSurname != null && newSurname.length() > 0 && !Objects.equals(chosenFighter.getSurname(),newName)){
            chosenFighter.setSurname(newSurname);
        }
        if(newNickname != null && newNickname.length() > 0 && !Objects.equals(chosenFighter.getNickname(),newNickname)){
            chosenFighter.setNickname(newNickname);
        }
        if(newDateOfBirth != null && !Objects.equals(chosenFighter.getDateOfBirth(),newDateOfBirth)){
            chosenFighter.setDateOfBirth(newDateOfBirth);
        }
        if(newNationality != null && newNationality.length() > 0 && !Objects.equals(chosenFighter.getNationality(),newNationality)){
            chosenFighter.setNationality(newNationality);
        }
        if(newWeightInKg != null && newWeightInKg > 0){
            chosenFighter.setWeightInKg(newWeightInKg);
        }
        if(newHeightInCm != null && newHeightInCm > 0){
            chosenFighter.setHeightInCm(newHeightInCm);
        }
    }
}
