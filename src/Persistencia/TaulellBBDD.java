package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Domini.Casella;

public class TaulellBBDD {

	public static void updateTaulell(int x, int y, int value,
			Casella[][] taulell, int su) throws Exception {
		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "UPDATE CASELLA SET VALOR = ? WHERE IDSUDOKU = ? AND COORX =? AND COORY=?";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setInt(1, taulell[x][y].getValor());
		pst.setInt(2, su);
		pst.setInt(3, x);
		pst.setInt(4, y);

		if (pst.executeUpdate() != 1)
			throw new Exception("ERROR METODE UPDATE");

	}

	public static void storeTaullell(Casella[][] taulell, int quinSu)
			throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String sql = "INSERT INTO CASELLA VALUES(?,?,?,?,?)";
				PreparedStatement pst = connection.prepareStatement(sql);

				pst.setInt(1, quinSu);
				pst.setInt(2, i);
				pst.setInt(3, j);
				pst.setInt(4, taulell[i][j].getValor());

				int editable;
				if (taulell[i][j].isEditable())
					editable = 0;
				else
					editable = 1;
				pst.setInt(5, editable);

				if (pst.executeUpdate() != 1)
					throw new Exception("ERRO METODE STORE");
			}
		}

	}

	public static String[][] getEditables(int i) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();
		String[][] taulell = new String[9][9];

		try {
			String sql = "SELECT * FROM CASELLA WHERE IDSUDOKU = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, i);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				int x, y, valor;
				x = rs.getInt("COORX");
				y = rs.getInt("COORY");
				valor = rs.getInt("EDITABLE");

				if (valor == 0)
					taulell[x][y] = "s";
				else
					taulell[x][y] = "n";
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
			String sql = "SELECT * FROM CASELLA WHERE IDSUDOKU = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, i);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				int x, y, valor;
				x = rs.getInt("COORX");
				y = rs.getInt("COORY");
				valor = rs.getInt("VALOR");

				if (valor == 0)
					taulell[x][y] = null;
				else
					taulell[x][y] = "" + valor;
			}
			return taulell;
		} catch (SQLException e) {
			throw new Exception("ERROR METODE GET TAULELL");
		}

	}

	public static boolean estaBuit(int i) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM CASELLA WHERE IDSUDOKU = ?";
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

	public static int quantesPartides() throws Exception {
		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT MAX(IDSUDOKU) AS ID FROM CASELLA";
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

	public static void actualitzarBBDD(Casella[][] taulell, int quinSu)
			throws Exception {

		String[][] antic = getTaulell(quinSu);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (antic[i][j] == null)
					antic[i][j] = "0";

			}

		}

		ConnectionBBDD connection = LoginBBDD.getConnection();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				if (!(antic[i][j].equals(String.valueOf(taulell[i][j].getValor())))) {
					String sql = "UPDATE CASELLA SET valor=?,editable=? WHERE COORX=? AND COORY=? AND IDSUDOKU=?";
					PreparedStatement pst = connection.prepareStatement(sql);

					pst.setInt(1, taulell[i][j].getValor());
					pst.setInt(5, quinSu);
					pst.setInt(3, i);
					pst.setInt(4, j);

					int editable;
					if (taulell[i][j].isEditable())
						editable = 0;
					else
						editable = 1;
					pst.setInt(2, editable);

					if (pst.executeUpdate() != 1)
						throw new Exception("ERRO METODE STORE");

					System.out.print(1+",");
				}
			}
		}
	}

}
