package com.example.dto;

import lombok.Data;

@Data
public class LocationRequestDTO {
    private String city;
    private double latitude;
    private double longitude;
}
