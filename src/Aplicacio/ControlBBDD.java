package Aplicacio;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import Domini.Jugador;
import Domini.Sudoku;
import Domini.Taulell;
import Persistencia.JugadorBBDD;
import Persistencia.SudokuBBDD;

public class ControlBBDD {

	private Map<Integer, Date> partidesRecuperades;
	private Sudoku sudoku;
	private Jugador jugador;
	private java.sql.Timestamp time;
	private JugadorBBDD jugadorBBDD;
	private SudokuBBDD sudokuBBDD;
	private boolean inciar = false;

	public boolean getInciar() {
		return inciar;
	}

	public void setInciar(boolean inciar) {
		this.inciar = inciar;
	}

	public ControlBBDD(String nom) {

		jugador = new Jugador(nom);
		sudoku = new Sudoku(time, -1, jugador, null);

		this.jugadorBBDD = new JugadorBBDD();
		this.sudokuBBDD = new SudokuBBDD();
	}

	public void iniciarSudoku() throws Exception {
		Calendar calendar = Calendar.getInstance();
		time = new java.sql.Timestamp(calendar.getTime().getTime());
		crearSudoku();

	}

	private void crearSudoku() throws Exception {
		partidesRecuperades = sudokuBBDD.getTimestamps(sudoku);
		Set<Integer> IDSfromMAP = partidesRecuperades.keySet();
		Integer[] IdSudokusRecuperats = (Integer[]) (IDSfromMAP.toArray(new Integer[IDSfromMAP.size()]));

		boolean[] check = new boolean[999];
		for (int i = 0; i < check.length; i++) {
			check[i] = false;
		}
		for (int i = 0; i < IdSudokusRecuperats.length; i++) {
			int quin = IdSudokusRecuperats[i];
			check[quin] = true;
		}

		sudoku = new Sudoku(time, getFirstIdLiure(check), jugador, null);

	}

	private int getFirstIdLiure(boolean[] q) {
		int retorn = 0;
		for (int i = 1; i < q.length; i++) {
			if (!(q[i]))
				return i;
		}
		return retorn;
	}

	public void storeSudoku(Taulell t) throws Exception {
		sudoku.setTaulell(t);
		sudokuBBDD.storeSudoku(sudoku);
	}

	public void nouJugador(String nom) throws Exception {

		Jugador jugadorRecuperatDeDB = jugadorBBDD.getJugadorFromDB(nom);

		if (jugadorRecuperatDeDB == null) {
			jugador = new Jugador(nom, true);
			jugadorBBDD.storeJugador(jugador);
		} else if (jugadorRecuperatDeDB.getEstat() == true) {
			throw new Exception("Aquest jugador esta actualment jugant.\nPoseuvos en contacte amb l'administrador");
		} else {
			jugador.setEstat(true);
			jugadorBBDD.updateJugador(jugador);
			partidesRecuperades = sudokuBBDD.getTimestamps(sudoku);
		}
	}

	public Map<Integer, Date> getTimeStamps() throws Exception {
		return sudokuBBDD.getTimestamps(sudoku);
	}

	public void esborrarSudokuTaulell() throws Exception {
		sudokuBBDD.esborrarSudoku(sudoku);
	}

	public Map<Integer, Date> getPartidesRecuperades() {
		return this.partidesRecuperades;
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public void setEstatJuagdor() throws Exception {

		if (!(jugador.getNom().equals("Anonim"))) {
			jugador.setEstat(false);
			jugadorBBDD.updateJugador(jugador);
		}
	}

	public void recuperarTaulellGuardat() throws Exception {
		sudokuBBDD.recuperarTaulellFromSudoku(sudoku);
	}

	public Taulell getTaulellFromSudoku() {
		return sudoku.getTaulell();
	}

	public Sudoku getSudoku() {
		return sudoku;
	}
}
