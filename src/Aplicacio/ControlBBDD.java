package Aplicacio;

import Domini.Jugador;
import Domini.Sudoku;
import Domini.Taulell;
import Persistencia.FacanaBBDD;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ControlBBDD {

	private Map<Integer, Date> partidesRecuperades;
	private Sudoku sudoku;
	private Jugador jugador;

	private boolean inciar = false;

	public void logIn(String user, String password) throws Exception {
		FacanaBBDD.LoginUser(user, password);
	}

	public ControlBBDD(String nom) {
		jugador = new Jugador(nom);
		sudoku = new Sudoku(jugador);
	}

	public boolean getInciar() {
		return inciar;
	}

	public void setInciar(boolean inciar) {
		this.inciar = inciar;
	}

	public void iniciarSudoku() throws Exception {
		Date time = new Date();
		this.partidesRecuperades = FacanaBBDD.getInstance().getTimestamps(sudoku);
		sudoku = new Sudoku(time, getFirstIdLiure(partidesRecuperades), jugador, null);
	}

	private int getFirstIdLiure(Map<Integer, Date> recuperats) throws Exception {
		Set<Integer> IDSfromMAP = recuperats.keySet();
		for (int i = 1; i < 1000; i++) {
			if (!(IDSfromMAP.contains(i)))
				return i;
		}
		throw new Exception("Maxim partides guardades");
	}

	public void storeSudoku(Taulell t) throws Exception {
		sudoku.setTaulell(t);
		FacanaBBDD.getInstance().storeSudoku(sudoku);
	}

	public void nouJugador(String nom) throws Exception {

		jugador = FacanaBBDD.getInstance().getJugadorFromDB(nom);

		if (jugador == null) {
			jugador = new Jugador(nom, true);
			FacanaBBDD.getInstance().storeJugador(jugador);

		} else if (jugador.getEstat() == true) {
			throw new Exception("Aquest jugador esta actualment jugant.\nPoseuvos en contacte amb l'administrador");
		} else {
			jugador.setEstat(true);
			FacanaBBDD.getInstance().updateJugador(jugador);
			partidesRecuperades = FacanaBBDD.getInstance().getTimestamps(sudoku);

		}
	}

	public Map<Integer, Date> getTimeStamps() throws Exception {
		return this.partidesRecuperades;
	}

	public void esborrarSudokuTaulell() throws Exception {
		FacanaBBDD.getInstance().esborrarSudoku(sudoku);
	}

	public Map<Integer, Date> getPartidesRecuperades() {
		return this.partidesRecuperades;
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public void setEstatJuagdor() throws Exception {

		if (!(jugador.getNom().equals("Anonim"))) {
			jugador.setEstat(false);
			FacanaBBDD.getInstance().updateJugador(jugador);
		}
	}

	public void recuperarTaulellGuardat() throws Exception {
		FacanaBBDD.getInstance().recuperarTaulellFromSudoku(sudoku);
	}

	public Taulell getTaulellFromSudoku() {
		return sudoku.getTaulell();
	}

}
