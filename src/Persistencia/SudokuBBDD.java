package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Domini.Sudoku;

public class SudokuBBDD {

	private TaulellBBDD taulell;

	public SudokuBBDD() {
		this.taulell = new TaulellBBDD();
	}

	public void recuperarTaulellFromSudoku(Sudoku sudoku) throws Exception {
		taulell.recuperarTaulell(sudoku);
	}

	public boolean estaBuit(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM SUDOKU WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, sudoku.getIdSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {

				int value;
				value = rs.getInt("COUNT");
				return value == 0;
			}

			throw new Exception("No s'ha trobat valor!");
		} catch (SQLException e) {
			throw new Exception("ERROR METODE ESTA BUIT");
		}

	}

	public void storeSudoku(Sudoku sudoku) throws Exception {

		try {
			Timestamp nouAfegir = new Timestamp(sudoku.getTime().getTime());
			ConnectionBBDD connection = LoginBBDD.getConnection();

			String sqlTimestampInsertStatement = "INSERT INTO SUDOKU VALUES (?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlTimestampInsertStatement);

			preparedStatement.setString(1, sudoku.getJugador().getNom());
			preparedStatement.setInt(2, sudoku.getIdSudoku());
			preparedStatement.setTimestamp(3, nouAfegir);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			taulell.storeTaullell(sudoku);
		} catch (Exception e) {
			// Si no es pot guardar anteriorment es perquqe ja existeix el
			// Sudoku(PARTIDA) i quan arriba en el catch el guarda
			taulell.storeTaullell(sudoku);
		}

	}

	public void esborrarSudoku(Sudoku sudoku) throws Exception {

		taulell.esborrarTaulell(sudoku);

		try {
			ConnectionBBDD connection = LoginBBDD.getConnection();

			String sqlTimestampInsertStatement = "DELETE FROM SUDOKU WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlTimestampInsertStatement);
			preparedStatement.setInt(1, sudoku.getIdSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());

			taulell.esborrarTaulell(sudoku);

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new Exception("ERROR METODE esborrarSudoku");
		}
	}

	public Map<Integer, Date> getTimestamps(Sudoku sudoku) throws Exception {

		Map<Integer, Date> recuperats = new HashMap<Integer, Date>();
		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT IDSUDOKU,DATACREACIO FROM SUDOKU WHERE NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, sudoku.getJugador().getNom());
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Date date = new Date(rs.getTimestamp("DATACREACIO").getTime());
				recuperats.put(rs.getInt("IDSUDOKU"), date);

			}

			return recuperats;
		} catch (SQLException e) {
			throw new Exception("ERROR METODE getTimestamps");
		}

	}

}
