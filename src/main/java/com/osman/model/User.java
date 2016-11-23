package com.osman.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*This class Represents the mongoDb User Documents*/
@Document
public class User {
	@Id
	private String id;
	private String name;
	
	private String surname;
	private String telephone;
	
	public User() {
	}
	
	public User(String name, String surname, String telephone) {
		setName(name);
		setSurname(surname);
		setTelephone(telephone);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", telephone=" + telephone + "]";
	}
}
