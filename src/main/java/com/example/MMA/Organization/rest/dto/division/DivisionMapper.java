package com.example.MMA.Organization.rest.dto.division;

import com.example.MMA.Organization.persistence.entity.division.Division;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DivisionMapper {
    Division toEntity(DivisionDTO divisionDTO);
    DivisionDTO toDTO(Division division);
}
