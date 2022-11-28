package com.example.MMA.Organization.rest.controller.fighter;


import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import com.example.MMA.Organization.rest.dto.fighter.FighterDTO;
import com.example.MMA.Organization.rest.service.fighter.FighterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @PostMapping
    public String addNewFighter(@RequestBody Fighter fighterToAdd){
        return fighterService.addNewFighter(fighterToAdd);
    }

    @GetMapping
    public List<FighterDTO> getAllFighters(){return fighterService.getAllFighters(); }


    @DeleteMapping(path = "/{fighterId}")
    public String deleteFighter(@PathVariable("fighterId") Long fighterId){
        return fighterService.deleteFighter(fighterId);

    }

    @PutMapping(path = "/{fighterId}")
    public String updateFighter(@PathVariable("fighterId") Long fighterId,
                              @RequestParam (required = false) String newName,
                              @RequestParam (required = false) String newSurname,
                              @RequestParam (required = false) String newNickname,
                              @RequestParam (required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newDateOfBirth,
                              @RequestParam (required = false) String newNationality,
                              @RequestParam (required = false) Double newWeightInKg,
                              @RequestParam (required = false) Double newHeightInCm){
        return fighterService.updateFighter(fighterId,
                newName,newSurname,
                newNickname,
                newDateOfBirth,
                newNationality,
                newWeightInKg,
                newHeightInCm);
    }

    @PutMapping(path = "/{fighterId}/division/{divisionId}")
    public void addFighterToDivision(
            @PathVariable Long divisionId,
            @PathVariable Long fighterId
    ) {
        fighterService.addFighterToDivision(divisionId,fighterId);
    }



}
