package edu.upc.eetac.dsa.arnau.libros.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.arnau.libros.api.links.LibrosAPILinkBuilder;
import edu.upc.eetac.dsa.arnau.libros.api.model.LibrosRootAPI;

//TODO: URI /
@Path("/")
public class LibrosRootAPIResource {
	@Context
	private UriInfo uriInfo;

	// TODO: Return links
	@GET
	@Produces(MediaType.LIBROS_API_LINK_COLLECTION)
	public LibrosRootAPI getLinks() {
		LibrosRootAPI root = new LibrosRootAPI();

		root.add(LibrosAPILinkBuilder.buildURIRootAPI(uriInfo));
		root.add(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo,
				"libros"));
		root.add(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo,
				"libros"));
		root.add(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo,
				"resenas"));
		root.add(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo,
				"resenas"));
		root.add(LibrosAPILinkBuilder.buildURILibros(uriInfo, "create"));

		return root;

	}

}
