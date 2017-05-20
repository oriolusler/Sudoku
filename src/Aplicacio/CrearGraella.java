package Aplicacio;

import Domini.Casella;
import Prova.GraellaInicial;

public abstract class CrearGraella {

	public static void crearGraella(Casella[][] t) throws Exception {
		GraellaInicial gi = new GraellaInicial();
		gi.iniciarGraella(t);

	}

	public abstract void iniciarGraella(Casella[][] t) throws Exception;

}