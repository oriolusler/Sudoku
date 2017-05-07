package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class SudokuBBDD {
	public static boolean estaBuit(int i) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM SUDOKU WHERE IDSUDOKU = ?";
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

	public static void storeSudoku(int quinSudoku, String nom) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(
				calendar.getTime().getTime());

		String sqlTimestampInsertStatement = "INSERT INTO SUDOKU VALUES (?,?,?)";
		PreparedStatement preparedStatement = connection
				.prepareStatement(sqlTimestampInsertStatement);
		preparedStatement.setInt(2, quinSudoku);
		preparedStatement.setString(1, nom);
		preparedStatement.setTimestamp(3, ourJavaTimestampObject);

		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
	public static Timestamp[] getTimestamps(String nom) throws Exception {

		Timestamp[] partides = new Timestamp[quantsSudokus(nom)];
		ConnectionBBDD connection = LoginBBDD.getConnection();

		int i = 0;
		try {
			String sql = "SELECT DATACREACIO FROM SUDOKU WHERE NOMJUGADOR = ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, nom);
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
	
	
	public static int getIdFromTimeStamp(Timestamp time) throws Exception {

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

	public static int quantsSudokus(String nom) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) FROM SUDOKU WHERE NOMJUGADOR= ?";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, nom);

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
