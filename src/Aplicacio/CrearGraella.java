package Aplicacio;

import Domini.Taulell;
import Prova.GraellaInicial;

public abstract class CrearGraella {

	public static void crearGraella(Taulell taulell) throws Exception {
		GraellaInicial gi = new GraellaInicial();
		gi.iniciarGraella(taulell);
	}
	public abstract void iniciarGraella(Taulell t) throws Exception;

}