package Aplicacio;

import java.util.Date;
import java.util.Map;

import Domini.Casella;
import Domini.Taulell;

public class Control {

	private Taulell taulell;
	private ControlBBDD controlBBDD;

	public Control(ControlBBDD controlBBDD) {

		taulell = new Taulell();
		this.controlBBDD = controlBBDD;
	}

	public Taulell getTaulellT() {
		return this.taulell;
	}

	public void inciarCaselles() throws Exception {
		CrearGraella.crearGraella(taulell);
	}

	public void setEntrada(int fila, int columna, String valor) throws Exception {
		taulell.canviarValor(fila, columna, Integer.parseInt(valor));

	}

	public void resetejarCasella() {
		taulell.resetejarCasella();
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

	public Casella[][] getTTaulell() {
		return taulell.getCasella();
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

	public void setEditable(int fila, int columna, boolean editable) {
		taulell.setEditable(fila, columna, editable);
	}

	public void setCasella(int fila, int columna, int valor) throws Exception {
		taulell.setCasella(fila, columna, valor);
	}

	// METODES CONTROL BBDD //
	
	public String getNomJugador() {
		return controlBBDD.getNomJugador();
	}

	public String[][] getTaulellBBDD() throws Exception {
		return controlBBDD.getTaulellBBDD();
	}

	public String[][] getEditablesBBDD() throws Exception {
		return controlBBDD.getEditablesBBDD();
	}

	public int getSudokuID() {
		return controlBBDD.getSudokuID();
	}

	public Map<Integer, Date> getTimeStamps() throws Exception {
		return controlBBDD.getTimeStamps();
	}

	public void iniciarSudoku() throws Exception {
		controlBBDD.iniciarSudoku();

	}

	public void setTaulell(Taulell taulellT) {
		controlBBDD.setTaulell(taulellT);

	}

	public boolean sudokuBuit() throws Exception {
		return controlBBDD.sudokuBuit();
	}

	public void storeSudoku() throws Exception {
		controlBBDD.storeSudoku();

	}

	public boolean taulellBuit() throws Exception {
		return controlBBDD.taulellBuit();
	}

	public void actualitzarBBDD() throws Exception {
		controlBBDD.actualitzarBBDD();

	}

	public void setSudokuID(int iDsudoku) {
		controlBBDD.setSudokuID(iDsudoku);

	}

	public void setEstatJuagdor() throws Exception {
		controlBBDD.setEstatJuagdor();
		
	}

	public void esborrarSudokuTaulell() throws Exception {
		controlBBDD.esborrarSudokuTaulell();
		
	}
}
