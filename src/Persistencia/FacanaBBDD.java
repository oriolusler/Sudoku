package Persistencia;

import java.util.Date;
import java.util.Map;

import Domini.Jugador;
import Domini.Sudoku;

public class FacanaBBDD {

	private static FacanaBBDD facana;

	private JugadorBBDD jugadorBBDD;
	private SudokuBBDD sudokuBBDD;

	// LOGIN USER

	// Cridat a control linia 24
	public void LoginUser(String user, String password) throws Exception {
		ConnectionBBDD.initInstancia(user, password);

		// preguntar de bru!!!!! 21/06

		initAtributs();

	}

	public synchronized static FacanaBBDD getInstance() throws Exception {
		if (facana == null)
			facana = new FacanaBBDD();
		return facana;
	}

	private FacanaBBDD() throws Exception {
		super();

	}

	private void initAtributs() throws Exception {
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
