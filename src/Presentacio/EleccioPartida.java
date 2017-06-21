package Presentacio;

import java.awt.HeadlessException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Aplicacio.ControlBBDD;

public class EleccioPartida {

	private ControlBBDD controlBBDD;

	public EleccioPartida(ControlBBDD controlBBDD) {
		this.controlBBDD = controlBBDD;
		iniciarEleccio();

	}

	private void iniciarEleccio() {

		String[] partidesRecuperades = controlBBDD.getIdAndDateFromDataBase();

		// Si nomes hi ha una partida...

		try {
			if (partidesRecuperades.length == 1) {
				String[] parts = partidesRecuperades[0].split(" - ");

				controlBBDD.setIdSudoku(Integer.parseInt(parts[0]));
				controlBBDD.setTimeStampSudoku((parts[1]));

			}
			// Si hi ha mes d'una...
			else {
				String input = (String) JOptionPane.showInputDialog(null, "Quin sudoku vols recuperar?",
						"Eleccio sudoku", JOptionPane.QUESTION_MESSAGE, null, partidesRecuperades, partidesRecuperades);

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
					controlBBDD.setTimeStampSudoku(parts[1]);
				}

			}

			new Presentacio(controlBBDD, true);
		} catch (NumberFormatException | HeadlessException | ParseException e) {
			JOptionPane.showMessageDialog(new JFrame(), "EleccioPartida/iniciarEleccio" + e.getMessage());
		}

	}
}
