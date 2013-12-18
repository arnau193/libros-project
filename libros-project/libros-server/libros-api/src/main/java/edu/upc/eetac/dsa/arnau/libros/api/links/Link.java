package edu.upc.eetac.dsa.arnau.libros.api.links;

public class Link {

	private String uri; // URL absoluta
	private String rel;
	private String type; // indica el tipo de media que respone o consume la uri
	private String title; // descripción legible de lo que hay en la uri

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
