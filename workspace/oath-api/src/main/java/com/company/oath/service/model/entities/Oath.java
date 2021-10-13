package com.company.oath.service.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.company.oath.rest.dto.OathDTO;

/**
 *
 * @author bankaya
 */
@Entity
public class Oath {
	@Id
	private int id;
	private String description;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public static Oath fromOathDTO(OathDTO oath) {
		Oath rest = new Oath();
		rest.setId(oath.getId());
		rest.setDescription(oath.getDescription());
		return rest;
	}
	
	public OathDTO toOathDTO() {
		 OathDTO dto = new  OathDTO();
		 dto.setId(this.getId());
		 dto.setDescription(this.getDescription());
		return dto;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", description=" + description + "]";
	}
}