package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.arnau.libros.api.links.Link;

public class UserCollection {

private List<User> users = new ArrayList<User>();
private List<Link> links = new ArrayList<Link>();


	public void add(User user) {
		users.add(user);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	public void add(Link link) {
		links.add(link);
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
