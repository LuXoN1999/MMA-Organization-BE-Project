package com.example.MMA.Organization.persistence.entity.division;

import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

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


    public Division(Long id,
                    @NonNull String name,
                    @NonNull Double minWeight,
                    @NonNull Double maxWeight,
                    @NonNull Integer maxNumberOfFighters){
        this.id = id;
        this.name = name;
        this.minWeightInKg = minWeight;
        this.maxWeightInKg = maxWeight;
        this.maxNumberOfFighters = maxNumberOfFighters;
    }

    public Division(@NonNull String name,
                    @NonNull Double minWeight,
                    @NonNull Double maxWeight,
                    @NonNull Integer maxNumberOfFighters){
        this.name = name;
        this.minWeightInKg = minWeight;
        this.maxWeightInKg = maxWeight;
        this.maxNumberOfFighters = maxNumberOfFighters;
    }

    public Division(Long id, @NonNull String name, @NonNull Double minWeightInKg, @NonNull Double maxWeightInKg) {
        this.id = id;
        this.name = name;
        this.minWeightInKg = minWeightInKg;
        this.maxWeightInKg = maxWeightInKg;
    }

    public Division(@NonNull String name, @NonNull Double minWeightInKg, @NonNull Double maxWeightInKg) {
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

    public Integer getCurrentNumberOfFighters(){
        return fighters.size();
    }

    @Override
    public String toString() {
        return "{\n" +
                "\tid= " + id +
                ",\n\tname= '" + name + '\'' +
                ",\n\tminWeightInKg= " + minWeightInKg +
                ",\n\tmaxWeightInKg= " + maxWeightInKg +
                ",\n\tmaxNumberOfFighters= " + maxNumberOfFighters +
                "\n}";
    }


}
