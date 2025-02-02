package com.example.mapper;

import com.example.dto.LocationRequestDTO;
import com.example.dto.LocationResponseDTO;
import com.example.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    Location toEntity(LocationRequestDTO dto);

    LocationResponseDTO toResponseDTO(Location location);
}
