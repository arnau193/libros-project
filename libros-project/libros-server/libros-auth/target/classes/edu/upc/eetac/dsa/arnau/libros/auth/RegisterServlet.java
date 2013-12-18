package edu.upc.eetac.dsa.arnau.libros.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	DataSource ds = null;

	@Override
	public void init() throws ServletException {
		super.init();
		ds = DataSourceSPA.getInstance().getDataSource();

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		


		// Declaro e inicio las variables
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
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
			String update = null;
			update = "INSERT INTO users (username,name,email,userpass) VALUES ('"
					+ username
					+ "','"
					+ name
					+ "','"
					+ email
					+ "' , MD5('"
					+ userpass + "'))";
			int rows = stmt.executeUpdate(update,
					Statement.RETURN_GENERATED_KEYS);

			update = "INSERT INTO user_roles (username,rolename) VALUES ('"
					+ username + "','registered')";
			rows = stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	
		HttpHost targetHost = new HttpHost("localhost", 8080, "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(targetHost.getHostName(),
				targetHost.getPort()), new UsernamePasswordCredentials("admin",
				"admin"));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);

		HttpPost httpPost = new HttpPost(
				"http://localhost:8080/libros-api/users");
		httpPost.addHeader("Content-Type",
				"application/vnd.libros.api.user+json");
		httpPost.addHeader("Accept", "application/vnd.libros.api.user+json");
		String user = "{\"username\": \"" + username + "\", \"name\": \""
				+ name + "\", \"email\": \"" + email + "\" }";

		httpPost.setEntity(new StringEntity(user));
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

		CloseableHttpResponse httpResponse = closeableHttpClient.execute(
				targetHost, httpPost, context);
		HttpEntity entity = httpResponse.getEntity();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				entity.getContent()));
		String line = null;
		while ((line = reader.readLine()) != null)
			System.out.println(line);
		
		httpResponse.close();
	}
	

}



