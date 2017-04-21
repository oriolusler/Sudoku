package Domini;

import java.util.LinkedList;

public class Coordenada {

	private int fila, columna;

	public Coordenada(int fila, int columna) throws IllegalArgumentException {
		this.fila = fila;
		this.columna = columna;
		if (fila < 0 || fila > 8 || columna < 0 || columna > 8)
			throw new IllegalArgumentException("Fila o Columna Incorrecta");
	}

	int getColumna() {
		return columna;
	}

	int getFila() {
		return fila;
	}

	LinkedList<Coordenada> mateixaFila() {
		LinkedList<Coordenada> files = new LinkedList<Coordenada>();
		int i = 0;
		while (i < 9) {
			if (i != this.columna) {
				files.add(new Coordenada(fila, i));
			}
			i++;
		}
		return files;
	}

	LinkedList<Coordenada> mateixaColumna() {
		LinkedList<Coordenada> columnes = new LinkedList<Coordenada>();
		int j = 0;
		while (j < 9) {
			if (j != this.fila) {
				columnes.add(new Coordenada(j, columna));
			}
			j++;
		}
		return columnes;
	}

	LinkedList<Coordenada> mateixBloc() {
		LinkedList<Coordenada> blocs = new LinkedList<Coordenada>();
		int f = fila / 3 * 3;
		int c = columna / 3 * 3;

		int i = f + 3;
		int j = c + 3;
		while (f < i) {
			while (c < j) {
				if ((f != fila && c != columna))
					blocs.add(new Coordenada(f, c));
				c++;
			}
			c = columna / 3 * 3;
			f++;
		}
		return blocs;
	}

	@Override
	public boolean equals(Object obj) {
		Coordenada altreCord;
		if (!(obj instanceof Coordenada))
			return false;
		altreCord = (Coordenada) obj;
		return (this.fila == altreCord.fila && this.columna == altreCord.columna);
	}
}