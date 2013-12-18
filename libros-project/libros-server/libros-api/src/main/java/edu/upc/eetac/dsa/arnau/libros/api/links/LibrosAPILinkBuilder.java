package edu.upc.eetac.dsa.arnau.libros.api.links;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.arnau.libros.api.LibrosRootAPIResource;
import edu.upc.eetac.dsa.arnau.libros.api.MediaType;
import edu.upc.eetac.dsa.arnau.libros.api.LibroResource;
import edu.upc.eetac.dsa.arnau.libros.api.UserResource;
import edu.upc.eetac.dsa.arnau.libros.api.model.Libro;
import edu.upc.eetac.dsa.arnau.libros.api.model.User;
import edu.upc.eetac.dsa.arnau.libros.api.ResenaResource;
import edu.upc.eetac.dsa.arnau.libros.api.model.Resena;

public class LibrosAPILinkBuilder {
	public final static Link buildURIRootAPI(UriInfo uriInfo) {
		URI uriRoot = uriInfo.getBaseUriBuilder()
				.path(LibrosRootAPIResource.class).build();// nos devuelve
															// http://localhost:8080/libros-api
		Link link = new Link(); // utilizamos la clase link para construir el
								// enlace
		link.setUri(uriRoot.toString());
		link.setRel("self bookmark"); // decirle al cliente la pagina inicial
		link.setTitle("Libros API"); // descripción
		link.setType(MediaType.LIBROS_API_LINK_COLLECTION); /*
															 * devolver una
															 * colección de
															 * enlaces
															 */

		return link;
	}


	public static final Link buildURILibros(UriInfo uriInfo, String rel) { // poner la uri con
															// query parámetros
		URI uriLibros;
		uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class).build();
		Link self = new Link();
		self.setUri(uriLibros.toString());
		self.setRel("self");
		self.setTitle("Libros collection");
		self.setType(MediaType.LIBROS_API_LIBRO_COLLECTION);

		return self;
	}

	public static final Link buildTemplatedURILibros(UriInfo uriInfo, String rel) {

		return buildTemplatedURILibros(uriInfo, rel);
	}


	public final static Link buildURILibro(UriInfo uriInfo, Libro libro) {
		URI libroURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.build(); // http://localhost:8080/beeter-api/stings
		Link link = new Link();
		link.setUri(libroURI.toString());
		link.setRel("self");
		link.setTitle("Libro " + libro.getIdlibro());
		link.setType(MediaType.LIBROS_API_LIBRO);

		return link;
	}

	public final static Link buildURILibroId(UriInfo uriInfo, String idlibro,
			String rel) {
		// http://localhost:8080/beeter-api/stings/{valor sting}
		URI libroURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.path(LibroResource.class, "getLibro").build(idlibro);
		Link link = new Link();
		link.setUri(libroURI.toString());
		link.setRel("self");
		link.setTitle("Libro " + idlibro);
		link.setType(MediaType.LIBROS_API_LIBRO);

		return link;
	}

	
	
	
	
	
	// Users

	public static final Link buildURIUsers(UriInfo uriInfo, String rel) {
		URI uriUsers;

		uriUsers = uriInfo.getBaseUriBuilder().path(UserResource.class).build();

		Link self = new Link();
		self.setUri(uriUsers.toString());
		self.setRel(rel);
		self.setTitle("Users collection");
		self.setType(MediaType.LIBROS_API_USER_COLLECTION);

		return self;
	}

	public static final Link buildTemplatedURIUsers(UriInfo uriInfo, String rel) {

		return buildTemplatedURIUsers(uriInfo, rel, false, false);
	}

	public static final Link buildTemplatedURIUsers(UriInfo uriInfo,
			String rel, boolean name, boolean email)
	// no toman un valor en concreto sino que es una plantilla
	{
		URI uriLibros;
		if (email && name)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("username", "{username}")
					.queryParam("name", "{name}")
					.queryParam("email", "{email}").build();
		else if (name)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("username", "{username}")
					.queryParam("name", "{name}").build();
		else
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("username", "{username}")
					.queryParam("email", "{email}").build();

		Link link = new Link();
		link.setUri(URITemplateBuilder.buildTemplatedURI(uriLibros));
		link.setRel(rel);

		link.setType(MediaType.LIBROS_API_USER_COLLECTION);

		return link;
	}

	public final static Link buildURIUserName(UriInfo uriInfo, String username,
			String rel) {
		URI userURI = uriInfo.getBaseUriBuilder().path(UserResource.class)
				.path(UserResource.class, "getUser").build(username);
		Link link = new Link();
		link.setUri(userURI.toString());
		link.setRel("self");
		link.setTitle("User " + username);
		link.setType(MediaType.LIBROS_API_USER);

		return link;
	}


	// Reseñas


public final static Link buildURIResenaId(UriInfo uriInfo, String idresena, String idlibro,
		String rel) {
	// http://localhost:8080/beeter-api/stings/{valor sting}
	URI resenaURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
			.path("/" + idlibro).path("/resenas/").path("/" + idresena).build();
	Link link = new Link();
	link.setUri(resenaURI.toString());
	link.setRel("self");
	link.setTitle("Resena " + idresena);
	link.setType(MediaType.LIBROS_API_RESENA);

	return link;
}

public static final Link buildTemplatedURIResenas(UriInfo uriInfo, String rel) {

	return buildTemplatedURIResenas(uriInfo, rel);
}

public static final Link buildURIResenas(UriInfo uriInfo,String idlibro, String rel) {

	URI uriResenas = uriInfo.getBaseUriBuilder().path(LibroResource.class)
			.path("/" + idlibro).path("/resenas/").build();

	Link self = new Link();
	self.setUri(uriResenas.toString());
	self.setRel(rel);
	self.setTitle("Reseñas collection");
	self.setType(MediaType.LIBROS_API_RESENA_COLLECTION);

	return self;
}



}



