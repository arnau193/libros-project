package edu.upc.eetac.dsa.arnau.libros.api.model;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.arnau.libros.api.links.Link;

public class ResenaCollection {

	private List<Resena> resenas = new ArrayList<Resena>();
	private List<Link> links = new ArrayList<Link>();


	public void add(Resena resena) {
		resenas.add(resena);
	}


	public List<Resena> getResenas() {
		return resenas;
	}


	public void setResenas(List<Resena> resenas) {
		this.resenas = resenas;
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