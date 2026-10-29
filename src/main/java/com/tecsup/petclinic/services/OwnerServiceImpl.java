package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public OwnerDTO findById(Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found: " + id));
        return toDTO(owner);
    }

    @Override
    public List<OwnerDTO> findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OwnerDTO> findByCity(String city) {
        return ownerRepository.findByCity(city)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OwnerDTO toDTO(Owner o) {
        return OwnerDTO.builder()
                .id(o.getId())
                .firstName(o.getFirstName())
                .lastName(o.getLastName())
                .address(o.getAddress())
                .city(o.getCity())
                .telephone(o.getTelephone())
                .build();
    }
}