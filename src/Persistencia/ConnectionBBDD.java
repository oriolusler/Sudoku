package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OracleDriver;

public class ConnectionBBDD extends ConnectionBBDDAbstracte {

	private Connection connection;

	ConnectionBBDD(String USER, String PASSWORD) throws Exception {
		try {
			DriverManager.registerDriver(new OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@Kali.eupmt.tecnocampus.cat:1521:sapiens", USER,
					PASSWORD);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public Statement createStatement() throws SQLException {

		return connection.createStatement();
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {

		return connection.prepareStatement(sql);
	}

}
