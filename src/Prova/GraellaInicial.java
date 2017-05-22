package Prova;

import Aplicacio.CrearGraella;
import Domini.Casella;

public class GraellaInicial extends CrearGraella {

	@Override
	public void iniciarGraella(Casella[][] t) throws Exception {
		t[0][0].setCasella(5);
		t[4][3].setCasella(8);
		t[8][7].setCasella(7);
		t[0][4].setCasella(7);
		t[4][8].setCasella(1);
		t[0][1].setCasella(3);
		t[1][3].setCasella(1);
		t[5][4].setCasella(2);
		t[1][0].setCasella(6);
		t[1][5].setCasella(5);
		t[6][1].setCasella(6);
		t[1][4].setCasella(9);
		t[2][2].setCasella(8);
		t[6][7].setCasella(8);
		t[2][1].setCasella(9);
		t[3][0].setCasella(8);
		t[7][4].setCasella(1);
		t[2][7].setCasella(6);
		t[3][8].setCasella(3);
		t[7][8].setCasella(5);
		t[3][4].setCasella(6);
		t[4][0].setCasella(4);
		t[4][5].setCasella(3);
		t[5][0].setCasella(7);
		t[5][8].setCasella(6);
		t[6][6].setCasella(2);
		t[7][3].setCasella(4);
		t[7][5].setCasella(9);
		t[8][4].setCasella(8);
		t[8][8].setCasella(9);

	}
}
