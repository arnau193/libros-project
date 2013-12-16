package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;



public class UserCollection {

	private List<User> users = new ArrayList<User>();

	public void add(User user) {
		users.add(user);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
