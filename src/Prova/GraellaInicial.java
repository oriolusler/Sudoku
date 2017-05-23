package Prova;

import Aplicacio.CrearGraella;
import Domini.Casella;
import Domini.Taulell;

public class GraellaInicial extends CrearGraella {

	@Override
	public void iniciarGraella(Taulell t) throws Exception {
		t.setCasella(0, 0, 5);
		t.setCasella(4, 3, 8);
		t.setCasella(8, 7, 7);
		t.setCasella(0, 4, 7);
		t.setCasella(4, 8, 1);
		t.setCasella(0, 1, 3);
		t.setCasella(1, 3, 1);
		t.setCasella(5, 4, 2);
		t.setCasella(1, 0, 6);
		t.setCasella(1, 5, 5);
		t.setCasella(6, 1, 6);
		t.setCasella(1, 4, 9);
		t.setCasella(2, 2, 8);
		t.setCasella(6, 7, 8);
		t.setCasella(2, 1, 9);
		t.setCasella(3, 0, 8);
		t.setCasella(7, 4, 1);
		t.setCasella(2, 7, 6);
		t.setCasella(3, 8, 3);
		t.setCasella(7, 8, 5);
		t.setCasella(3, 4, 6);
		t.setCasella(4, 0, 4);
		t.setCasella(4, 5, 3);
		t.setCasella(5, 0, 7);
		t.setCasella(5, 8, 6);
		t.setCasella(6, 6, 2);
		t.setCasella(7, 3, 4);
		t.setCasella(7, 5, 9);
		t.setCasella(8, 4, 8);
		t.setCasella(8, 8, 9);

	}
}
