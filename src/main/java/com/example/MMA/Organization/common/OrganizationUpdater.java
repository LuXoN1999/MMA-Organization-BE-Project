package com.example.MMA.Organization.common;

import com.example.MMA.Organization.persistence.entity.division.Division;
import com.example.MMA.Organization.persistence.entity.fighter.Fighter;

import java.util.List;

public class OrganizationUpdater {
    public static void sortOutFightersByDivision(List<Division> allDivisions, List<Fighter> allFighters){
        for(Fighter fighter : allFighters){
            fighter.assignDivision(FighterChecker.findDivisionByWeight(
                    fighter.getWeightInKg(),
                    allDivisions));
        }
    }
}
