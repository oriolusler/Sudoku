package Aplicacio;

import java.util.Date;
import java.util.Map;

import Domini.Jugador;
import Domini.Sudoku;
import Domini.Taulell;

public class Control {

	private Taulell taulell;
	private ControlBBDD controlBBDD;

	// OK
	public Control(ControlBBDD controlBBDD) {
		taulell = new Taulell();
		this.controlBBDD = controlBBDD;
	}

	// OK
	public void iniciarSudokuPredefinit() throws Exception {
		CrearGraella.crearGraella(taulell);
	}

	// OK
	public void inciarSudokuBuit() {
		taulell.resetejarCasella();
	}

	// OK
	public void setEntrada(int fila, int columna, String valor) throws Exception {
		taulell.canviarValor(fila, columna, Integer.parseInt(valor));
	}

	// IDK
	public boolean estaBuit() {
		return taulell.estaBuit();
	}

	// OK
	public void esborrarCasella(int fila, int columna) throws Exception {
		taulell.esborrarCasella(fila, columna);
	}

	// OK
	public String[][] getTaulell() {
		return taulell.getTaulell();
	}

	// OK
	public int[][] error() {
		return taulell.getError();
	}

	// OK
	public boolean isComplete() {
		return taulell.esComplet();
	}

	// OK
	public boolean esModificable(int fila, int columna) {
		return taulell.esModificable(fila, columna);
	}

	// OK
	public void canviarTaulell() throws Exception {
		taulell.canvis();
	}

	// OK
	public void iniciarUsuari() throws Exception {
		taulell.iniciarUsuari();
	}

	// OK
	public void setTaulell(Taulell nouTaulell) {
		this.taulell = nouTaulell;

	}

	// METODES CONTROL BBDD //

	// OK
	public Jugador getJugador() {
		return controlBBDD.getJugador();
	}

	// OK
	public Map<Integer, Date> getTimeStamps() throws Exception {
		return controlBBDD.getTimeStamps();
	}

	// OK
	public void iniciarSudoku() throws Exception {
		controlBBDD.iniciarSudoku();
	}

	// OK
	public void storeSudoku() throws Exception {
		controlBBDD.storeSudoku(this.taulell);
	}

	//OK
	public Sudoku getSudoku(){
		return controlBBDD.getSudoku();
	}

	//OK
	public void setEstatJuagdor() throws Exception {
		controlBBDD.setEstatJuagdor();
	}

	//OK
	public void esborrarSudokuTaulell() throws Exception {
		controlBBDD.esborrarSudokuTaulell();
	}

	//OK
	public void recuperarTaulell() throws Exception {
		controlBBDD.recuperarTaulellGuardat();
		setTaulell(controlBBDD.getTaulellFromSudoku());
	}
}
