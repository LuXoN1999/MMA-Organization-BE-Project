package com.example.MMA.Organization.rest.controller.division;

import com.example.MMA.Organization.persistence.entity.division.Division;
import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import com.example.MMA.Organization.rest.dto.division.DivisionDTO;
import com.example.MMA.Organization.rest.service.division.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping(path = "api/v1/division")
public class DivisionController {
    private final DivisionService divisionService;

    @Autowired
    public DivisionController(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @PostMapping
    public String addNewDivision(@RequestBody Division division) { return divisionService.addNewDivision(division); }

    @GetMapping
    public List<DivisionDTO> getAllDivisions(){ return divisionService.getAllDivisions(); }

    @GetMapping(path = "/fighters/{divisionId}")
    public List<Fighter> getAllFightersInDivision(@PathVariable Long divisionId){
        return divisionService.getAllFightersInDivision(divisionId);
    }

    @DeleteMapping(path = "/{divisionId}")
    public String deleteDivision(@PathVariable("divisionId") Long divisionId){
        return divisionService.deleteDivision(divisionId);
    }

    @PutMapping(path = "/{divisionId}")
    public String updateDivision(@PathVariable("divisionId") Long divisionId,
                             @RequestParam (required = false) String newName,
                             @RequestParam (required = false) Double newMinWeight,
                             @RequestParam (required = false) Double newMaxWeight,
                             @RequestParam (required = false) Integer newMaxNumberOfFighters){
        return divisionService.updateDivision(divisionId,newName,newMinWeight,newMaxWeight,newMaxNumberOfFighters);
    }


}
