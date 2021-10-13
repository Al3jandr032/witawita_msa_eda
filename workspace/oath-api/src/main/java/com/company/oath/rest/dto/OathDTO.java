package com.company.oath.rest.dto;

/**
 *
 * @author bankaya
 */
public class OathDTO {
	
	private int id;
	private String description;

	
	public OathDTO () {
		
	}
	
	public OathDTO (int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	
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
	
	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", description=" + description + "]";
	}
}