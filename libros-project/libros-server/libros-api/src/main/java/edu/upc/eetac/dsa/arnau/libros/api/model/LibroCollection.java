package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.arnau.libros.api.links.Link;


public class LibroCollection {

	private List<Libro> libros = new ArrayList<Libro>();
	private List<Link> links = new ArrayList<Link>();

	public void add(Libro libro) {
		libros.add(libro);
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
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
