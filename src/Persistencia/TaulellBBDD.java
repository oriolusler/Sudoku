package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Domini.Casella;
import Domini.Sudoku;
import Domini.Taulell;

public class TaulellBBDD {

	public void storeTaullell(Sudoku sudoku) throws Exception {

		try {
			Casella[][] taulell = sudoku.getTaulell().getCasella();
			ConnectionBBDD connection = LoginBBDD.getConnection();
			esborrarTaulell(sudoku);
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {

					try {

						if (taulell[i][j].getValor() != 0) {
							String sql = "INSERT INTO CASELLA VALUES(?,?,?,?,?,?)";
							PreparedStatement pst = connection.prepareStatement(sql);
							pst.setString(1, sudoku.getJugador().getNom());
							pst.setInt(2, sudoku.getIdSudoku());
							pst.setInt(3, i);
							pst.setInt(4, j);
							pst.setInt(5, taulell[i][j].getValor());
							pst.setBoolean(6, taulell[i][j].isEditable());
							pst.executeUpdate();
						}

					} catch (Exception e) {
						throw new Exception("ERROR METODE storeTaullell introduint els valors de les caselles.");
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("ERROR METODE storeTaullell");
		}

	}

	public void recuperarTaulell(Sudoku sudoku) throws Exception {

		try {
			ConnectionBBDD connection = LoginBBDD.getConnection();

			Taulell nouTauell = new Taulell();
			Casella[][] noves = nouTauell.getCasella();

			String sql = "SELECT * FROM CASELLA WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, sudoku.getIdSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				if (rs.getInt("VALOR") != 0) {
					int fila, columna, valor;
					boolean editable;
					fila = rs.getInt("COORX");
					columna = rs.getInt("COORY");
					valor = rs.getInt("VALOR");
					editable = rs.getBoolean("EDITABLE");

					noves[fila][columna].setValor(valor);
					noves[fila][columna].setEditable(editable);
				}
			}

			sudoku.setTaulell(nouTauell);

		} catch (Exception e) {
			throw new Exception("ERROR METODE recuperarTaulell");
		}

	}

	public void esborrarTaulell(Sudoku sudoku) throws Exception {

		try {
			ConnectionBBDD connection = LoginBBDD.getConnection();

			String sqlTimestampInsertStatement = "DELETE FROM CASELLA WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlTimestampInsertStatement);
			preparedStatement.setInt(1, sudoku.getIdSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());

			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			throw new Exception("ERROR METODE esborrarTaulell");
		}
	}

}
