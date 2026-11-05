package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.OwnerDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;          // golpea H2 real, SIN mocks

    @Test
    public void testCreateOwner() throws Exception {

        String FIRST_NAME = "Ana";
        String LAST_NAME = "Torres";
        String ADDRESS = "123 Main St.";
        String CITY = "Lima";
        String TELEPHONE = "6085551111";

        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .build();

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(newOwner)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.address", is(ADDRESS)))
                .andExpect(jsonPath("$.city", is(CITY)))
                .andExpect(jsonPath("$.telephone", is(TELEPHONE)));
    }

    @Test
    public void testCreateOwner_BadRequest() throws Exception {

        // Cuerpo inválido: falta lastName
        OwnerDTO invalidOwner = OwnerDTO.builder()
                .firstName("Ana")
                .address("123 Main St.")
                .city("Lima")
                .telephone("6085551111")
                .build();

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(invalidOwner)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindByLastName() throws Exception {

        String LAST_NAME = "Davis";
        int SIZE_EXPECTED = 2;

        mockMvc.perform(get("/owners").param("lastName", LAST_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(SIZE_EXPECTED)))
                .andExpect(jsonPath("$[0].lastName", is(LAST_NAME)));
    }
}