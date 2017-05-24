package Aplicacio;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

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
		su = new Sudoku(time, quantsTaulells() + 1, jugador, ta);

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

	public int quantsTaulells() throws Exception {
		return jugadorBBDD.quantesPartides(jugador);
	}

	public void nouJugador(String nom) throws Exception {
		//
		// jugador.setEstat(true);
		// if (!(jugadorBBDD.estaJugantActualment(jugador))) {
		// jugadorBBDD.storeJugador(jugador);
		// recup = sudokuBBDD.getTimestamps(su);
		// } else
		// throw new
		// Exception("Aquest jugador esta actualment jugant.\nPoseuvos en contacte amb l'administrador");

		Jugador jugadorRecuperatDeDB = jugadorBBDD.getJugadorFromDB(nom);

		if (jugadorRecuperatDeDB == null) {
			jugadorBBDD.storeJugador(jugadorRecuperatDeDB);
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
