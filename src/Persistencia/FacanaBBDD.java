package Persistencia;

import java.util.Date;
import java.util.Map;

import Domini.Jugador;
import Domini.Sudoku;

public class FacanaBBDD {

	private static FacanaBBDD instancia;

	private JugadorBBDD jugadorBBDD;
	private SudokuBBDD sudokuBBDD;

	// LOGIN USER
	public static void LoginUser(String user, String password) throws Exception {
		if (instancia == null) {
			instancia = new FacanaBBDD(user, password);
		}

	}

	public static FacanaBBDD getInstance() throws Exception {
		if (instancia == null) {
			throw new Exception("Error de la fa�ana.");
		}
		return instancia;
	}

	private FacanaBBDD(String user, String password) throws Exception {

		LoginBBDD.login(user, password);
		this.jugadorBBDD = new JugadorBBDD();
		this.sudokuBBDD = new SudokuBBDD();

	}

	// PARTIDA
	public Map<Integer, Date> getTimestamps(Sudoku sudoku) throws Exception {
		return sudokuBBDD.getTimestamps(sudoku);
	}

	public void storeSudoku(Sudoku sudoku) throws Exception {
		sudokuBBDD.storeSudoku(sudoku);

	}

	public void recuperarTaulellFromSudoku(Sudoku sudoku) throws Exception {
		sudokuBBDD.recuperarTaulellFromSudoku(sudoku);

	}

	public void esborrarSudoku(Sudoku sudoku) throws Exception {
		sudokuBBDD.esborrarSudoku(sudoku);

	}

	// JUGADOR
	public Jugador getJugadorFromDB(String nom) throws Exception {
		return jugadorBBDD.getJugadorFromDB(nom);
	}

	public void storeJugador(Jugador jugador) throws Exception {
		jugadorBBDD.storeJugador(jugador);

	}

	public void updateJugador(Jugador jugador) throws Exception {
		jugadorBBDD.updateJugador(jugador);

	}

}
