package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Domini.Casella;
import Domini.Jugador;

public class SudokuBBDD {
	public static boolean estaBuit(int i) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM SUDOKU WHERE IDS = ?";
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
	
	public static void storeSudoku(int quinSudoku,String nom) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		//String sql = "INSERT INTO FRACTEST VALUES (TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS.FF'))";
		//PreparedStatement pst = connection.prepareStatement(sql);

		 Calendar calendar = Calendar.getInstance();
		 java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(calendar.getTime().getTime());
		 System.out.println(ourJavaTimestampObject);
		 
		    // (3) create a java timestamp insert statement
		    String sqlTimestampInsertStatement = "INSERT INTO SUDOKU VALUES (?,?,?)";
		    PreparedStatement preparedStatement = connection.prepareStatement(sqlTimestampInsertStatement);
		    preparedStatement.setInt(1, quinSudoku);
		    preparedStatement.setString(2, nom);
		    preparedStatement.setTimestamp(3, ourJavaTimestampObject);

		    // (4) execute the sql timestamp insert statement, then shut everything down
		    preparedStatement.executeUpdate();
		    preparedStatement.close();
		    
		//pst.setTimestamp(1, time);
		// pst.executeUpdate();
		// pst.close();
		
		//pst.setInt(1, quinSudoku);
		
		//pst.setString(2, nom);
		//System.out.println("OK");
		//pst.setString(1, time);
		//System.out.println("OK");
		

	}


}
