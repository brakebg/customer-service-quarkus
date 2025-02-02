package com.example.mapper;

import com.example.dto.CustomerRequestDTO;
import com.example.dto.CustomerResponseDTO;
import com.example.dto.LocationRequestDTO;
import com.example.model.Customer;
import com.example.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

/*    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criteria", ignore = true)
    @Mapping(target = "customer", ignore = true)*/
  //  Location toEntity(LocationRequestDTO dto);

    Customer toEntity(CustomerRequestDTO dto);

    CustomerResponseDTO toResponseDTO(Customer customer);
}
