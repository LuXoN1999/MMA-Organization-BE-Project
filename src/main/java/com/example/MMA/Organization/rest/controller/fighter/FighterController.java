package com.example.MMA.Organization.rest.controller.fighter;


import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import com.example.MMA.Organization.rest.service.fighter.FighterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/fighter")
public class FighterController {
    private final FighterService fighterService;

    @Autowired
    public FighterController(FighterService fighterService) {
        this.fighterService = fighterService;
    }

    @GetMapping
    public List<Fighter> getAllFighters(){
        return fighterService.getAllFighters();
    }

    @PostMapping
    public void addNewFighter(@RequestBody Fighter fighterToAdd){
        fighterService.addNewFighter(fighterToAdd);
    }

    @PutMapping(path = "/{fighterId}/division/{divisionId}")
    public void addFighterToDivision(
            @PathVariable Long divisionId,
            @PathVariable Long fighterId
    ) {
        fighterService.addFighterToDivision(divisionId,fighterId);
    }

    @DeleteMapping(path = "/{fighterId}")
    public void deleteFighter(@PathVariable("fighterId") Long fighterId){
        fighterService.deleteFighter(fighterId);

    }

    @PutMapping(path = "/{fighterId}")
    public void updateFighter(@PathVariable("fighterId") Long fighterId,
                              @RequestParam (required = false) String newName,
                              @RequestParam (required = false) String newSurname,
                              @RequestParam (required = false) String newNickname,
                              @RequestParam (required = false) LocalDate newDateOfBirth,
                              @RequestParam (required = false) String newNationality,
                              @RequestParam (required = false) Double newWeightInKg,
                              @RequestParam (required = false) Double newHeightInCm){
        fighterService.updateFighter(fighterId,
                newName,newSurname,
                newNickname,
                newDateOfBirth,
                newNationality,
                newWeightInKg,
                newHeightInCm);
    }



}
