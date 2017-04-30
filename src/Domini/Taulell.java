package Domini;

import java.util.LinkedList;

import Aplicacio.CrearGraella;

public class Taulell {

	private Casella[][] graella;
	private int[][] error = new int[2][3];
	private java.sql.Timestamp identificadorTaulell;

	public java.sql.Timestamp getIdentificadorTaulell() {
		return identificadorTaulell;
	}

	public void setIdentificadorTaulell(java.sql.Timestamp identificadorTaulell) {
		this.identificadorTaulell = identificadorTaulell;
	}

	private  int Ccpunter = 1;
	private int counter = 1;

	public void setCcpunter(int ccpunter) {
		Ccpunter = ccpunter;
	}
	
	public int getCcpunter(){
		return Ccpunter;
	}

	private int idSu;

	//
	public Taulell(boolean buit, int lastID, java.sql.Timestamp time) throws Exception {

		this.identificadorTaulell=time;
		lastID = lastID + 1;
		//System.out.print(lastID);
		Ccpunter = (lastID++);

		graella = new Casella[9][9];
		iniciarErrors();
		CrearTaulell();
		if (!buit){
			CrearGraella.crearGraella(this);
			
			
			/*MATEIX
			 
			this.setCasella(4, 3, 8);
			this.setCasella(8, 7, 7);
			this.setCasella(0, 4, 7);
			this.setCasella(4, 8, 1);
			this.setCasella(0, 1, 3);
			this.setCasella(1, 3, 1);
			this.setCasella(5, 4, 2);
			this.setCasella(1, 0, 6);
			this.setCasella(1, 5, 5);
			this.setCasella(6, 1, 6);
			this.setCasella(1, 4, 9);
			this.setCasella(2, 2, 8);
			this.setCasella(6, 7, 8);
			this.setCasella(2, 1, 9);
			this.setCasella(3, 0, 8);
			this.setCasella(7, 4, 1);
			this.setCasella(2, 7, 6);
			this.setCasella(3, 8, 3);
			this.setCasella(7, 8, 5);
			this.setCasella(3, 4, 6);
			this.setCasella(4, 0, 4);
			this.setCasella(4, 5, 3);
			this.setCasella(5, 0, 7);
			this.setCasella(5, 8, 6);
			this.setCasella(6, 6, 2);
			this.setCasella(7, 3, 4);
			this.setCasella(7, 5, 9);
			this.setCasella(8, 4, 8);
			this.setCasella(8, 8, 9);	*/
		
		}
	}
	
	public Taulell(int lastID, Casella[][] taulell,java.sql.Timestamp time) throws Exception{
		
		this.identificadorTaulell=time;
		lastID = lastID + 1;
		System.out.print(lastID);
		Ccpunter = (lastID++);

		graella = new Casella[9][9];
		iniciarErrors();
		CrearTaulell();
	
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(!(taulell[i][j].isEditable())){
					this.setCasella(i, j, taulell[i][j].getValor());
				}
			}
		}
		
	}

	public void getNumero(int i) {

		i = i + 1;
		Ccpunter = i;
	}

	public int getIdSu() {
		return idSu;
	}

	public void setIdCasella(int idSu) {
		this.idSu = idSu;
	}

	public Casella[][] getCasella() {
		return this.graella;
	}

	public int[][] getError() {
		return error;
	}

	private void iniciarErrors() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				error[i][j] = -1;
			}
		}
	}

	public boolean esComplet() {
		for (Casella[] aGraella : graella) {
			for (int y = 0; y < aGraella.length; y++) {
				if (aGraella[y].getValor() == Casella.vDefecte)
					return false;
			}
		}
		return true;
	}

	public void iniciarUsuari() throws Exception {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (graella[x][y].getValor() != 0)
					setCasella(x, y, graella[x][y].getValor());
			}
		}
	}

	public void CrearTaulell() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				graella[i][j] = new Casella();
				graella[i][j].setIdCasella(Ccpunter);
				graella[i][j].setIdC(counter);
				counter++;
				Ccpunter++;

				// ////////////////////////
				//System.out.print(Ccpunter);
			}
		}
	}

	public String[][] getTaulell() {
		String[][] taulell = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (graella[i][j].getValor() != 0) {
					taulell[i][j] = "" + graella[i][j].getValor();
				} else {
					taulell[i][j] = null;
				}
			}
		}
		return taulell;
	}

	public void setCasella(int x, int y, int valor) throws Exception {
		graella[x][y].setCasella(valor);
	}

	// ////////////////////////////////////////////////////////////////////

	public int getValorCasella(int x, int y) {
		return graella[x][y].getValor();
	}

	public int getIdCasella(int x, int y) {
		return graella[x][y].getIdCasella();
	}

	// ////////////////////////////////////////////////////////////////////

	public void canviarValor(int f, int c, int valor) throws Exception {

		iniciarErrors();
		Coordenada cord = new Coordenada(f, c);

		LinkedList<Coordenada> columnes = cord.mateixaColumna();
		LinkedList<Coordenada> files = cord.mateixaFila();
		LinkedList<Coordenada> blocs = cord.mateixBloc();

		boolean valorRepe = this.valorRepe(blocs, valor, 2);
		valorRepe = this.valorRepe(columnes, valor, 1) || valorRepe;
		valorRepe = this.valorRepe(files, valor, 0) || valorRepe;

		if (valorRepe) {
			throw new Exception("Error valor repetit....");
		} else {
			graella[f][c].setValor(valor);
		}
	}

	private boolean valorRepe(LinkedList<Coordenada> coordenades, int valor,
			int llocError) {
		int fila, columna;
		for (Coordenada coordenada : coordenades) {
			fila = coordenada.getFila();
			columna = coordenada.getColumna();
			if (graella[fila][columna].getValor() == valor) {
				error[0][llocError] = fila;
				error[1][llocError] = columna;
				return true;
			}
		}
		return false;
	}

	public void esborrarCasella(int fila, int columna) throws Exception {
		new Coordenada(fila, columna);
		graella[fila][columna].esborrarCasella();
	}

	public boolean esModificable(int fila, int columna) {
		new Coordenada(fila, columna);
		return graella[fila][columna].isEditable();
	}

	public void canvis() throws Exception {
		new Equivalent(this.graella);
	}
}
