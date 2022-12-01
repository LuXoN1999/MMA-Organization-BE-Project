package com.example.MMA.Organization.rest.dto.fighter;

import com.example.MMA.Organization.persistence.entity.division.Division;

public class FighterDTO {
    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private Double weightInKg;
    private Double heightInCm;
    private Division division;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(Double weightInKg) {
        this.weightInKg = weightInKg;
    }

    public Double getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(Double heightInCm) {
        this.heightInCm = heightInCm;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\nid=" + id +
                ",\n\t name='" + name + '\'' +
                ",\n\t surname='" + surname + '\'' +
                ",\n\t nickname='" + nickname + '\'' +
                ",\n\t weightInKg=" + weightInKg + "kg" +
                ",\n\t heightInCm=" + heightInCm + "cm" +
                ",\n\t division=" + division.getName() +
                "\n}";
    }
}
