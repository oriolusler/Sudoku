package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import Domini.Sudoku;

public class SudokuBBDD {

	private TaulellBBDD taulell;

	public SudokuBBDD() {
		this.taulell = new TaulellBBDD();
	}

	public boolean estaBuit(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM SUDOKU WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
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
			throw new Exception("ERROR METODE ESTA BUIT");
		}

	}

	public void storeSudoku(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sqlTimestampInsertStatement = "INSERT INTO SUDOKU VALUES (?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sqlTimestampInsertStatement);

		preparedStatement.setString(1, sudoku.getJugador().getNom());
		preparedStatement.setInt(2, sudoku.getQuinSudoku());
		preparedStatement.setTimestamp(3, sudoku.getTime());

		preparedStatement.executeUpdate();
		preparedStatement.close();
		taulell.storeTaullell(sudoku);
	}

	public void esborrarSudoku(Sudoku sudoku) throws Exception {

		try {
			ConnectionBBDD connection = LoginBBDD.getConnection();

			String sqlTimestampInsertStatement = "DELETE FROM SUDOKU WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlTimestampInsertStatement);
			preparedStatement.setInt(1, sudoku.getQuinSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());

			taulell.esborrarTaulell(sudoku);

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new Exception("ERROR METODE esborrarSudoku");
		}
	}

	public Map<Integer, Timestamp> getTimestamps(Sudoku sudoku) throws Exception {

		Map<Integer, Timestamp> recuperats = new HashMap<Integer, Timestamp>();
		// Timestamp[] partides = new Timestamp[quantsSudokus(sudoku)];
		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT IDSUDOKU,DATACREACIO FROM SUDOKU WHERE NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, sudoku.getJugador().getNom());
			ResultSet rs = preparedStatement.executeQuery();

			try {
				while (rs.next()) {

					recuperats.put(rs.getInt("IDSUDOKU"), rs.getTimestamp("DATACREACIO"));

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new Exception("Accedit el numero permes");
			}
			return recuperats;
		} catch (SQLException e) {
			throw new Exception("ERROR METODE GET ID SU");
		}

	}

	public int quantsSudokus(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) FROM SUDOKU WHERE NOMJUGADOR= ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, sudoku.getJugador().getNom());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int value;
				value = rs.getInt("COUNT(*)");
				return value;
			}

			throw new Exception("No s'ha trobat valor!");
		} catch (SQLException e) {
			throw new Exception("ERROR METODE quantsSudokus");
		}

	}

}
