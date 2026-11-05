package com.tecsup.petclinic.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDTO {

    private Long id;
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    private String address;
    private String city;
    private String telephone;
}