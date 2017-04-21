package Aplicacio;

import Domini.Taulell;
import Prova.GraellaInicial;

public abstract class CrearGraella {

	public static void crearGraella(Taulell t) throws Exception {
		GraellaInicial gi = new GraellaInicial();
		gi.iniciarGraella(t);

	}

	public abstract void iniciarGraella(Taulell t) throws Exception;

}