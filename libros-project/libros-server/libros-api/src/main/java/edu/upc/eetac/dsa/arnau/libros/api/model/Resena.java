package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.arnau.libros.api.links.Link;

public class Resena {

	private int idresena;
	private int idlibro;
	private String username;
	private String name;
	private String texto;
	private java.util.Date fecha_creacion;
	private List<Link> links = new ArrayList<Link>();


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public java.util.Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(java.util.Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public int getIdlibro() {
		return idlibro;
	}

	public void setIdlibro(int idlibro) {
		this.idlibro = idlibro;
	}

	public int getIdresena() {
		return idresena;
	}

	public void setIdresena(int idresena) {
		this.idresena = idresena;
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
