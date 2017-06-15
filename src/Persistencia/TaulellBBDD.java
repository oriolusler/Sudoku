package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Domini.Casella;
import Domini.Sudoku;
import Domini.Taulell;

class TaulellBBDD {

	private ConnectionBBDD conn;

	TaulellBBDD() throws Exception {
		conn = ConnectionBBDD.getInstacia();
	}

	void storeTaullell(Sudoku sudoku) throws Exception {

		Casella[][] taulell = sudoku.getTaulell().getCasella();
		PreparedStatement pst = null;
		esborrarTaulell(sudoku);
		try {

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {

					try {

						if (taulell[i][j].getValor() != 0) {
							String sql = "INSERT INTO CASELLA VALUES(?,?,?,?,?,?)";
							pst = conn.prepareStatement(sql);
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
		} catch (SQLException e) {
			throw new Exception("ERROR de SQL/TAULELLBBDD/storeTaullell\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("ERROR de CLASS/TAULELLBBDD/storeTaullell\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
			conn.close();
		}

	}

	void recuperarTaulell(Sudoku sudoku) throws Exception {

		PreparedStatement pst = null;
		try {
			Taulell nouTauell = new Taulell();
			Casella[][] noves = nouTauell.getCasella();

			String sql = "SELECT * FROM CASELLA WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			pst = conn.prepareStatement(sql);
			pst.clearParameters();
			pst.setInt(1, sudoku.getIdSudoku());
			pst.setString(2, sudoku.getJugador().getNom());
			ResultSet rs = pst.executeQuery();

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

		} catch (SQLException e) {
			throw new Exception("ERROR de SQL/TAULELLBBDD/recuperarTaulell\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("ERROR de CLASS/TAULELLBBDD/recuperarTaulell\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
			conn.close();
		}

	}

	void esborrarTaulell(Sudoku sudoku) throws Exception {

		PreparedStatement pst = null;
		try {

			String sql = "DELETE FROM CASELLA WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, sudoku.getIdSudoku());
			pst.setString(2, sudoku.getJugador().getNom());
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("ERROR de SQL/TAULELLBBDD/esborrarTaulell\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("ERROR de CLASS/TAULELLBBDD/esborrarTaulell\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
			conn.close();
		}
	}

}
