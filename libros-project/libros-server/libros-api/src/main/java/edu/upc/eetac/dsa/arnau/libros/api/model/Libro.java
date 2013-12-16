package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;

public class Libro {

	private String titulo;
	private String autor;
	private String lengua;
	private int edicion;
	private java.util.Date fecha_edicion;
	private java.util.Date fecha_impresion;
	private String editorial;
	private int idlibro;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String id) {
		this.titulo = id;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getLengua() {
		return lengua;
	}

	public void setLengua(String lengua) {
		this.lengua = lengua;
	}

	public int getEdicion() {
		return edicion;
	}

	public void setEdicion(int edicion) {
		this.edicion = edicion;
	}

	public java.util.Date getFecha_edicion() {
		return fecha_edicion;
	}

	public void setFecha_edicion(java.util.Date fecha_edicion) {
		this.fecha_edicion = fecha_edicion;
	}
	public java.util.Date getFecha_impresion() {
		return fecha_impresion;
	}

	public void setFecha_impresion(java.util.Date fecha_impresion) {
		this.fecha_impresion = fecha_impresion;
	}
	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getIdlibro() {
		return idlibro;
	}

	public void setIdlibro(int idlibro) {
		this.idlibro = idlibro;
	}



}
