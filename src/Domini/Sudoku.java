package Domini;

import java.sql.Timestamp;

public class Sudoku {

	private java.sql.Timestamp time;
	private int quinSudoku;
	private Jugador jugador;
	private Taulell taulell;

	public Taulell getTaulell() {
		return taulell;
	}

	public void setTaulell(Taulell t) {
		this.taulell = t;
	}

	public Timestamp getTime() {
		return time;
	}

	public int getQuinSudoku() {
		return quinSudoku;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public void setQuinSudoku(int quinSudoku) {
		this.quinSudoku = quinSudoku;
	}

	public Sudoku(Timestamp time, int quinSudoku, Jugador jugador) {

		this.time = time;
		this.quinSudoku = quinSudoku;
		this.jugador = jugador;
		this.taulell = taulell;
	}

}
