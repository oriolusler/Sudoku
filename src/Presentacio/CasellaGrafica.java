package Presentacio;

class CasellaGrafica extends javax.swing.JTextField {

	private int fila, columna;
	private boolean focus;

	CasellaGrafica(int fila, int columna) {
		super();
		this.fila = fila;
		this.columna = columna;
		focus = false;
	}

	int getFila() {
		return fila;
	}

	int getColumna() {
		return columna;
	}

	boolean isFocus() {
		return focus;
	}

	void setFocus(boolean focus) {
		this.focus = focus;
	}
}