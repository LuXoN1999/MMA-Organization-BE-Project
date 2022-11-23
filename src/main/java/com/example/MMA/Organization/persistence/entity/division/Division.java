package com.example.MMA.Organization.persistence.entity.division;

import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "division")
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double minWeightInKg;
    private Double maxWeightInKg;
    private Integer maxNumberOfFighters = 15;

    @JsonIgnore
    @OneToMany(mappedBy = "division",fetch=FetchType.EAGER)
    private List<Fighter> fighters;


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

    public void addFighter(Fighter fighter){
        fighters.add(fighter);
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

    public List<Fighter> getFighters() {
        return fighters;
    }
}
