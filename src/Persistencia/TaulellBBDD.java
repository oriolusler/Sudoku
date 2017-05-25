package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Domini.Casella;
import Domini.Sudoku;

public class TaulellBBDD {

	public void storeTaullell(Sudoku sudoku) throws Exception {

		Casella[][] taulell = sudoku.getTaulell().getCasella();
		ConnectionBBDD connection = LoginBBDD.getConnection();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String sql = "INSERT INTO CASELLA VALUES(?,?,?,?,?,?)";
				PreparedStatement pst = connection.prepareStatement(sql);

				pst.setString(1, sudoku.getJugador().getNom());
				pst.setInt(2, sudoku.getQuinSudoku());
				pst.setInt(3, i);
				pst.setInt(4, j);
				pst.setInt(5, taulell[i][j].getValor());

				int editable;
				if (taulell[i][j].isEditable())
					editable = 0;
				else
					editable = 1;
				pst.setInt(6, editable);

				if (pst.executeUpdate() != 1)
					throw new Exception("ERRO METODE storeTaullell");
			}
		}

	}

	public String[][] getEditables(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();
		String[][] taulell = new String[9][9];

		try {
			String sql = "SELECT * FROM CASELLA WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, sudoku.getQuinSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());
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
			throw new Exception("ERROR METODE getEditables");
		}

	}

	public String[][] getTaulell(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String[][] taulell = new String[9][9];

		try {
			String sql = "SELECT * FROM CASELLA WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, sudoku.getQuinSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());
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
			throw new Exception("ERROR METODE getTaulell");
		}

	}

	public boolean estaBuit(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM CASELLA WHERE IDSUDOKU = ? AND NOMJUGADOR= ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, sudoku.getQuinSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {

				int value;
				value = rs.getInt("COUNT");
				return value == 0;
			}

			throw new Exception("No s'ha trobat valor!");
		} catch (SQLException e) {
			throw new Exception("ERROR METODE estaBuit");
		}

	}

	public void actualitzarBBDD(Sudoku sudoku) throws Exception {

		Casella[][] taulell = sudoku.getTaulell().getCasella();

		String[][] antic = getTaulell(sudoku);
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
					String sql = "UPDATE CASELLA SET valor=?,editable=? WHERE COORX=? AND COORY=? AND IDSUDOKU=? AND NOMJUGADOR= ? ";
					PreparedStatement pst = connection.prepareStatement(sql);

					pst.setInt(1, taulell[i][j].getValor());

					int editable;
					if (taulell[i][j].isEditable())
						editable = 0;
					else
						editable = 1;
					pst.setInt(2, editable);
					pst.setInt(3, i);
					pst.setInt(4, j);
					pst.setInt(5, sudoku.getQuinSudoku());
					pst.setString(6, sudoku.getJugador().getNom());

					if (pst.executeUpdate() != 1)
						throw new Exception("ERRO METODE actualitzarBBDD");
				}
			}
		}
	}

	public void esborrarTaulell(Sudoku sudoku) throws Exception {

		try {
			ConnectionBBDD connection = LoginBBDD.getConnection();

			String sqlTimestampInsertStatement = "DELETE FROM CASELLA WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlTimestampInsertStatement);
			preparedStatement.setInt(1, sudoku.getQuinSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new Exception("ERROR METODE esborrarTaulell");
		}
	}
}
