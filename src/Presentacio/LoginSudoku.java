package Presentacio;

import Aplicacio.ControlBBDD;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Map;

public class LoginSudoku {

	private JLabel result_label = new JLabel("Introdueix el nom d'usuari per jugar");
	private JLabel label_login = new JLabel("Usuari:");
	private JTextField login = new JTextField();

	private String nom;
	private boolean iniciat = false;

	public LoginSudoku(ControlBBDD controlBBDD) {

		boolean logat = demanarCredencials(controlBBDD);
		if (logat)
			new Presentacio(controlBBDD, iniciat);

	}

	private boolean demanarCredencials(ControlBBDD controlBBDD) {

		boolean logat = false;
		while (!logat) {

			Object[] array = { result_label, label_login, login };

			int preguntaNomUsuari = JOptionPane.showConfirmDialog(null, array, "User Login",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (preguntaNomUsuari == JOptionPane.CANCEL_OPTION || preguntaNomUsuari == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			} else if (preguntaNomUsuari == JOptionPane.OK_OPTION) {

				nom = login.getText();

				if (nom.equals("")) {
					result_label.setText("El nom d'usuari no pot ser buit.\nEscriu un nom:");
					result_label.setForeground(Color.RED);

				} else {
					Map<Integer, Date> recuperats = null;
					controlBBDD.setJugadorNom(nom);
					try {
						controlBBDD.nouJugador();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(new JFrame(), e.getMessage());

					}

					recuperats = controlBBDD.getPartidesRecuperades();

					// Si no hi han aprtides començades...
					if (recuperats == null || recuperats.size() == 0) {
						controlBBDD.iniciarSudoku();

					} else {

						// RECUPERACIO SUDOKU
						int preguntaSiVolJugarSudokuGuardat = JOptionPane.showConfirmDialog(new JFrame(),
								"Vols jugar un sudoku guardat?\nEn cas contrari comencaras un sudoku nou.", "TRIA",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (preguntaSiVolJugarSudokuGuardat == JOptionPane.YES_OPTION) {

							new EleccioPartida(controlBBDD, recuperats);
							return false;

							// si diu q no vol jugar una partida guarda
						} else if (preguntaSiVolJugarSudokuGuardat == JOptionPane.NO_OPTION) {

							controlBBDD.iniciarSudoku();

							// si el la pantalla si vols jugar un sudoku
							// guardat tanca
						} else {
							try {
								controlBBDD.setEstatJuagdor();
								System.exit(0);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
								System.exit(0);
							}
						}
					}

					// FI DE RECUPERACIO

					logat = true;
					return true;
				}
			}
		}
		return false;
	}
}
