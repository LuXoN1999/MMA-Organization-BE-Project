package com.example.MMA.Organization.persistence.entity.division;

import javax.persistence.*;
@Entity
@Table(name = "division")
public class Division {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double minWeightInKg;
    private Double maxWeightInKg;
    private Integer maxNumberOfFighters = 15;


    public Division(){

    }

    public Division(Long id, String name, Double minWeight, Double maxWeight, Integer maxNumberOfFighters){
        this.id = id;
        this.name = name;
        this.minWeightInKg = minWeight;
        this.maxWeightInKg = maxWeight;
        this.maxNumberOfFighters = maxNumberOfFighters;
    }

    public Division(String name, Double minWeight, Double maxWeight, Integer maxNumberOfFighters){
        this.name = name;
        this.minWeightInKg = minWeight;
        this.maxWeightInKg = maxWeight;
        this.maxNumberOfFighters = maxNumberOfFighters;
    }

    public Division(Long id, String name, Double minWeightInKg, Double maxWeightInKg) {
        this.id = id;
        this.name = name;
        this.minWeightInKg = minWeightInKg;
        this.maxWeightInKg = maxWeightInKg;
    }

    public Division(String name, Double minWeightInKg, Double maxWeightInKg) {
        this.name = name;
        this.minWeightInKg = minWeightInKg;
        this.maxWeightInKg = maxWeightInKg;
    }

    public Long getId() { return id;}

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinWeight() {
        return minWeightInKg;
    }

    public void setMinWeight(Double minWeight) {
        this.minWeightInKg = minWeight;
    }

    public Double getMaxWeight() {
        return maxWeightInKg;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeightInKg = maxWeight;
    }

    public Integer getMaxNumberOfFighters() {
        return maxNumberOfFighters;
    }

    public void setMaxNumberOfFighters(Integer maxNumberOfFighters) {
        this.maxNumberOfFighters = maxNumberOfFighters;
    }
}
