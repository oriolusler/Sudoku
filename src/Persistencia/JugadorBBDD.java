package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Domini.Jugador;

public class JugadorBBDD {

	public static String getJugadors() throws Exception {
		ConnectionBBDD connection = LoginBBDD.getConnection();
		String llista = "";
		try {
			String sql = "SELECT * FROM JUGADOR";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String nom;
				nom = rs.getString("NOM");

				llista += nom + "\n";
			}
			return llista;
		} catch (SQLException e) {
			throw new Exception("ERROR GETTING JUGADORS");
		}

	}

	public static void storeJugador(Jugador jugador) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "INSERT INTO JUGADOR VALUES(?,?)";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setString(1, jugador.getNom());
		pst.setInt(2, jugador.getEstat());

		if (pst.executeUpdate() != 1)
			throw new Exception("JUGADOR NO GUARDAT!");
	}
	
	public static void updateJugador(Jugador jugador) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "UPDATE jugador SET   estajugant = ? WHERE   nomjugador = ?";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setInt(1, jugador.getEstat());
		pst.setString(2, jugador.getNom());
		
		if (pst.executeUpdate() != 1)
			throw new Exception("JUGADOR NO ACTUALITZAT!");
	}
}
