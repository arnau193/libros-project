package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;


public class LibroCollection {

	private List<Libro> libros = new ArrayList<Libro>();

	public void add(Libro libro) {
		libros.add(libro);
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

}
