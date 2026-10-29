package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;


    @Test
    public void testFindOwnerById() {

        final String FIRST_NAME_EXPECTED = "George";
        final String LAST_NAME_EXPECTED = "Franklin";

        Integer ID = 1;

        OwnerDTO owner = null;

        try {
            owner = this.ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("OWNER FOUND :" + owner);

        assertEquals(FIRST_NAME_EXPECTED, owner.getFirstName());
        assertEquals(LAST_NAME_EXPECTED, owner.getLastName());
    }

    @Test
    public void testFindByLastName() {

        String LAST_NAME = "Davis";
        int SIZE_EXPECTED = 2;

        List<OwnerDTO> owners = this.ownerService.findByLastName(LAST_NAME);

        log.info("OWNERS FOUND :" + owners);

        assertEquals(SIZE_EXPECTED, owners.size());
    }
}
