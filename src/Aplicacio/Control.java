package Aplicacio;

import Domini.Jugador;
import Domini.Sudoku;
import Domini.Taulell;

import java.util.Date;
import java.util.Map;

public class Control {

	private Taulell taulell;
	private ControlBBDD controlBBDD;

	public Control(ControlBBDD controlBBDD) {
		taulell = new Taulell();
		this.controlBBDD = controlBBDD;
	}

	public void iniciarSudokuPredefinit() throws Exception {
		CrearGraella.crearGraella(taulell);
	}

	public void inciarSudokuBuit() {
		taulell.resetejarCasella();
	}

	public void setEntrada(int fila, int columna, String valor) throws Exception {
		taulell.canviarValor(fila, columna, Integer.parseInt(valor));
	}

	public boolean estaBuit() {
		return taulell.estaBuit();
	}

	public void esborrarCasella(int fila, int columna) throws Exception {
		taulell.esborrarCasella(fila, columna);
	}

	public String[][] getTaulell() {
		return taulell.getTaulell();
	}

	public int[][] error() {
		return taulell.getError();
	}

	public boolean isComplete() {
		return taulell.esComplet();
	}

	public boolean esModificable(int fila, int columna) {
		return taulell.esModificable(fila, columna);
	}

	public void canviarTaulell() throws Exception {
		taulell.canvis();
	}

	public void iniciarUsuari() throws Exception {
		taulell.iniciarUsuari();
	}

	public void setTaulell(Taulell nouTaulell) {
		this.taulell = nouTaulell;

	}
	// METODES CONTROL BBDD //

	public Jugador getJugador() {
		return controlBBDD.getJugador();
	}

	public Map<Integer, Date> getPartidesRecuperades() throws Exception {
		return controlBBDD.getPartidesRecuperades();
	}

	public void iniciarSudoku() throws Exception {
		controlBBDD.iniciarSudoku();
	}

	public void storeSudoku() throws Exception {
		controlBBDD.storeSudoku(this.taulell);
	}

	public Sudoku getSudoku() {
		return controlBBDD.getSudoku();
	}

	public void setEstatJuagdor() throws Exception {
		controlBBDD.setEstatJuagdor();
	}

	public void esborrarSudokuTaulell() throws Exception {
		controlBBDD.esborrarSudokuTaulell();
	}

	public void recuperarTaulell() throws Exception {
		controlBBDD.recuperarTaulellGuardat();
		setTaulell(controlBBDD.getTaulellFromSudoku());
	}
}
