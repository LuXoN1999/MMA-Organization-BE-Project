package com.example.MMA.Organization.common;

import com.example.MMA.Organization.persistence.entity.division.Division;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;


public class FighterChecker {
    public static boolean nameIsValid(@NonNull String name){
            return name.length() > 1 && !nameContainsNumbers(name);
    }

    public static boolean surnameIsValid(@NonNull String surname){
        return surname.length() > 1 && !nameContainsNumbers(surname);
    }

    private static boolean nameContainsNumbers(String name){
        char[] chars = name.toCharArray();
        for(char character: chars){
            if(Character.isDigit(character)){
                return true;
            }
        }
        return false;
    }

    public static boolean nicknameIsValid(@NonNull String nickname){
        return nickname.length() > 0 && !NumberUtils.isParsable(nickname);
    }

    public static boolean fighterIsAMinor(@NonNull LocalDate dateOfBirth){
        return LocalDate.now().getYear() - dateOfBirth.getYear() < 18;
    }

    public static boolean fighterAgeIsValid(@NonNull LocalDate dateOfBirth){
        return LocalDate.now().getYear() - dateOfBirth.getYear() >= 0;
    }

    public static boolean weightIsValid(@NonNull Double weight){
        return weight > 0 && !Double.isNaN(weight);
    }

    public static boolean heightIsValid(@NonNull Double height){
        return height > 0 && !Double.isNaN(height);
    }

    public static Division findDivisionByWeight(@NonNull Double weight, List<Division> allDivisions){
        if(allDivisions == null){
            return null;
        }
        for(Division division : allDivisions) {
            Range<Double> divisionWeightRange = Range.between(division.getMinWeight(), division.getMaxWeight());
            if (divisionWeightRange.contains(weight)) {
                return division;
            }
        }
        return null;
    }

}
