package Domini;

import java.util.Date;

public class Sudoku {

	private Date time;
	private int iDsudoku;
	private Jugador jugador;
	private Taulell taulell;

	public Taulell getTaulell() {
		return taulell;
	}

	public void setTaulell(Taulell t) {
		this.taulell = t;
	}

	public Date getTime() {
		return time;
	}

	public int getIdSudoku() {
		return iDsudoku;
	}
	
	public void setIdSudoku(int nouID){
		this.iDsudoku=nouID;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setQuinSudoku(int quinSudoku) {
		this.iDsudoku = quinSudoku;
	}

	public Sudoku(Date time, int quinSudoku, Jugador jugador, Taulell ta) {

		this.time = time;
		this.iDsudoku = quinSudoku;
		this.jugador = jugador;
		this.taulell = ta;
	}

	public Sudoku(Jugador jugador) {
		this.jugador = jugador;
	}
}
