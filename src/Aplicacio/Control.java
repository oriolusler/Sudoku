package Aplicacio;

import Domini.Casella;
import Domini.Jugador;
import Domini.Taulell;
import Persistencia.JugadorBBDD;
import Persistencia.TaulellBBDD;

public class Control {

	private Taulell t;
	private Jugador jugador;


	public Control(boolean buit) throws Exception {
	
		t = new Taulell(buit,getUltimId());
		
	}
	
	public Control(Casella[][] a) throws Exception{
		t=new Taulell(getUltimId(), a);
		
	}
	public void getNumero(int i){
		t.getNumero(i);
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

	// ////////////////////////////
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

	public void updateTaulell(int x, int y, String valor, Casella[][] taulell)
			throws Exception {
		int value = Integer.parseInt(valor);
		TaulellBBDD.updateTaulell(x, y, value, taulell);
	}

	public void storeTaulell(Casella[][] taulell, String nom) throws Exception {
		jugador=new Jugador(nom);
		 int quants= quantsTaulells() +1;
		TaulellBBDD.storeTaullell(taulell,jugador,quants);
	}


	public boolean taulellBuit(int i) throws Exception {
		return TaulellBBDD.estaBuit(i);
	}

	public int quantsTaulells() throws Exception{
		
		return TaulellBBDD.quantesPartides();
	}
	public void nouJugador(String nom) throws Exception {

		jugador = new Jugador(nom);
		try {
			JugadorBBDD.storeJugador(jugador);
		} catch (Exception e) {
			throw new Exception("Aquest jugador esta registrat");
		}

	}

	public String nomsBDD() {

		try {
			return JugadorBBDD.getJugadors();
		} catch (Exception e) {
			return null;
		}

	}

	// ////////////////////////////////

	public String[][] getTaulellBBDD(int i) {
		try {
			return TaulellBBDD.getTaulell(i);
		} catch (Exception e) {
			return null;
		}
	}
	
	public int getUltimId() throws Exception{
		return TaulellBBDD.getUltimId();
	}
	
	public int[] getTotalIdSu() throws Exception{
		return TaulellBBDD.getTotalIdSu();
		
	}
	
}
