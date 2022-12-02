package com.example.MMA.Organization.common;

import com.example.MMA.Organization.persistence.entity.division.Division;
import org.apache.commons.lang3.Range;

import java.util.List;
import java.util.Objects;

public class InputChecker {

    public static boolean divisionNameIsTaken(Division chosenDivision, List<Division> allDivisions){
        for(Division element: allDivisions){
            if(Objects.equals(element.getName(),chosenDivision.getName())){
                return true;
            }
        }
        return false;
    }

    public static Division weightRangeOverlapped(Division chosenDivision, List<Division> allDivisions){
        Range<Double> divisionWeightRange = Range.between(chosenDivision.getMinWeight(),chosenDivision.getMaxWeight());
        for(Division element: allDivisions){
            Range<Double> elementWeightRange = Range.between(element.getMinWeight(), element.getMaxWeight());
            if(divisionWeightRange.isOverlappedBy(elementWeightRange)){
                return element;
            }
        }
        return null;
    }
}
