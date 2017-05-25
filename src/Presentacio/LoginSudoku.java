package Presentacio;

import java.awt.Color;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Aplicacio.ControlBBDD;

public class LoginSudoku {

	private JLabel result_label = new JLabel("Introdueix el nom d'usuari per jugar");
	private JLabel label_login = new JLabel("Usuari:");
	private JTextField login = new JTextField();
	private ControlBBDD controlBBDD;
	private String nom;

	public LoginSudoku() {

		DemanarCredencials();
		new Presentacio(controlBBDD);

	}

	public LoginSudoku(String nom) {

		controlBBDD = new ControlBBDD(nom);
		new Presentacio(controlBBDD);

	}

	private void DemanarCredencials() {

		Object[] array = { result_label, label_login, login };

		int preguntaNomUsuari = JOptionPane.showConfirmDialog(null, array, "User Login", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (preguntaNomUsuari == JOptionPane.OK_OPTION) {

			try {
				nom = login.getText();
				Map<Integer, Date> recuperats = null;

				controlBBDD = new ControlBBDD(nom);

				controlBBDD.nouJugador(nom);

				controlBBDD.iniciarSudoku();
				recuperats = controlBBDD.getPartidesRecuperades();

				if (recuperats.size() == 0) {
					controlBBDD.iniciarSudoku();

				} else {

					// RECUPERACIO SUDOKU
					int preguntaSiVolJugarSudokuGuardat = JOptionPane.showConfirmDialog(new JFrame(),
							"Vols jugar un sudoku guardat?\nEn cas contrari comencaras un sudoku nou.", "TRIA",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (preguntaSiVolJugarSudokuGuardat == JOptionPane.YES_OPTION) {

						Set<Integer> IDSfromMAP = recuperats.keySet();
						Collection<Date> DATAfromMAP = recuperats.values();

						Integer[] IdSudokusRecuperats = (Integer[]) (IDSfromMAP
								.toArray(new Integer[IDSfromMAP.size()]));
						Date[] DatesRecuperades = (Date[]) (DATAfromMAP.toArray(new Date[IDSfromMAP.size()]));
						String[] stringPerMostrat = new String[recuperats.size()];

						for (int i = 0; i < recuperats.size(); i++) {
							stringPerMostrat[i] = IdSudokusRecuperats[i] + " - " + DatesRecuperades[i];
						}

						if (recuperats.size() == 1) {
							controlBBDD.getSudoku().setIdSudoku(IdSudokusRecuperats[0]);
							controlBBDD.setInciar(true);
						} else {
							String input = (String) JOptionPane.showInputDialog(null, "Quin sudoku vols recuperar?",
									"Eleccio sudoku", JOptionPane.QUESTION_MESSAGE, null, stringPerMostrat,
									stringPerMostrat);

							String[] parts = input.split(" - ");
							controlBBDD.getSudoku().setIdSudoku(Integer.parseInt(parts[0]));
							controlBBDD.setInciar(true);

						}
					} else if (preguntaSiVolJugarSudokuGuardat == JOptionPane.NO_OPTION) {

						controlBBDD.iniciarSudoku();

					} else {
						controlBBDD.setEstatJuagdor();
						System.exit(0);
					}
				}

				// FI DE RECUPERACIO

			} catch (Exception e) {
				result_label.setText("Usuari incorrecte.");
				result_label.setForeground(Color.RED);
				login.setText("");
			}

		} else
			System.exit(0);

	}

}
