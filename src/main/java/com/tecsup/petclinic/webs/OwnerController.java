package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.services.OwnerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/owners")
    public ResponseEntity<List<OwnerDTO>> findByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(ownerService.findByLastName(lastName));
    }

    @PostMapping("/owners")
    public ResponseEntity<OwnerDTO> create(@Valid @RequestBody OwnerDTO ownerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerService.create(ownerDTO));
    }
}