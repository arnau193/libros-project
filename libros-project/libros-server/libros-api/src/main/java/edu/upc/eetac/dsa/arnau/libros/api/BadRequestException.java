package edu.upc.eetac.dsa.arnau.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.arnau.libros.api.model.LibrosError;

public class BadRequestException extends WebApplicationException {

	public BadRequestException(String message) {
		super(Response
				.status(Response.Status.BAD_REQUEST)
				.entity(new LibrosError(Response.Status.BAD_REQUEST
						.getStatusCode(), message))
				.type(MediaType.LIBROS_API_ERROR).build());
	}
	
}
