package Persistencia;

import java.sql.PreparedStatement;
import Domini.Jugador;

public class JugadorBBDD {

	public void storeJugador(Jugador jugador) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "INSERT INTO JUGADOR VALUES(?,?)";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setString(1, jugador.getNom());
		pst.setInt(2, jugador.getEstat());

		if (pst.executeUpdate() != 1)
			throw new Exception("JUGADOR NO GUARDAT!");
	}

	public void updateJugador(Jugador jugador) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "UPDATE jugador SET   estajuagnt = ? WHERE   nomjugador = ?";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setInt(1, jugador.getEstat());
		pst.setString(2, jugador.getNom());

		if (pst.executeUpdate() != 1)
			throw new Exception("JUGADOR NO ACTUALITZAT!");

	}
}
