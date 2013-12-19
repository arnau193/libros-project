package edu.upc.eetac.dsa.arnau.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.arnau.libros.api.InternalServerException;
import edu.upc.eetac.dsa.arnau.libros.api.MediaType;
import edu.upc.eetac.dsa.arnau.libros.api.ServiceUnavailableException;
import edu.upc.eetac.dsa.arnau.libros.api.ResenaNotFoundException;
import edu.upc.eetac.dsa.arnau.libros.api.links.LibrosAPILinkBuilder;
import edu.upc.eetac.dsa.arnau.libros.api.links.Link;
import edu.upc.eetac.dsa.arnau.libros.api.model.Libro;
import edu.upc.eetac.dsa.arnau.libros.api.model.LibroCollection;
import edu.upc.eetac.dsa.arnau.libros.api.model.Resena;
import edu.upc.eetac.dsa.arnau.libros.api.model.ResenaCollection;
import edu.upc.eetac.dsa.arnau.libros.api.model.User;

@Path("/libros/{idlibro}/resenas")
public class ResenaResource {

	@Context
	private UriInfo uriInfo;
	
	@Context
	private SecurityContext security;

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@GET
	@Produces(MediaType.LIBROS_API_RESENA_COLLECTION)
	public ResenaCollection getReseñas(@PathParam("idlibro") String idlibro) {
		ResenaCollection resenas = new ResenaCollection();

		Connection conn = null;
		Statement stmt = null;
		String sql;
		String rel = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			sql = "SELECT resenas.*, users.name FROM users INNER JOIN resenas ON(idlibro= '"
					+ idlibro + "' and users.username=resenas.username) ";

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new ResenaNotFoundException();
			}

			else {
				rs.previous();

				while (rs.next()) {
					Resena resena = new Resena();
					resena.setIdlibro(rs.getInt("idlibro"));
					resena.setIdresena(rs.getInt("idresena"));
					resena.setName(rs.getString("name"));
					resena.setUsername(rs.getString("username"));
					resena.setTexto(rs.getString("texto"));
					resena.setFecha_creacion(rs.getDate("fecha_creacion"));
					resena.add(LibrosAPILinkBuilder.buildURIResenaId(uriInfo,
							rs.getString("idresena"), rs.getString("idlibro"), rel));
					
					List<Link> links = new ArrayList<Link>();
					links.add(LibrosAPILinkBuilder.buildURIResenas(uriInfo,rs.getString("idlibro"), rel));
					resenas.setLinks(links);
					resenas.add(resena);
					
					//MIRAR LINK
					
				}
			}
		} catch (SQLException e) {
			throw new LibroNotFoundException();
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

	@POST
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena createResena(@PathParam("idlibro") String idlibro,
			Resena resena) {
		String username = security.getUserPrincipal().getName();

		Connection conn = null;
		Statement stmt = null;
		String sql;
		ResultSet rs;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM libros WHERE idlibro='" + idlibro + "'";

			rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				throw new LibroNotFoundException();
			}

			else {

				try {
					stmt = conn.createStatement();
					sql = "SELECT * FROM resenas WHERE idlibro='" + idlibro
							+ "' and username='" + username + "' ";

					rs = stmt.executeQuery(sql);
					if (rs.next() == true) {
						throw new NotAllowedException();
					}

					else {
						try {
							stmt = conn.createStatement();
							String update = null;
							String rel = null;
							update = "INSERT INTO resenas (idlibro,username,texto) VALUES ('"
									+ idlibro
									+ "','"
									+ username
									+ "' ,'"
									+ resena.getTexto() + "')";
							stmt.executeUpdate(update,
									Statement.RETURN_GENERATED_KEYS);
							rs = stmt.getGeneratedKeys();

							if (rs.next()) {

								int idresena = rs.getInt(1);
								rs.close();

								sql = "SELECT resenas.*, users.name FROM users INNER JOIN resenas ON(idresena= '"
										+ idresena
										+ "' and users.username=resenas.username) ";
								rs = stmt.executeQuery(sql);
								if (rs.next()) {
									resena.setIdresena(rs.getInt("idresena"));
									resena.setIdlibro(rs.getInt("idlibro"));
									resena.setUsername(rs.getString("username"));
									resena.setName(rs.getString("name"));
									resena.setFecha_creacion(rs
											.getDate("fecha_creacion"));
									resena.setTexto(rs.getString("texto"));
									resena.add(LibrosAPILinkBuilder.buildURIResenaId(uriInfo,
											rs.getString("idresena"), rs.getString("idlibro"), rel));
								}
							} else
								throw new ResenaNotFoundException();
						} catch (SQLException e) {
							throw new InternalServerException(e.getMessage());
						} finally {
							try {
								stmt.close();
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}

				catch (SQLException e) {
					throw new InternalServerException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}

		return resena;
	}

	@DELETE
	@Path("/{idresena}")
	public Resena deleteResena(@PathParam("idresena") String idresena) {
		String username;
		Connection conn = null;
		Statement stmt = null;
		String sql;
		ResultSet rs;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM resenas WHERE idresena='" + idresena + "'";
			rs = stmt.executeQuery(sql);
			rs.next();
			username = rs.getString("username");
			Resena resena = new Resena();

			if (security.getUserPrincipal().getName().equals(username)) {

				try {
					
					stmt = conn.createStatement();
					sql = "DELETE FROM resenas WHERE idresena='" + idresena
							+ "'and username='" + username + "'";
					stmt.executeUpdate(sql);
					String rel = null;
					
					resena.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
							idresena, rel));
					return resena;
				} catch (SQLException e) {
					throw new InternalServerException(e.getMessage());
				} finally {
					try {
						stmt.close();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			else {
				throw new NotAllowedException();
			}

		} catch (SQLException e) {
			throw new ResenaNotFoundException();
		}

	}

	@PUT
	@Path("/{idresena}")
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena updateResena(@PathParam("idresena") String idresena,
			Resena resena) {

		String username;
		Connection conn = null;
		Statement stmt = null;
		String sql;
		ResultSet rs;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM resenas WHERE idresena='" + idresena + "'";
			rs = stmt.executeQuery(sql);
			rs.next();
			username = rs.getString("username");

			if (security.getUserPrincipal().getName().equals(username)) {
				try {
					stmt = conn.createStatement();
					String update = null;
					update = "UPDATE resenas SET resenas.texto ='"
							+ resena.getTexto() + "'WHERE idresena='"
							+ idresena + "'";
					int rows = stmt.executeUpdate(update,
							Statement.RETURN_GENERATED_KEYS);
					if (rows != 0) {

						sql = "SELECT resenas.*, users.name FROM users INNER JOIN resenas ON(idresena= '"
								+ idresena
								+ "' and users.username=resenas.username) ";
						rs = stmt.executeQuery(sql);
						if (rs.next()) {
							resena.setIdresena(rs.getInt("idresena"));
							resena.setIdlibro(rs.getInt("idlibro"));
							resena.setUsername(rs.getString("username"));
							resena.setName(rs.getString("name"));
							resena.setFecha_creacion(rs
									.getDate("fecha_creacion"));
							resena.setTexto(rs.getString("texto"));
							String rel = null;
							resena.add(LibrosAPILinkBuilder.buildURIResenaId(uriInfo,
									rs.getString("idresena"), rs.getString("idlibro"), rel));
						}
					}

					else
						throw new ResenaNotFoundException();
				} catch (SQLException e) {
					throw new InternalServerException(e.getMessage());
				} finally {
					try {
						stmt.close();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}

			else {
				throw new NotAllowedException();

			}
		} catch (SQLException e) {
			throw new ResenaNotFoundException();
		}

		return resena;

	}

}
