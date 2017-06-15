package Persistencia;

import Domini.Sudoku;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class SudokuBBDD {

	private TaulellBBDD taulell;
	private ConnectionBBDD conn;

	SudokuBBDD() throws Exception {
		this.taulell = new TaulellBBDD();
		conn = ConnectionBBDD.getInstacia();
	}

	void recuperarTaulellFromSudoku(Sudoku sudoku) throws Exception {
		taulell.recuperarTaulell(sudoku);
	}

	boolean estaBuit(Sudoku sudoku) throws Exception {

		PreparedStatement pst = null;
		try {
			String sql = "SELECT COUNT(*) AS COUNT FROM SUDOKU WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			pst = conn.prepareStatement(sql);
			pst.clearParameters();
			pst.setInt(1, sudoku.getIdSudoku());
			pst.setString(2, sudoku.getJugador().getNom());
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				int value;
				value = rs.getInt("COUNT");
				return value == 0;
			}

		} catch (SQLException e) {
			throw new Exception("ERROR de SQL/SUDOKUBBDD/estaBuit\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("ERROR de CLASS/SUDOKUBBDD/estaBuit\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
			conn.close();
		}

		return false;
	}

	void storeSudoku(Sudoku sudoku) throws Exception {

		Timestamp nouAfegir = new Timestamp(sudoku.getTime().getTime());

		PreparedStatement pst = null;
		try {

			if (estaBuit(sudoku)) {
				String sql = "INSERT INTO SUDOKU VALUES (?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, sudoku.getJugador().getNom());
				pst.setInt(2, sudoku.getIdSudoku());
				pst.setTimestamp(3, nouAfegir);
				pst.executeUpdate();

				taulell.storeTaullell(sudoku);
			} else {
				taulell.storeTaullell(sudoku);
			}
		} catch (SQLException e) {
			throw new Exception("ERROR de SQL/SUDOKUBBDD/storeSudoku\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("ERROR de CLASS/SUDOKUBBDD/storeSudoku\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
			conn.close();
		}

	}

	void esborrarSudoku(Sudoku sudoku) throws Exception {

		taulell.esborrarTaulell(sudoku);
		PreparedStatement pst = null;
		try {
			String sql = "DELETE FROM SUDOKU WHERE IDSUDOKU = ? AND NOMJUGADOR = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, sudoku.getIdSudoku());
			pst.setString(2, sudoku.getJugador().getNom());
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("SQL/SUDOKUBBDD/esborrarSudoku\n" + e.getMessage());
		} catch (Exception e) {
			throw new Exception("CLASS/SUDOKUBBDD/esborrarSudoku\n" + e.getMessage());
		} finally {
			if (pst != null)
				pst.close();
			conn.close();
		}

	}

	Map<Integer, Date> getTimestamps(Sudoku sudoku) throws Exception {
		Map<Integer, Date> recuperats = new HashMap<Integer, Date>();
		if (!(sudoku.getJugador().getNom().equals("Anonim"))) {

			PreparedStatement pst = null;

			try {
				String sql = "SELECT IDSUDOKU,DATACREACIO FROM SUDOKU WHERE NOMJUGADOR = ?";
				pst = conn.prepareStatement(sql);
				pst.clearParameters();
				pst.setString(1, sudoku.getJugador().getNom());
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					Date date = new Date(rs.getTimestamp("DATACREACIO").getTime());
					recuperats.put(rs.getInt("IDSUDOKU"), date);

				}

			} catch (SQLException e) {
				throw new Exception("ERROR de SQL/SUDOKUBBDD/getTimestamps\n" + e.getMessage());
			} catch (Exception e) {
				throw new Exception("ERROR de CLASS/SUDOKUBBDD/getTimestamps\n" + e.getMessage());
			} finally {
				if (pst != null)
					pst.close();
				conn.close();
			}
		}

		return recuperats;
	}

}
