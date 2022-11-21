package com.example.MMA.Organization.rest.service.fighter;

import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
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

    @Autowired
    public FighterService(FighterRepository fighterRepository) {
        this.fighterRepository = fighterRepository;
    }

    public List<Fighter> getAllFighters(){
        return fighterRepository.findAll();
    }

    public void addNewFighter(Fighter fighterToAdd){
        fighterRepository.save(fighterToAdd);
    }

    public void deleteFighter(Long fighterId){
        fighterRepository.deleteById(fighterId);
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
