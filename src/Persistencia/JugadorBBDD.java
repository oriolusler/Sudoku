package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Domini.Jugador;

public class JugadorBBDD {

	public void storeJugador(Jugador jugador) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "INSERT INTO JUGADOR VALUES(?,?)";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setString(1, jugador.getNom());
		pst.setBoolean(2, jugador.getEstat());

		try {
			pst.executeUpdate();
		} catch (Exception e) {
			updateJugador(jugador);
		}

	}

	public void updateJugador(Jugador jugador) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "UPDATE jugador SET   estajuagnt = ? WHERE   nomjugador = ?";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setBoolean(1, jugador.getEstat());
		pst.setString(2, jugador.getNom());

		if (pst.executeUpdate() != 1)
			throw new Exception("JUGADOR NO ACTUALITZAT!");

	}

	public int quantesPartides(Jugador jugador) throws Exception {
		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT MAX(IDSUDOKU) AS ID FROM CASELLA WHERE NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, jugador.getNom());
			ResultSet rs = preparedStatement.executeQuery();

			int maxID = 0;

			if (rs.next()) {

				maxID = rs.getInt("ID");
			}

			return maxID;
		} catch (SQLException e) {
			throw new Exception("ERROR METODEquantesPartides");
		}
	}

	public boolean estaJugantActualment(Jugador jugador) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "SELECT ESTAJUAGNT FROM JUGADOR WHERE NOMJUGADOR = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, jugador.getNom());
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			return rs.getBoolean("ESTAJUAGNT");
		}
		return false;

	}

}
