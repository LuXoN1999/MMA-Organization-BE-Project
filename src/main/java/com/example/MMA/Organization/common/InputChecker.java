package com.example.MMA.Organization.common;

import com.example.MMA.Organization.persistence.entity.division.Division;
import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import org.apache.commons.lang3.Range;

import java.util.List;
import java.util.Objects;

public class InputChecker {

    public static boolean allRequestParametersAreNull(List<Object> parameters){
        for(Object parameter: parameters){
            if(parameter != null){
                return false;
            }
        }
        return true;
    }

    public static boolean divisionNameIsTaken(String name, List<Division> allDivisions){
        for(Division element: allDivisions){
            if(Objects.equals(element.getName(),name)){
                return true;
            }
        }
        return false;
    }

    public static Division weightRangeOverlapped(Double minWeight, Double maxWeight, List<Division> allDivisions){
        Range<Double> divisionWeightRange = Range.between(minWeight,maxWeight);
        for(Division element: allDivisions){
            Range<Double> elementWeightRange = Range.between(element.getMinWeight(), element.getMaxWeight());
            if(divisionWeightRange.isOverlappedBy(elementWeightRange)){
                return element;
            }
        }
        return null;
    }

    public static boolean fighterNicknameIsTaken(String nickname, List<Fighter> allFighters){
        for(Fighter element : allFighters){
            if(Objects.equals(element.getNickname(),nickname)){
                return true;
            }
        }
        return false;
    }
}
