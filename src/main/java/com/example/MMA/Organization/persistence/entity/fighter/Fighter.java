package com.example.MMA.Organization.persistence.entity.fighter;

import com.example.MMA.Organization.persistence.entity.division.Division;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fighter")
public class Fighter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private LocalDate dateOfBirth;
    private String nationality;
    private Double weightInKg;
    private Double heightInCm;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "divisionId",referencedColumnName = "id")
    private Division division;

    public Fighter(){

    }

    @Override
    public String toString() {
        return "Fighter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", nationality='" + nationality + '\'' +
                ", weightInKg=" + weightInKg +
                ", heightInCm=" + heightInCm +
                '}';
    }

    public Fighter(Long id, String name, String surname, String nickname, LocalDate dateOfBirth, String nationality, Double weightInKg, Double heightInCm) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.weightInKg = weightInKg;
        this.heightInCm = heightInCm;
    }

    public Fighter(String name, String surname, String nickname, LocalDate dateOfBirth, String nationality, Double weightInKg, Double heightInCm) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.weightInKg = weightInKg;
        this.heightInCm = heightInCm;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public void assignDivision(Division division){
        this.division = division;
    }

    public void leaveDivision(){ this.division = null; }

}
