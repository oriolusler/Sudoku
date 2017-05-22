package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import Domini.Sudoku;

public class SudokuBBDD {

	public boolean estaBuit(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM SUDOKU WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, sudoku.getQuinSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());
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

	public void storeSudoku(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sqlTimestampInsertStatement = "INSERT INTO SUDOKU VALUES (?,?,?)";
		PreparedStatement preparedStatement = connection
				.prepareStatement(sqlTimestampInsertStatement);
		
		preparedStatement.setString(1, sudoku.getJugador().getNom());
		preparedStatement.setInt(2, sudoku.getQuinSudoku());
		preparedStatement.setTimestamp(3, sudoku.getTime());

		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	public void esborrarSudoku(Sudoku sudoku) throws Exception {

		try {
			ConnectionBBDD connection = LoginBBDD.getConnection();

			String sqlTimestampInsertStatement = "DELETE FROM SUDOKU WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sqlTimestampInsertStatement);
			preparedStatement.setInt(1, sudoku.getQuinSudoku());
			preparedStatement.setString(2, sudoku.getJugador().getNom());

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new Exception("ERROR METODE esborrarSudoku");
		}
	}

	public Timestamp[] getTimestamps(Sudoku sudoku) throws Exception {

		Timestamp[] partides = new Timestamp[quantsSudokus(sudoku)];
		ConnectionBBDD connection = LoginBBDD.getConnection();

		int i = 0;
		try {
			String sql = "SELECT DATACREACIO FROM SUDOKU WHERE NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, sudoku.getJugador().getNom());
			ResultSet rs = preparedStatement.executeQuery();

			try {
				while (rs.next()) {

					partides[i] = rs.getTimestamp("DATACREACIO");
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

	public int getIdFromTimeStamp(Timestamp time) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT IDSUDOKU FROM SUDOKU WHERE DATACREACIO = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setTimestamp(1, time);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int value;
				value = rs.getInt("IDSUDOKU");
				return value;
			}

			throw new Exception("No s'ha trobat valor!");
		} catch (SQLException e) {
			throw new Exception("ERROR METODE getIdFromTimeStamp");
		}

	}

	public int quantsSudokus(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) FROM SUDOKU WHERE NOMJUGADOR= ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
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
