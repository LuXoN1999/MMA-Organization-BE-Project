package com.example.MMA.Organization.rest.dto.fighter;

import com.example.MMA.Organization.persistence.entity.fighter.Fighter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FighterMapper {
    Fighter toEntity(FighterDTO fighterDTO);

    FighterDTO toDTO(Fighter fighter);
}
