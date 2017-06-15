package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OracleDriver;

public class ConnectionBBDD {

	private static ConnectionBBDD instacia;

	private Connection connection;
	private String user;
	private String password;

	private ConnectionBBDD(String user, String password) throws Exception {

		this.password = password;
		this.user = user;

		try {
			DriverManager.registerDriver(new OracleDriver());
			ferConexio();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static synchronized ConnectionBBDD initInstancia(String user, String password) throws Exception {
		if (instacia == null)
			instacia = new ConnectionBBDD(user, password);

		return instacia;
	}

	public static synchronized ConnectionBBDD getInstacia() throws Exception {
		if (instacia == null)
			throw new Exception("ConnectionBBDD/getInstacia\nInstancia no inicialitzada!");

		return instacia;
	}

	public Statement createStatement() throws Exception {

		if (connection.isClosed())
			ferConexio();
		return connection.createStatement();
	}

	public PreparedStatement prepareStatement(String sql) throws Exception {

		if (connection.isClosed())
			ferConexio();
		return connection.prepareStatement(sql);
	}

	public void close() throws SQLException {
		connection.close();
	}
	

	public void ferConexio() throws Exception {
		connection = DriverManager.getConnection("jdbc:oracle:thin:@Kali.eupmt.tecnocampus.cat:1521:sapiens", user,
				password);
	}

	public boolean isClosed() throws SQLException {
		
		return connection.isClosed();
	}

}
