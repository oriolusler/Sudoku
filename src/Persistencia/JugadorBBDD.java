package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Domini.Jugador;

class JugadorBBDD {

	private ConnectionBBDD conn;

	JugadorBBDD() throws Exception {
		conn = ConnectionBBDD.getInstacia();
	}

	void storeJugador(Jugador jugador) throws Exception {

		PreparedStatement pst = null;
		try {
			String sql = "INSERT INTO JUGADOR VALUES(?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, jugador.getNom());
			pst.setBoolean(2, jugador.getEstat());
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("ERROR de SQL/JUGADORBBDD/storeJugador\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("ERROR de CLASS/JUGADORBBDD/storeJugador\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	void updateJugador(Jugador jugador) throws Exception {

		PreparedStatement pst = null;
		try {
			String sql = "UPDATE jugador SET   estajuagnt = ? WHERE   nomjugador = ?";
			pst = conn.prepareStatement(sql);
			pst.setBoolean(1, jugador.getEstat());
			pst.setString(2, jugador.getNom());
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("ERROR de SQL/JUGADORBBDD/updateJugador\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("ERROR de CLASS/JUGADORBBDD/updateJugador\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	Jugador getJugadorFromDB(String nom) throws Exception {

		Jugador jugadorRecuperat;
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM JUGADOR WHERE NOMJUGADOR = ?";
			pst = conn.prepareStatement(sql);
			pst.clearParameters();
			pst.setString(1, nom);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				jugadorRecuperat = new Jugador(nom, rs.getBoolean("ESTAJUAGNT"));
				return jugadorRecuperat;
			}
		} catch (SQLException e) {
			throw new Exception("ERROR de SQL/JUGADORBBDD/getJugadorFromDB\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("ERROR de CLASS/JUGADORBBDD/getJugadorFromDB\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
		}
		return null;
	}

}
