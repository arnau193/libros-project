package edu.upc.eetac.dsa.arnau.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.arnau.libros.api.model.LibrosError;

public class NotAllowedException extends WebApplicationException {
	private final static String MESSAGE = "You are not allowed";

	public NotAllowedException() {
		super(Response
				.status(Response.Status.FORBIDDEN)
				.entity(new LibrosError(Response.Status.FORBIDDEN
						.getStatusCode(), MESSAGE))
				.type(MediaType.LIBROS_API_ERROR).build());
	}

}