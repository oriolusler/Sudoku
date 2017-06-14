package Presentacio;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Aplicacio.ControlBBDD;

public class EleccioPartida {

	public EleccioPartida(ControlBBDD controlBBDD, Map<Integer, Date> recuperats) {
		iniciarEleccio(controlBBDD, recuperats);

	}

	private void iniciarEleccio(ControlBBDD controlBBDD, Map<Integer, Date> recuperats) {
		Set<Integer> IDSfromMAP = recuperats.keySet();
		Collection<Date> DATAfromMAP = recuperats.values();

		Integer[] IdSudokusRecuperats = (Integer[]) (IDSfromMAP.toArray(new Integer[IDSfromMAP.size()]));
		Date[] DatesRecuperades = (Date[]) (DATAfromMAP.toArray(new Date[IDSfromMAP.size()]));
		String[] stringPerMostrat = new String[recuperats.size()];

		for (int i = 0; i < recuperats.size(); i++) {
			stringPerMostrat[i] = IdSudokusRecuperats[i] + " - " + DatesRecuperades[i];
		}

		// Si nomes hi ha una partida...
		if (recuperats.size() == 1) {
			controlBBDD.setIdSudoku(IdSudokusRecuperats[0]);
			controlBBDD.setTimeStampSudoku(DatesRecuperades[0]);

		}
		// Si hi ha mes d'una...
		else {
			String input = (String) JOptionPane.showInputDialog(null, "Quin sudoku vols recuperar?", "Eleccio sudoku",
					JOptionPane.QUESTION_MESSAGE, null, stringPerMostrat, stringPerMostrat);

			// tanca pantalla i programa
			if (input == null) {
				try {
					controlBBDD.setEstatJuagdor();
					System.exit(0);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
				}

				// escull ellecio
			} else {
				String[] parts = input.split(" - ");
				controlBBDD.setIdSudoku(Integer.parseInt(parts[0]));
				controlBBDD.setTimeStampSudoku(DatesRecuperades[Integer.parseInt(parts[0]) - 1]);
			}

		}

		new Presentacio(controlBBDD, true);
	}
}
