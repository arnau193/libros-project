package edu.upc.eetac.dsa.arnau.libros.auth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	DataSource ds = null;

	@Override
	public void init() throws ServletException {
		super.init();
		ds = DataSourceSPA.getInstance().getDataSource();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Declaro e inicio las variables
		String username = request.getParameter("username");
		String userpass = request.getParameter("userpass");

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {

			stmt = conn.createStatement();
			String sql = null;
			sql = "SELECT * FROM users WHERE username='" + username
					+ "' AND userpass=MD5('" + userpass + "')";
			ResultSet rows = stmt.executeQuery(sql);
			if (rows.next()) {
				response.setContentType("text/html");
				response.sendRedirect("http://localhost/libros/libros.html");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
