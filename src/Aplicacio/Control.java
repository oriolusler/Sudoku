package Aplicacio;

import Domini.Casella;
import Domini.Taulell;

public class Control {

	private Taulell t;

	public Control(boolean buit) throws Exception {
		t = new Taulell(buit);
	}

	public Control(Casella[][] a) throws Exception {
		t = new Taulell(a);
	}

	public void setEntrada(int fila, int columna, String valor)
			throws Exception {

		try {
			Integer.valueOf(valor);
		} catch (Exception e) {
			throw new Exception("Numero introduit esborrat");
		}
		t.canviarValor(fila, columna, Integer.parseInt(valor));
	}

	public void esborrarCasella(int fila, int columna) throws Exception {
		t.esborrarCasella(fila, columna);
	}

	public String[][] getTaulell() {
		return t.getTaulell();
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

	public void setEditable(int f, int c, boolean editable) {
		t.setEditable(f, c, editable);
	}

	public void setCasella(int x, int y, int valor) throws Exception {
		t.setCasella(x, y, valor);
	}

}
