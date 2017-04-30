package Aplicacio;

import java.util.Calendar;

import javax.swing.JOptionPane;

import Domini.Casella;
import Domini.Jugador;
import Domini.Taulell;
import Persistencia.JugadorBBDD;
import Persistencia.TaulellBBDD;

public class Control {

	private Taulell t;
	private Jugador jugador;

	public Control(boolean buit) throws Exception {

		Calendar calendar = Calendar.getInstance();
	    java.sql.Timestamp time = new java.sql.Timestamp(calendar.getTime().getTime());
	    System.out.println(time);
		t = new Taulell(buit, getUltimId(),time);

	}

	public Control(Casella[][] a) throws Exception {
		Calendar calendar = Calendar.getInstance();
	    java.sql.Timestamp time = new java.sql.Timestamp(calendar.getTime().getTime());
	    System.out.println(time);
		t = new Taulell(getUltimId(), a, time);

	}
	
	

	public void getNumero(int i) {
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

	////////////
	
	public void setCCounter(int i){
		t.setCcpunter(i);
	}
	
	public int getCCouter(){
		return t.getCcpunter();
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

	public void updateTaulell(int x, int y, String valor, Casella[][] taulell,
			int su) throws Exception {
		int value = Integer.parseInt(valor);
		TaulellBBDD.updateTaulell(x, y, value, taulell, su);
	}

	public void storeTaulell(Casella[][] taulell, String nom, int quantsTaulells) throws Exception {
		jugador = new Jugador(nom);
		int quants = quantsTaulells + 1;
		TaulellBBDD.storeTaullell(taulell, jugador, quants);
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
			int[] quantsId = getTotalIdSu();
			int quantsBons = 0;
			for (int i = 0; i < quantsId.length; i++) {

				if (!(quantsId[i] == 0)) {
					quantsBons++;
				}
			}
			String[] buttons = new String[quantsBons];

			for (int i = 0; i < quantsId.length; i++) {

				if (!(quantsId[i] == 0)) {
					buttons[i] = String.valueOf(quantsId[i]);
				}
			}

			int rc = JOptionPane.showOptionDialog(null, "Quin sudoku vols recuperar?",
					"Confirmation", JOptionPane.WARNING_MESSAGE, 0, null,
					buttons, buttons);
			rc += 1;
			return rc;
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

	public String[][] getEditablesBBDD(int i) {
		try {
			return TaulellBBDD.getEditables(i);
		} catch (Exception e) {
			return null;
		}
	}
	
	public int getUltimId() throws Exception {
		return TaulellBBDD.getUltimId();
	}

	public int[] getTotalIdSu() throws Exception {
		return TaulellBBDD.getTotalIdSu();

	}

}
