package Aplicacio;

import java.sql.Timestamp;
import java.util.Calendar;
import Domini.Casella;
import Domini.Jugador;
import Domini.Sudoku;
import Domini.Taulell;
import Persistencia.JugadorBBDD;
import Persistencia.SudokuBBDD;
import Persistencia.TaulellBBDD;

public class Control {

	private Taulell t;
	private Jugador jugador;
	private Sudoku su;
	private java.sql.Timestamp time;

	public Control(boolean buit) throws Exception {

		Calendar calendar = Calendar.getInstance();
		time = new java.sql.Timestamp(calendar.getTime().getTime());
		t = new Taulell(buit);

	}

	public Control(Casella[][] a) throws Exception {
		Calendar calendar = Calendar.getInstance();
		time = new java.sql.Timestamp(calendar.getTime().getTime());
		t = new Taulell(a);

	}

	public void setEntrada(int fila, int columna, String valor)
			throws Exception {
		t.canviarValor(fila, columna, Integer.parseInt(valor));
	}

	public void esborrarCasella(int fila, int columna) throws Exception {
		t.esborrarCasella(fila, columna);
	}

	public String[][] getTaulell() {
		return t.getTaulell();
	}

	public boolean sudokuBuit(int i) throws Exception {
		return SudokuBBDD.estaBuit(i);
	}

	public void storeSudoku(int quinSudoku, String nom) throws Exception {
		
		su = new Sudoku(time, quinSudoku, nom);
		SudokuBBDD.storeSudoku(su);
	}

	public Casella[][] getTTaulell() {
		return t.getCasella();
	}

	public int[][] error() {
		return t.getError();
	}

	public boolean isComplete() {
		return t.esComplet();
	}

	public boolean esModificable(int fila, int columna) {
		return t.esModificable(fila, columna);
	}

	public void canviarTaulell() throws Exception {
		t.canvis();
	}

	public void iniciarUsuari() throws Exception {
		t.iniciarUsuari();
	}

	

	public void storeTaulell(Casella[][] taulell, int quantsTaulells)
			throws Exception {
		int quants = quantsTaulells + 1;
		TaulellBBDD.storeTaullell(taulell, quants);
	}

	public boolean taulellBuit(int i) throws Exception {
		return TaulellBBDD.estaBuit(i);
	}

	public int quantsTaulells() throws Exception {

		return TaulellBBDD.quantesPartides();
	}

	public int nouJugador(String nom) throws Exception {

		jugador = new Jugador(nom);

		try {
			JugadorBBDD.storeJugador(jugador);
			return -2;
		} catch (Exception e) {
			return 1;
		}
	}

	public String[][] getTaulellBBDD(int i) {
		try {
			return TaulellBBDD.getTaulell(i);
		} catch (Exception e) {
			return null;
		}
	}

	public String[][] getEditablesBBDD(int i) {
		try {
			return TaulellBBDD.getEditables(i);
		} catch (Exception e) {
			return null;
		}
	}

	public Timestamp[] getTimeStamps() throws Exception {
		return SudokuBBDD.getTimestamps(jugador.getNom());

	}

	public void setEditable(int f, int c, boolean editable) {
		t.setEditable(f, c, editable);
	}

	public void setCasella(int x, int y, int valor) throws Exception {
		t.setCasella(x, y, valor);
	}

	public void actualitzarBBDD(Casella[][] taulell, int quinSu)
			throws Exception {
		TaulellBBDD.actualitzarBBDD(taulell, quinSu);
	}

	public int getIdFromTimeStamp(Timestamp input) throws Exception {
		return SudokuBBDD.getIdFromTimeStamp(input);

	}

}
