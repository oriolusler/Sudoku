package Domini;

import java.util.Random;

class Equivalent {

	private int[][] intercanvis = { { 0, 0, 1, 3, 3, 4, 6, 6, 7 }, { 1, 2, 2, 4, 5, 5, 7, 8, 8 } };
	private Casella[][] graella;

	Equivalent(Casella[][] graella) throws Exception {
		this.graella = graella;
		esboorarCasellesJugador();

		int canvis[] = new int[numRandom(1, 6)];
		int n;

		for (int i = 0; i < canvis.length; i++) {
			n = numRandom(1, 6);
			while (repe(canvis, n)) {
				n = numRandom(1, 6);
			}
			canvis[i] = n;
			canviarTaulell(n);
		}
	}

	private boolean repe(int[] array, int valor) {
		for (int anArray : array)
			if (anArray == valor)
				return true;
		return false;
	}

	private void canviarTaulell(int n) throws Exception {
		switch (n) {
		case 1:
			girar();
			break;
		case 2:
			intercanviColumnes();
			break;
		case 3:
			intercanviFiles();
			break;
		case 4:
			transposar();
			break;
		case 5:
			intercanviHoritzontal();
			break;
		case 6:
			intercanviVertical();
			break;
		}
	}

	private void esboorarCasellesJugador() throws Exception {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (graella[x][y].isEditable()) {
					graella[x][y].esborrarCasella();
				}
			}
		}
	}

	private void intercanviFiles() {
		int rand = numRandom(0, 8);
		intercanviDosFiles(intercanvis[0][rand], intercanvis[1][rand]);
	}

	private void intercanviColumnes() {
		int rand = numRandom(0, 8);
		intercanviDosColumnes(intercanvis[0][rand], intercanvis[1][rand]);

	}

	private void transposar() {
		Casella[][] transposada = new Casella[9][9];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				transposada[y][x] = graella[x][y];
			}
		}
		System.arraycopy(transposada, 0, graella, 0, 9);
	}

	private void girar() {
		int n = numRandom(1, 3);
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < 4; y++) {
				intercanviDosFiles(y, 8 - y);
			}
			transposar();
		}
	}

	private void intercanviHoritzontal() throws Exception {
		int rnd = numRandom(1, 3);
		switch (rnd) {
		case 1:
			intercanviDosFiles(0, 3);
			intercanviDosFiles(1, 4);
			intercanviDosFiles(2, 5);
			break;
		case 2:
			intercanviDosFiles(0, 6);
			intercanviDosFiles(1, 7);
			intercanviDosFiles(2, 8);
			break;

		case 3:
			intercanviDosFiles(3, 6);
			intercanviDosFiles(4, 7);
			intercanviDosFiles(5, 8);
			break;
		}
	}

	private void intercanviVertical() {
		int rnd = numRandom(1, 3);
		switch (rnd) {
		case 1:
			intercanviDosColumnes(0, 3);
			intercanviDosColumnes(1, 4);
			intercanviDosColumnes(2, 5);

		case 2:
			intercanviDosColumnes(0, 6);
			intercanviDosColumnes(1, 7);
			intercanviDosColumnes(2, 8);
			break;

		case 3:
			intercanviDosColumnes(3, 6);
			intercanviDosColumnes(4, 7);
			intercanviDosColumnes(5, 8);
			break;
		}
	}

	private void intercanviDosFiles(int f1, int f2) {
		Casella copia[] = graella[f1];
		graella[f1] = graella[f2];
		graella[f2] = copia;
	}

	private void intercanviDosColumnes(int c1, int c2) {
		Casella copia;
		for (int x = 0; x < 9; x++) {
			copia = graella[x][c1];
			graella[x][c1] = graella[x][c2];
			graella[x][c2] = copia;
		}
	}

	private int numRandom(int min, int max) {
		Random rnd = new Random();
		return rnd.nextInt((max - min) + 1) + min;
	}

}
