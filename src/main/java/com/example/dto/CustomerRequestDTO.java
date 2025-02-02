package com.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
@Getter
@Setter
public class CustomerRequestDTO {
    private String name;
    private String email;
    private List<LocationRequestDTO> locations;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LocationRequestDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationRequestDTO> locations) {
        this.locations = locations;
    }
}
