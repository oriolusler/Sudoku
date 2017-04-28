package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Domini.Casella;
import Domini.Taulell;

public class TaulellBBDD {

	public static void updateTaulell(int x, int y, int value,
			Casella[][] taulell) throws Exception {
		ConnectionBBDD connection = LoginBBDD.getConnection();

		String sql = "UPDATE SUDOKU SET VALOR = ? WHERE SUID = ?";
		PreparedStatement pst = connection.prepareStatement(sql);

		pst.setInt(1, taulell[x][y].getValor());
		pst.setInt(2, taulell[x][y].getIdCasella());

		if (pst.executeUpdate() != 1)
			throw new Exception("CASELLA NO GUARDADA!");

	}

	public static void storeTaullell(Casella[][] taulell) throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String sql = "INSERT INTO SUDOKU VALUES(?,?,?,?)";
				PreparedStatement pst = connection.prepareStatement(sql);

				pst.setInt(1, taulell[i][j].getIdCasella());
				pst.setInt(2, i);
				pst.setInt(3, j);
				pst.setInt(4, taulell[i][j].getValor());

				if (pst.executeUpdate() != 1)
					throw new Exception("CASELLA NO GUARDADA!");
			}
		}

	}

	//comprovar metode
	public static String[][] getTaulell() throws Exception{
		
		ConnectionBBDD connection = LoginBBDD.getConnection();
		//Taulell taulell = new Taulell(true);
		//taulell.CrearTaulell();
		String[][] taulell = new String[9][9];
		
		try {
			String sql = "SELECT * FROM SUDOKU";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int x,y,valor;
				x = rs.getInt("X");
				y = rs.getInt("Y");
				valor = rs.getInt("VALOR");
				
				if(valor==0)taulell[x][y]=null;
				else taulell[x][y]= "" + valor;
				//taulell.setCasella(x, y, valor);

			}
			return taulell;
		} catch (SQLException e) {
			throw new Exception("ERROR");
		}

		
		
		/////FUNCIONA/////
		
		/*
		ConnectionBBDD connection = LoginBBDD.getConnection();
		String llista="";
		try {
			//String sql = "SELECT * FROM JUGADOR";
			String sql = "SELECT * FROM SUDOKU";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.clearParameters();
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				//String nom;
				//nom = rs.getString("NOM");
				
				int x = rs.getInt("X");
				int y = rs.getInt("Y");
				int valor = rs.getInt("VALOR");
				llista += x + ", " + y + ", " + valor + "\n";
			}
			return llista;
		} catch (SQLException e) {
			throw new Exception("ERROR");
		}
		*/
	}
	
	public static boolean estaBuit() throws Exception {

		ConnectionBBDD connection = LoginBBDD.getConnection();

		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM SUDOKU";
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.clearParameters();
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int value;
				value = rs.getInt("COUNT");
				return value == 0;
			}

			throw new Exception("No s'ha trobat valor!");
		} catch (SQLException e) {
			throw new Exception("ERROR");
		}

	}

}
