package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import Domini.Casella;
import Domini.Jugador;

public class TaulellBBDD {

	public static void updateTaulell(int x, int y, int value,
			Casella[][] taulell,int su) throws Exception {
		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "UPDATE PARTIDA SET VALOR = ? WHERE ID = ? AND IDSO=?";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setInt(1, taulell[x][y].getValor());
		System.out.println( taulell[x][y].getIdC());
		pst.setInt(2, taulell[x][y].getIdC());
		pst.setInt(3, su);

		if (pst.executeUpdate() != 1)
			throw new Exception("ERROR METODE UPDATE");

	}

	public static void storeTaullell(Casella[][] taulell, Jugador jugador,
			int quinSu) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String sql = "INSERT INTO PARTIDA VALUES(?,?,?,?,?,?,?,?)";
				PreparedStatement pst = connection.prepareStatement(sql);

				pst.setInt(6, taulell[i][j].getIdCasella());
				pst.setInt(2, i);
				pst.setInt(3, j);
				pst.setInt(4, taulell[i][j].getValor());
				pst.setString(1, jugador.getNom());
				pst.setInt(5, quinSu);
				pst.setInt(8, taulell[i][j].getIdC());
				int editable;
				if (taulell[i][j].isEditable())
					editable = 0;
				else
					editable = 1;
				pst.setInt(7, editable);

				if (pst.executeUpdate() != 1)
					throw new Exception("ERRO METODE STORE");
			}
		}

	}
	
	public static String[][] getEditables(int i) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();
		String[][] taulell = new String[9][9];

		try {
			String sql = "SELECT * FROM PARTIDA WHERE IDSO = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, i);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				int x, y, valor;
				x = rs.getInt("X");
				y = rs.getInt("Y");
				valor = rs.getInt("EDITABLE");

				if (valor == 0)
					taulell[x][y] = "s";
				else
					taulell[x][y] = "n";
				// taulell.setCasella(x, y, valor);

			}
			return taulell;
		} catch (SQLException e) {
			throw new Exception("ERROR METODE GET TAULELL");
		}

	}

	
	public static String[][] getTaulell(int i) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String[][] taulell = new String[9][9];

		try {
			String sql = "SELECT * FROM PARTIDA WHERE IDSO = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, i);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				int x, y, valor;
				x = rs.getInt("X");
				y = rs.getInt("Y");
				valor = rs.getInt("VALOR");

				if (valor == 0)
					taulell[x][y] = null;
				else
					taulell[x][y] = "" + valor;
				// taulell.setCasella(x, y, valor);

			}
			return taulell;
		} catch (SQLException e) {
			throw new Exception("ERROR METODE GET TAULELL");
		}

		// ///FUNCIONA/////

		/*
		 * ConnectionBBDD connection = LoginBBDD.getConnection(); String
		 * llista=""; try { //String sql = "SELECT * FROM JUGADOR"; String sql =
		 * "SELECT * FROM SUDOKU"; PreparedStatement preparedStatement =
		 * connection.prepareStatement(sql);
		 * preparedStatement.clearParameters(); ResultSet rs =
		 * preparedStatement.executeQuery();
		 * 
		 * while (rs.next()) {
		 * 
		 * //String nom; //nom = rs.getString("NOM");
		 * 
		 * int x = rs.getInt("X"); int y = rs.getInt("Y"); int valor =
		 * rs.getInt("VALOR"); llista += x + ", " + y + ", " + valor + "\n"; }
		 * return llista; } catch (SQLException e) { throw new
		 * Exception("ERROR"); }
		 */
	}

	public static boolean estaBuit(int i) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM PARTIDA WHERE IDSO = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, i);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int value;
				value = rs.getInt("COUNT");
				return value == 0;
			}

			throw new Exception("No s'ha trobat valor!");
		} catch (SQLException e) {
			throw new Exception("ERROR METODE ESTA BUIT");
		}

	}

	public static int getUltimId() throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT MAX(IDC) AS ID FROM PARTIDA";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			ResultSet rs = preparedStatement.executeQuery();

			int maxID = 0;

			while (rs.next()) {

				maxID = rs.getInt("ID");
			}

			return maxID;
		} catch (SQLException e) {
			throw new Exception("ERROR METUDE GETULTIM ID");
		}
	}

	public static int quantesPartides() throws Exception {
		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT MAX(IDSO) AS ID FROM PARTIDA";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			ResultSet rs = preparedStatement.executeQuery();

			int maxID = 0;

			while (rs.next()) {

				maxID = rs.getInt("ID");
			}

			return maxID;
		} catch (SQLException e) {
			throw new Exception("ERROR METODE QUANTES PARTIDES");
		}
	}

	public static Timestamp[] getTotalIdSu() throws Exception {

		Timestamp[] partides = new Timestamp[999];
		ConnectionBBDD connection = LoginBBDD.getConnection();

		int i = 0;
		try {
			String sql = "SELECT TIMESTAMP  FROM SUDOKU";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			ResultSet rs = preparedStatement.executeQuery();

			try {
				while (rs.next()) {

					partides[i] = rs.getTimestamp("TIMESTAMP");
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new Exception("Accedit el numero permes");
			}
			return partides;
		} catch (SQLException e) {
			throw new Exception("ERROR METODE GET ID SU");
		}

	}

}
