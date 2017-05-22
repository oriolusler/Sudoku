package Aplicacio;

import Domini.Casella;
import Domini.Taulell;

public class Control {

	private Taulell t;
	private Casella[][] caselles;

	public Control() throws Exception {

		t = new Taulell();
		caselles = new Casella[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				caselles[i][j] = new Casella();
			}
		}

		t.setGraella(caselles);
	}

	public void inciarCaselles() throws Exception {
		CrearGraella.crearGraella(caselles);
	}

	public void setEntrada(int fila, int columna, String valor) throws Exception {
		if (t.canviarValor(fila, columna, Integer.parseInt(valor)))
			throw new Exception("Error, valor repetit.");
		else
			caselles[fila][columna].setValor(Integer.parseInt(valor));

	}

	public void esborrarCasella(int fila, int columna) throws Exception {
		caselles[fila][columna].esborrarCasella();
	}

	public String[][] getTaulell() {

		String[][] taulell = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (caselles[i][j].getValor() != 0) {
					taulell[i][j] = "" + caselles[i][j].getValor();
				} else {
					taulell[i][j] = null;
				}
			}
		}
		return taulell;

	}

	public int[][] error() {
		return t.getError();
	}

	public boolean isComplete() {

		for (Casella[] aGraella : caselles) {
			for (int y = 0; y < aGraella.length; y++) {
				if (aGraella[y].getValor() == Casella.vDefecte)
					return false;
			}
		}
		return true;
	}

	public boolean esModificable(int fila, int columna) {
		return caselles[fila][columna].isEditable();
	}

	public void canviarTaulell() throws Exception {
		Casella[][] nova = t.canvis();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				caselles[i][j] = nova[i][j];
			}
		}
	}

	public void resetejarCasella() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				caselles[i][j] = new Casella();
			}
		}
	}

	public void iniciarUsuari() throws Exception {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (caselles[x][y].getValor() != 0)
					caselles[x][y].setCasella(caselles[x][y].getValor());
			}
		}
	}

	public void setEditable(int f, int c, boolean editable) {
		caselles[f][c].setEditable(editable);
	}

	public Casella[][] getTTaulell() {
		return caselles;
	}

	public boolean estaBuit() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (caselles[x][y].getValor() != 0)
					return false;
			}
		}

		return true;
	}

}
