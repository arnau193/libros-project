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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.arnau.libros.api.links.LibrosAPILinkBuilder;
import edu.upc.eetac.dsa.arnau.libros.api.links.Link;
import edu.upc.eetac.dsa.arnau.libros.api.model.Libro;
import edu.upc.eetac.dsa.arnau.libros.api.model.User;
import edu.upc.eetac.dsa.arnau.libros.api.model.UserCollection;

@Path("/users")
public class UserResource {
	
	@Context
	private UriInfo uriInfo;

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	String rel = null;

	@GET
	@Produces(MediaType.LIBROS_API_USER_COLLECTION)
	public UserCollection getUsers() {
		UserCollection users = new UserCollection();

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
			sql = "SELECT * FROM users";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.add(LibrosAPILinkBuilder.buildURIUserName(uriInfo,
						rs.getString("username"), rel));
				
				
				List<Link> links = new ArrayList<Link>();
				links.add(LibrosAPILinkBuilder.buildURIUsers(uriInfo, rel));

				users.setLinks(links);
				
				users.add(user);
				
				
				
				
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

		return users;
	}

	@GET
	@Path("/{username}")
	@Produces(MediaType.LIBROS_API_USER)
	public User getUser(@PathParam("username") String username) {
		User user = new User();

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
			sql = "SELECT * FROM users WHERE username= '" + username + "'";

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.add(LibrosAPILinkBuilder.buildURIUserName(uriInfo,
						rs.getString("username"), rel));
				
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

		return user;
	}

	@DELETE
	@Path("/{username}")
	public User deleteUser(@PathParam("username") String username) {

		User user = new User();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			String sql = "DELETE FROM resenas WHERE username='" + username + "'";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM users WHERE username='" + username + "'";
			stmt.executeUpdate(sql);
			user.add(LibrosAPILinkBuilder.buildURIUserName(uriInfo,
					username, rel));

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
		return user;
	}

	@PUT
	@Path("/{username}")
	@Consumes(MediaType.LIBROS_API_USER)
	@Produces(MediaType.LIBROS_API_USER)
	public User updateUser(@PathParam("username") String username, User user) {

		// TODO: get connection from database
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {

			stmt = conn.createStatement();
			String update = null; // TODO: create update query

			if (user.getName() != null && user.getEmail() != null) {
				update = "UPDATE users SET users.name='" + user.getName()
						+ "' , users.email= '" + user.getEmail()
						+ "' WHERE username='" + username + "'";
			}

			else if (user.getName() != null && user.getEmail() == null) {
				update = "UPDATE users SET users.name='" + user.getName()
						+ "' WHERE username='" + username + "'";

			}

			else if (user.getName() == null && user.getEmail() != null) {
				update = "UPDATE users SET users.email='" + user.getEmail()
						+ "' WHERE username='" + username + "'";
			}

			else

			{
				throw new BadRequestException(
						"name and email are mandatory parameters");
			}

			int rows = stmt.executeUpdate(update,
					Statement.RETURN_GENERATED_KEYS);

			if (rows != 0) {

				String sql = "SELECT * FROM users WHERE username='" + username
						+ "'";
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));

			}

			else
				throw new UserNotFoundException();

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

		return user;
	}

	@POST
	@Consumes(MediaType.LIBROS_API_USER)
	@Produces(MediaType.LIBROS_API_USER)
	public User createUser(User user) {

		// TODO: get connection from database
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {

			stmt = conn.createStatement();
			String update = null; // TODO: create update query
			update = "INSERT INTO users (username,name,email) VALUES ('"
					+ user.getUsername() + "','" + user.getName() + "','"
					+ user.getEmail() + "')";
			int rows = stmt.executeUpdate(update,
					Statement.RETURN_GENERATED_KEYS);

			if (rows != 0) {

				String sql = "SELECT * FROM users WHERE username='"
						+ user.getUsername() + "'";
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				user.add(LibrosAPILinkBuilder.buildURIUserName(uriInfo,
						rs.getString("username"), rel));

				// TODO: Retrieve the created sting from the database to get all
				// the remaining fields
			} else
				throw new UserNotFoundException();

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

		return user;
	}
}
