package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;

public class ResenaCollection {

	private List<Resena> resenas = new ArrayList<Resena>();


	public void add(Resena resena) {
		resenas.add(resena);
	}


	public List<Resena> getResenas() {
		return resenas;
	}


	public void setResenas(List<Resena> resenas) {
		this.resenas = resenas;
	}



}