package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.TObjectCreator;
import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.services.OwnerService;
// Copia aquí el import de TObjectCreator tal como está en PetControllerMockitoTest
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class OwnerControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OwnerService ownerService;   // servicio mockeado

    @Test
    public void testCreateOwner() throws Exception {

        OwnerDTO ownerTO = TObjectCreator.getOwnerTO();

        Mockito.when(ownerService.create(ArgumentMatchers.any(OwnerDTO.class)))
                .thenReturn(ownerTO);

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(ownerTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(ownerTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(ownerTO.getLastName())))
                .andExpect(jsonPath("$.address", is(ownerTO.getAddress())))
                .andExpect(jsonPath("$.city", is(ownerTO.getCity())))
                .andExpect(jsonPath("$.telephone", is(ownerTO.getTelephone())));
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

        // El servicio nunca debe llamarse si el cuerpo es inválido
        Mockito.verifyNoInteractions(ownerService);
    }

    @Test
    public void testFindByLastName() throws Exception {

        String LAST_NAME = "Davis";

        OwnerDTO betty = OwnerDTO.builder().id(2L).firstName("Betty").lastName(LAST_NAME)
                .address("638 Cardinal Ave.").city("Sun Prairie").telephone("6085551749").build();
        OwnerDTO harold = OwnerDTO.builder().id(4L).firstName("Harold").lastName(LAST_NAME)
                .address("563 Friendly St.").city("Windsor").telephone("6085553198").build();

        Mockito.when(ownerService.findByLastName(LAST_NAME))
                .thenReturn(List.of(betty, harold));

        mockMvc.perform(get("/owners").param("lastName", LAST_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[1].lastName", is(LAST_NAME)));
    }
}