package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Domini.Jugador;

class JugadorBBDD {

	public void storeJugador(Jugador jugador) throws Exception {

		ConnectionBBDDAbstracte connection = LoginBBDD.getConnection();

		String sql = "INSERT INTO JUGADOR VALUES(?,?)";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setString(1, jugador.getNom());
		pst.setBoolean(2, jugador.getEstat());

		try {
			pst.executeUpdate();
		} catch (Exception e) {
			throw new Exception("El jugador: " + jugador.getNom()
					+ ", ja existeix a la base de dades.");
		}

	}

	public void updateJugador(Jugador jugador) throws Exception {

		ConnectionBBDDAbstracte connection = LoginBBDD.getConnection();

		String sql = "UPDATE jugador SET   estajuagnt = ? WHERE   nomjugador = ?";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setBoolean(1, jugador.getEstat());
		pst.setString(2, jugador.getNom());

		if (pst.executeUpdate() != 1)
			throw new Exception("JUGADOR NO ACTUALITZAT!");

	}

	public Jugador getJugadorFromDB(String nom) throws Exception {

		Jugador jugadorRecuperat;
		ConnectionBBDDAbstracte connection = LoginBBDD.getConnection();

		String sql = "SELECT * FROM JUGADOR WHERE NOMJUGADOR = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, nom);
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			jugadorRecuperat = new Jugador(nom, rs.getBoolean("ESTAJUAGNT"));
			return jugadorRecuperat;

		}

		return null;
	}

}
