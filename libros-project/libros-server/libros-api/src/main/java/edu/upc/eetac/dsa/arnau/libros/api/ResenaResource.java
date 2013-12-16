package edu.upc.eetac.dsa.arnau.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.arnau.libros.api.InternalServerException;
import edu.upc.eetac.dsa.arnau.libros.api.MediaType;
import edu.upc.eetac.dsa.arnau.libros.api.ServiceUnavailableException;
import edu.upc.eetac.dsa.arnau.libros.api.ResenaNotFoundException;
import edu.upc.eetac.dsa.arnau.libros.api.model.Libro;
import edu.upc.eetac.dsa.arnau.libros.api.model.LibroCollection;
import edu.upc.eetac.dsa.arnau.libros.api.model.Resena;
import edu.upc.eetac.dsa.arnau.libros.api.model.ResenaCollection;

@Path("/libros/{idlibro}/resenas")
public class ResenaResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	String rel = null;

	@GET
	@Produces(MediaType.LIBROS_API_RESENA_COLLECTION)
	public ResenaCollection getReseñas(@PathParam("idlibro") String idlibro) {
		ResenaCollection resenas = new ResenaCollection();

		Connection conn = null;
		Statement stmt = null;
		String sql;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM resenas where idlibro='" + idlibro + "'";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Resena resena = new Resena();
				resena.setIdresena(rs.getInt("idresena"));
				resena.setIdlibro(rs.getInt("idlibro"));
				resena.setUsername(rs.getString("username"));
				resena.setName(rs.getString("name"));
				resena.setTexto(rs.getString("texto"));
				resena.setFecha_creacion(rs.getDate("fecha_creacion"));
				resenas.add(resena);

			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}

		finally {
			try {
				stmt.close();
				conn.close();
			}

			catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return resenas;

	}

	@DELETE
	@Path("/{idresena}")
	public void deleteResena(@PathParam("idlibro") String idlibro,
			@PathParam("idresena") String idresena) {
		// TODO Delete record in database stings identified by stingid.

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			String sql;

			sql = "DELETE FROM resenas WHERE idlibro='" + idlibro
					+ "' AND idresena='" + idresena + "'";
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}

		finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
