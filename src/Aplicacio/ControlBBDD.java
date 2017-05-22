package Aplicacio;

import java.sql.Timestamp;
import java.util.Calendar;

import Domini.Casella;
import Domini.Jugador;
import Domini.Sudoku;
import Persistencia.JugadorBBDD;
import Persistencia.SudokuBBDD;
import Persistencia.TaulellBBDD;

public class ControlBBDD {

	private Sudoku su;
	private Jugador jugador;
	private java.sql.Timestamp time;
	private JugadorBBDD jugadorBBDD;
	private SudokuBBDD sudokuBBDD;
	private TaulellBBDD taulellBBDD;

	public ControlBBDD(String nom) {

		jugador = new Jugador(nom);

		this.jugadorBBDD = new JugadorBBDD();
		this.sudokuBBDD = new SudokuBBDD();
		this.taulellBBDD = new TaulellBBDD();
	}

	public void iniciarSudoku() throws Exception {
		Calendar calendar = Calendar.getInstance();
		time = new java.sql.Timestamp(calendar.getTime().getTime());
		su = new Sudoku(time, quantsTaulells() + 1, jugador);

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

	public void storeTaulell(Casella[][] taulell) throws Exception {
		setSudokuID(quantsTaulells() + 1);
		taulellBBDD.storeTaullell(taulell, su);
	}

	public boolean taulellBuit() throws Exception {
		return taulellBBDD.estaBuit(su);
	}

	public int quantsTaulells() throws Exception {
		return taulellBBDD.quantesPartides(su);
	}

	public int nouJugador(String nom) throws Exception {

		jugador = new Jugador(nom, 1);

		try {
			jugadorBBDD.storeJugador(jugador);
			return -2;
		} catch (Exception e) {
			jugadorBBDD.updateJugador(jugador);
			return 1;
		}
	}

	public String[][] getTaulellBBDD() throws Exception {
		return taulellBBDD.getTaulell(su);
	}

	public String[][] getEditablesBBDD() throws Exception {
		return taulellBBDD.getEditables(su);
	}

	public Timestamp[] getTimeStamps() throws Exception {
		return sudokuBBDD.getTimestamps(su);
	}

	public void actualitzarBBDD(Casella[][] taulell) throws Exception {
		taulellBBDD.actualitzarBBDD(taulell, su);
	}

	public int getIdFromTimeStamp(Timestamp input) throws Exception {
		return sudokuBBDD.getIdFromTimeStamp(input);
	}

	public void setEstatJuagdor() throws Exception {
		
		if (!(jugador.getNom().equals("Anonim"))) {
			jugador.setEstat(0);
			jugadorBBDD.updateJugador(jugador);
		} else {
			System.out.print("OK");
		}
	}

	public void esborrarSudokuTaulell() throws Exception {
		taulellBBDD.esborrarTaulell(su);
		sudokuBBDD.esborrarSudoku(su);
	}

}
