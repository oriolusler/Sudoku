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
	
	public static Timestamp[] getTotalIdSu() throws Exception {

		Timestamp[] partides = new Timestamp[999];
		ConnectionBBDD connection = LoginBBDD.getConnection();

		int i = 0;
		try {
			String sql = "SELECT DATACREACIO FROM SUDOKU";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
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

}
