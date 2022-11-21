package com.example.MMA.Organization.rest.controller.division;

import com.example.MMA.Organization.persistence.entity.division.Division;
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

    @GetMapping
    public List<Division> getAllDivisions(){ return divisionService.getAllDivisions(); }

    @PostMapping
    public void addNewDivision(@RequestBody Division division) { divisionService.addNewDivision(division); }

    @DeleteMapping(path = "{divisionId}")
    public void deleteDivision(@PathVariable("divisionId") Long divisionId){
        divisionService.deleteDivision(divisionId);
    }

    @PutMapping(path = "{divisionId}")
    public void updateDivision(@PathVariable("divisionId") Long divisionId,
                             @RequestParam (required = false) String newName,
                             @RequestParam (required = false) Double newMinWeight,
                             @RequestParam (required = false) Double newMaxWeight,
                             @RequestParam (required = false) Integer newMaxNumberOfFighters){
        divisionService.updateDivision(divisionId,newName,newMinWeight,newMaxWeight,newMaxNumberOfFighters);
    }


}
