package Domini;

import java.sql.Timestamp;

public class Sudoku {

	private Timestamp time;
	private int quinSudoku;
	private String nom;
	public Timestamp getTime() {
		return time;
	}
	public int getQuinSudoku() {
		return quinSudoku;
	}
	public String getNom() {
		return nom;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public void setQuinSudoku(int quinSudoku) {
		this.quinSudoku = quinSudoku;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Sudoku(Timestamp time, int quinSudoku, String nom) {
	
		this.time = time;
		this.quinSudoku = quinSudoku;
		this.nom = nom;
	}
	
	
	
}
