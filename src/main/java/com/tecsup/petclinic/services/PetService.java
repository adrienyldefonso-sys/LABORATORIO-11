package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exceptions.PetNotFoundException;

import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
public interface PetService {

	public PetDTO create(PetDTO petDTO);

	PetDTO update(PetDTO pet);


	void delete(Integer id) throws PetNotFoundException;


	PetDTO findById(Integer id) throws PetNotFoundException;

	List<PetDTO> findByName(String name);

	List<Pet> findByTypeId(int typeId);


	List<Pet> findByOwnerId(int ownerId);


	List<Pet> findAll();
}