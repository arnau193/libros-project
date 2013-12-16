package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;


public class User {

	public String username;
	public String name;
	public String email;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}