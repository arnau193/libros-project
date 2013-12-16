package edu.upc.eetac.dsa.arnau.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.arnau.libros.api.model.Libro;
import edu.upc.eetac.dsa.arnau.libros.api.model.LibroCollection;

@Path("/libros")
public class LibroResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	String rel = null;

	@GET
	@Path("/{idlibro}")
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro getLibro(@PathParam("idlibro") int idlibro,
			@Context Request req) {

		// Create CacheControl
		CacheControl cc = new CacheControl();
		Libro libro = new Libro();

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM libros WHERE idlibro='" + idlibro + "'";
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				libro.setIdlibro(rs.getInt("idlibro"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getInt("edicion"));
				libro.setFecha_edicion(rs.getTimestamp("fecha_edicion"));
				libro.setFecha_impresion(rs.getTimestamp("fecha_impresion"));
				libro.setEditorial(rs.getString("editorial"));

			}

			else
				throw new LibroNotFoundException();

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

		return libro;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.LIBROS_API_LIBRO)
	public LibroCollection getLibroparecido(
			@QueryParam("titulo") String titulo,
			@QueryParam("autor") String autor, @Context Request req) {

		// Create CacheControl
		LibroCollection libros2 = new LibroCollection();

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
			sql=null;
			if (titulo!=null){

			sql = "SELECT * FROM libros WHERE titulo LIKE'%" + titulo + "%'";
			}
			else if (autor!=null){

				sql = "SELECT * FROM libros WHERE autor LIKE'%" + autor + "%'";
				}
			
			else{
				throw new BadRequestException("Campos vacios");
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Libro libro2 = new Libro();

				libro2.setIdlibro(rs.getInt("idlibro"));
				libro2.setTitulo(rs.getString("titulo"));
				libro2.setAutor(rs.getString("autor"));
				libro2.setLengua(rs.getString("lengua"));
				libro2.setEdicion(rs.getInt("edicion"));
				libro2.setFecha_edicion(rs.getTimestamp("fecha_edicion"));
				libro2.setFecha_impresion(rs.getTimestamp("fecha_impresion"));
				libro2.setEditorial(rs.getString("editorial"));

				libros2.add(libro2);
			}
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

		return libros2;
	}


	@GET
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibroCollection getLibros() {

		LibroCollection libros = new LibroCollection();

		// TODO: Retrieve all stings stored in the database, instantiate one
		// Sting for each one and store them in the StingCollection.
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

			sql = "SELECT * FROM libros";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Libro libro = new Libro();

				libro.setIdlibro(rs.getInt("idlibro"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getInt("edicion"));
				libro.setFecha_edicion(rs.getTimestamp("fecha_edicion"));
				libro.setFecha_impresion(rs.getTimestamp("fecha_impresion"));
				libro.setEditorial(rs.getString("editorial"));

				libros.add(libro);
			}
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

		return libros;
	}

	@POST
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro createLibro(Libro libro) {

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			java.sql.Date fecha_edicion = new java.sql.Date(
					(libro.getFecha_edicion()).getTime());

			java.sql.Date fecha_impresion = new java.sql.Date(
					(libro.getFecha_edicion()).getTime());

			stmt = conn.createStatement();
			String update = null;
			update = "INSERT INTO libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) VALUES ('"
					+ libro.getTitulo()
					+ "','"
					+ libro.getAutor()
					+ "','"
					+ libro.getLengua()
					+ "', '"
					+ libro.getEdicion()
					+ "', '"
					+ fecha_edicion
					+ "','"
					+ fecha_impresion
					+ "', '"
					+ libro.getEditorial() + "')";
			stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();

			try {
				String sql = "SELECT * FROM libros WHERE titulo='"
						+ libro.getTitulo() + "'";
				rs = stmt.executeQuery(sql);

				if (rs.next()) {

					libro.setIdlibro(rs.getInt("idlibro"));
					libro.setTitulo(rs.getString("titulo"));
					libro.setAutor(rs.getString("autor"));
					libro.setEditorial(rs.getString("editorial"));
					libro.setLengua(rs.getString("lengua"));
					libro.setEdicion(rs.getInt("edicion"));
					libro.setFecha_edicion(rs.getDate("fecha_edicion"));
					libro.setFecha_impresion(rs.getDate("fecha_impresion"));

				}

				else
					throw new LibroNotFoundException();

			} catch (SQLException e) {
				throw new InternalServerException(e.getMessage());
			}

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

		return libro;
	}

	@DELETE
	@Path("/{idlibro}")
	public void deleteLibro(@PathParam("idlibro") int idlibro) {
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
			sql = "DELETE FROM resenas WHERE idlibro='" + idlibro + "'";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM libros WHERE idlibro='" + idlibro + "'";
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

	@PUT
	@Path("/{idlibro}")
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro updateLibro(@PathParam("idlibro") int idlibro, Libro libro) {
			Connection conn = null;
			Statement stmt = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServiceUnavailableException(e.getMessage());
			}
			try {
				
				java.sql.Date fecha_edicion = new java.sql.Date(
						(libro.getFecha_edicion()).getTime());

				java.sql.Date fecha_impresion = new java.sql.Date(
						(libro.getFecha_edicion()).getTime());

				
				stmt = conn.createStatement();
				String update = null; // TODO: create update query
				update = "UPDATE libros SET libros.autor='" + libro.getAutor()
						+ "', libros.titulo= '" + libro.getTitulo()				
						+ "', libros.lengua= '" + libro.getLengua()
						+ "', libros.edicion= '" + libro.getEdicion()				
						+ "', libros.fecha_edicion= '" + fecha_edicion
						+ "', libros.fecha_impresion= '" + fecha_impresion
						+ "', libros.editorial= '" + libro.getEditorial()		
						+ "' WHERE idlibro='" + idlibro + "'";
				int rows = stmt.executeUpdate(update,
						Statement.RETURN_GENERATED_KEYS);
				if (rows != 0) {

					String sql = "SELECT * FROM libros WHERE idlibro='"
							+ idlibro + "'";
					ResultSet rs = stmt.executeQuery(sql);
					if (rs.next()) {
						libro.setIdlibro(rs.getInt("idlibro"));
						libro.setAutor(rs.getString("autor"));	
						libro.setEdicion(rs.getInt("edicion"));
						libro.setEditorial(rs.getString("editorial"));
						libro.setFecha_edicion(rs.getDate("fecha_edicion"));
						libro.setFecha_impresion(rs.getDate("fecha_impresion"));
						libro.setLengua(rs.getString("lengua"));
						libro.setTitulo(rs.getString("titulo"));
					}
				} else
					throw new LibroNotFoundException();
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
		return libro;
	}
}

