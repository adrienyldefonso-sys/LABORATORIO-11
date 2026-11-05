package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * 
 * @author jgomezm
 *
 */
@Entity
@Table(name = "owners")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "telephone")
	private String telephone;

//	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@ToString.Exclude
//	// @EqualsAndHashCode.Exclude
//	private Set<Pet> pets;
}