package Aplicacio;

import Domini.Jugador;
import Domini.Sudoku;
import Domini.Taulell;
import Persistencia.FacanaBBDD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ControlBBDD {

	private Map<Integer, Date> partidesRecuperades;
	private Sudoku sudoku;
	private Jugador jugador;

	public ControlBBDD(String nom) {
		jugador = new Jugador(nom);
		sudoku = new Sudoku(jugador);
	}

	// LOGIN
	// Presentacio/UserLoginBBDD
	public void logIn(String user, String password) throws Exception {
		FacanaBBDD.getInstance().LoginUser(user, password);
	}

	// JUGADOR
	Jugador getJugador() {
		return this.jugador;
	}

	public void setJugadorNom(String nom) {
		jugador.setNom(nom);

	}
	
	public void nouJugador(String nom) throws Exception {

		jugador.setNom(nom);
		jugador = FacanaBBDD.getInstance().getJugadorFromDB(nom);
		if (jugador == null) {
			jugador = new Jugador(nom, true);
			FacanaBBDD.getInstance().storeJugador(jugador);
			partidesRecuperades = new HashMap<Integer, Date>();
		} else if (jugador.getEstat() == true) {
			throw new Exception("Aquest jugador esta actualment jugant.\nPoseuvos en contacte amb l'administrador");
		} else {

			jugador.setEstat(true);
			FacanaBBDD.getInstance().updateJugador(jugador);
			partidesRecuperades = FacanaBBDD.getInstance().getTimestamps(sudoku);

		}
	}

	public void setEstatJuagdor() throws Exception {

		if (!(jugador.getNom().equals("Anonim"))) {
			jugador.setEstat(false);
			FacanaBBDD.getInstance().updateJugador(jugador);
		}
	}

	// PARTIDA
	Sudoku getSudoku() {
		return sudoku;
	}

	public void iniciarSudoku() {
		Date time = new Date();
		// sudoku = new Sudoku(time, getFirstIdLiure(partidesRecuperades),
		// jugador, null);
		sudoku = new Sudoku(time, -1, jugador, null);
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

		if (sudoku.getIdSudoku() == -1) {
			sudoku.setIdSudoku(getFirstIdLiure(partidesRecuperades));
		}

		if (!(partidesRecuperades.containsValue(sudoku.getIdSudoku()))) {
			partidesRecuperades.put(sudoku.getIdSudoku(), sudoku.getTime());
		}

		sudoku.setTaulell(t);
		FacanaBBDD.getInstance().storeSudoku(sudoku);
	}

	public void esborrarSudokuTaulell() throws Exception {
		FacanaBBDD.getInstance().esborrarSudoku(sudoku);
	}


	public void setIdSudoku(int nouID) {
		sudoku.setIdSudoku(nouID);

	}

	public void setTimeStampSudoku(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		Date Date = df.parse(date);
		sudoku.setTime(Date);

	}

	// TAULELL
	public void recuperarTaulellGuardat() throws Exception {
		FacanaBBDD.getInstance().recuperarTaulellFromSudoku(sudoku);
	}

	// PARTIDES RECUPERADES
	public String[] getIdAndDateFromDataBase() {

		Set<Integer> IDSfromMAP = partidesRecuperades.keySet();
		Collection<Date> DATAfromMAP = partidesRecuperades.values();

		Integer[] IdSudokusRecuperats = (Integer[]) (IDSfromMAP.toArray(new Integer[IDSfromMAP.size()]));
		Date[] DatesRecuperades = (Date[]) (DATAfromMAP.toArray(new Date[IDSfromMAP.size()]));
		String[] stringPerMostrat = new String[partidesRecuperades.size()];

		for (int i = 0; i < partidesRecuperades.size(); i++) {
			stringPerMostrat[i] = IdSudokusRecuperats[i] + " - " + DatesRecuperades[i];
		}

		return stringPerMostrat;
	}

	public Map<Integer, Date> getPartidesRecuperades() {

		return this.partidesRecuperades;
	}


	public boolean hiHanParitdesGuardades() {
		return !(this.partidesRecuperades == null || this.partidesRecuperades.size() == 0);
	}

}
