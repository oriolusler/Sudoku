package Aplicacio;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import Domini.Jugador;
import Domini.Sudoku;
import Domini.Taulell;
import Persistencia.JugadorBBDD;
import Persistencia.SudokuBBDD;
import Persistencia.TaulellBBDD;

public class ControlBBDD {

	private Map<Integer, Timestamp> recup;
	private Taulell ta;
	private Sudoku su;
	private Jugador jugador;
	private java.sql.Timestamp time;
	private JugadorBBDD jugadorBBDD;
	private SudokuBBDD sudokuBBDD;
	private TaulellBBDD taulellBBDD;

	public ControlBBDD(String nom) {

		jugador = new Jugador(nom);
		su = new Sudoku(time, -1, jugador, ta);

		this.jugadorBBDD = new JugadorBBDD();
		this.sudokuBBDD = new SudokuBBDD();
		this.taulellBBDD = new TaulellBBDD();
	}

	public void iniciarSudoku() throws Exception {
		Calendar calendar = Calendar.getInstance();
		time = new java.sql.Timestamp(calendar.getTime().getTime());
		crearSudoku();

	}

	private void crearSudoku() throws Exception {
		recup = sudokuBBDD.getTimestamps(su);
		Set<Integer> IDSfromMAP = recup.keySet();
		Integer[] IdSudokusRecuperats = (Integer[]) (IDSfromMAP
				.toArray(new Integer[IDSfromMAP.size()]));

		boolean[] check = new boolean[999];
		for (int i = 0; i < check.length; i++) {
			check[i] = false;
		}
		for (int i = 0; i < IdSudokusRecuperats.length; i++) {
			int quin = IdSudokusRecuperats[i];
			check[quin] = true;
		}

		su = new Sudoku(time, getFirstIdLiure(check), jugador, ta);

	}

	private int getFirstIdLiure(boolean[] q) {
		int retorn = 0;
		for (int i = 1; i < q.length; i++) {
			if (!(q[i]))
				return i;
		}
		return retorn;
	}

	public int getSudokuID() {
		return this.su.getQuinSudoku();
	}

	public void setSudokuID(int quinSudoku) {
		this.su.setQuinSudoku(quinSudoku);
	}

	public boolean sudokuBuit() throws Exception {
		return sudokuBBDD.estaBuit(su);
	}

	public void storeSudoku() throws Exception {
		sudokuBBDD.storeSudoku(su);
	}

	public boolean taulellBuit() throws Exception {
		return taulellBBDD.estaBuit(su);
	}

	public void nouJugador(String nom) throws Exception {

		Jugador jugadorRecuperatDeDB = jugadorBBDD.getJugadorFromDB(nom);

		if (jugadorRecuperatDeDB == null) {
			jugador = new Jugador(nom, true);
			jugadorBBDD.storeJugador(jugador);
		} else if (jugadorRecuperatDeDB.getEstat() == true)
			throw new Exception(
					"Aquest jugador esta actualment jugant.\nPoseuvos en contacte amb l'administrador");
		else {
			jugador.setEstat(true);
			jugadorBBDD.updateJugador(jugador);
			recup = sudokuBBDD.getTimestamps(su);
		}
	}

	public String[][] getTaulellBBDD() throws Exception {
		return taulellBBDD.getTaulell(su);
	}

	public String[][] getEditablesBBDD() throws Exception {
		return taulellBBDD.getEditables(su);
	}

	public Map<Integer, Timestamp> getTimeStamps() throws Exception {
		return sudokuBBDD.getTimestamps(su);
	}

	public void actualitzarBBDD() throws Exception {
		taulellBBDD.actualitzarBBDD(su);
	}

	public void setEstatJuagdor() throws Exception {

		if (!(jugador.getNom().equals("Anonim"))) {
			jugador.setEstat(false);
			jugadorBBDD.updateJugador(jugador);
		}
	}

	public void esborrarSudokuTaulell() throws Exception {
		taulellBBDD.esborrarTaulell(su);
		sudokuBBDD.esborrarSudoku(su);
	}

	public void setTaulell(Taulell t) {
		su.setTaulell(t);
	}

	public Map<Integer, Timestamp> getRecup() {
		return this.recup;
	}
}
