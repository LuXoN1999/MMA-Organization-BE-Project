package com.example.MMA.Organization.common;

import org.apache.commons.lang3.math.NumberUtils;

public class DivisionChecker {

    public static Boolean weightsAreValid(Double divisionMinWeight, Double divisionMaxWeight){
        return !(divisionMinWeight > divisionMaxWeight)
                && !(divisionMinWeight < 0)
                && !(divisionMaxWeight < 0)
                && !(Double.isNaN(divisionMinWeight))
                && !(Double.isNaN(divisionMaxWeight));
    }

    public static Boolean maxNumberOfFightersIsValid(Integer divisionMaxNumberOfFighters){
        return divisionMaxNumberOfFighters >= 0;
    }

    public static Boolean nameIsValid(String divisionName){
        return  (divisionName.length() > 3) && (!NumberUtils.isParsable(divisionName));
    }


}
