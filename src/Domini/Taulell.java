package Domini;

import java.util.LinkedList;

import Aplicacio.CrearGraella;

public class Taulell {

	private Casella[][] graella;
	private int[][] error = new int[2][3];

	public Taulell() throws Exception {

		graella = new Casella[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				graella[i][j] = new Casella();
			}
		}

		iniciarErrors();

	}

	public void iniciarTaulell() throws Exception {
		CrearGraella.crearGraella(this);
	}

	public int[][] getError() {
		return error;
	}

	private void iniciarErrors() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				error[i][j] = -1;
			}
		}
	}

	public boolean esComplet() {
		for (Casella[] aGraella : graella) {
			for (int y = 0; y < aGraella.length; y++) {
				if (aGraella[y].getValor() == Casella.vDefecte)
					return false;
			}
		}
		return true;
	}

	public void iniciarUsuari() throws Exception {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (graella[x][y].getValor() != 0)
					setCasella(x, y, graella[x][y].getValor());
			}
		}
	}

	/*
	 * private void CrearTaulell() { for (int i = 0; i < 9; i++) { for (int j =
	 * 0; j < 9; j++) { graella[i][j] = new Casella(); } } }
	 */
	
	public String[][] getTaulell() {
		String[][] taulell = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (graella[i][j].getValor() != 0) {
					taulell[i][j] = "" + graella[i][j].getValor();
				} else {
					taulell[i][j] = null;
				}
			}
		}
		return taulell;
	}

	public void setCasella(int x, int y, int valor) throws Exception {
		graella[x][y].setCasella(valor);
	}

	public void canviarValor(int f, int c, int valor) throws Exception {

		iniciarErrors();
		Coordenada cord = new Coordenada(f, c);

		LinkedList<Coordenada> columnes = cord.mateixaColumna();
		LinkedList<Coordenada> files = cord.mateixaFila();
		LinkedList<Coordenada> blocs = cord.mateixBloc();

		boolean valorRepe = this.valorRepe(blocs, valor, 2);
		valorRepe = this.valorRepe(columnes, valor, 1) || valorRepe;
		valorRepe = this.valorRepe(files, valor, 0) || valorRepe;

		if (valorRepe) {
			throw new Exception("Error, valor repetit.");
		} else {
			graella[f][c].setValor(valor);
		}
	}

	private boolean valorRepe(LinkedList<Coordenada> coordenades, int valor,
			int llocError) {
		int fila, columna;
		for (Coordenada coordenada : coordenades) {
			fila = coordenada.getFila();
			columna = coordenada.getColumna();
			if (graella[fila][columna].getValor() == valor) {
				error[0][llocError] = fila;
				error[1][llocError] = columna;
				return true;
			}
		}
		return false;
	}

	public void esborrarCasella(int fila, int columna) throws Exception {
		new Coordenada(fila, columna);
		graella[fila][columna].esborrarCasella();
	}

	public boolean esModificable(int fila, int columna) {
		new Coordenada(fila, columna);
		return graella[fila][columna].isEditable();
	}

	public Casella[][] canvis() throws Exception {
		return new Equivalent().nouCasella(this.graella);
	}

	public void setGraella(Casella[][] caselles) {
		this.graella = caselles;

	}

	public void setEditable(int fila, int columna, boolean editable) {
		new Coordenada(fila, columna);
		graella[fila][columna].setEditable(editable);
	}

	public Casella[][] getCasella() {
		return this.graella;
	}

	public void resetejarCasella() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				graella[i][j] = new Casella();
			}
		}
	}

	public boolean estaBuit() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (graella[x][y].getValor() != 0)
					return false;
			}
		}

		return true;
	}
}
