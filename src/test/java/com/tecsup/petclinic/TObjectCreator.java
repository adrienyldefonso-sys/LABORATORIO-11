package com.tecsup.petclinic;

import com.tecsup.petclinic.dtos.OwnerDTO;

/**
 * Clase de utilidad para crear objetos de prueba (DTOs y Entidades).
 * Facilita la creación de datos estáticos para las pruebas de integración.
 */
public class TObjectCreator {

    public static OwnerDTO getOwnerTO() {
        return OwnerDTO.builder()
                .id(1L)
                .firstName("George")
                .lastName("Franklin")
                .address("110 W. Liberty St.")
                .city("Madison")
                .telephone("6085551023")
                .build();
    }
}