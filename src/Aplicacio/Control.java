package Aplicacio;

import Domini.Casella;
import Domini.Taulell;
import Persistencia.TaulellBBDD;

public class Control {

	private Taulell t;

	public Control(boolean buit) throws Exception {
		t = new Taulell(buit);
	}

	public void setEntrada(int fila, int columna, String valor) throws Exception {
		t.canviarValor(fila, columna, Integer.parseInt(valor));
	}

	public void esborrarCasella(int fila, int columna) throws Exception {
		t.esborrarCasella(fila, columna);
	}

	public String[][] getTaulell() {
		return t.getTaulell();
	}
	
	//////////////////////////////
	public Casella[][] getTTaulell(){
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
	
	public void updateTaulell(int x, int y, String valor,Casella[][] taulell) throws Exception {
		int value=Integer.parseInt(valor);
		TaulellBBDD.updateTaulell(x, y, value, taulell);
	}
	
	public void storeTaulell(Casella[][] taulell) throws Exception {
		TaulellBBDD.storeTaullell(taulell);
	}
	
	
	public boolean taulellBuit() throws Exception{
		return TaulellBBDD.estaBuit();
	}


}
