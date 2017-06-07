package Presentacio;

import Aplicacio.ControlBBDD;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class LoginSudoku {

	private JLabel result_label = new JLabel("Introdueix el nom d'usuari per jugar");
	private JLabel label_login = new JLabel("Usuari:");
	private JTextField login = new JTextField();

	private String nom;
	private boolean iniciat = false;

	public LoginSudoku(String nom, ControlBBDD controlBBDD) {

		if (nom == null) {
			boolean logat = DemanarCredencials(controlBBDD);
			if (logat)
				new Presentacio(controlBBDD, iniciat);
		} else {
			controlBBDD = new ControlBBDD(nom);
			new Presentacio(controlBBDD, iniciat);
		}
	}

	private boolean DemanarCredencials(ControlBBDD controlBBDD) {

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
						System.exit(0);
					}

					recuperats = controlBBDD.getPartidesRecuperades();

					// Si no hi han aprtides començades...
					if (recuperats == null || recuperats.size() == 0) {
						try {
							intentarCrearSudoku(controlBBDD);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
							System.exit(0);
						}
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

							// Si nomes hi ha una partida...
							if (recuperats.size() == 1) {
								controlBBDD.setIdSudoku(IdSudokusRecuperats[0]);
								controlBBDD.setTimeStampSudoku(DatesRecuperades[0]);
								iniciat = true;
							}
							// Si hi ha mes d'una...
							else {
								String input = (String) JOptionPane.showInputDialog(null, "Quin sudoku vols recuperar?",
										"Eleccio sudoku", JOptionPane.QUESTION_MESSAGE, null, stringPerMostrat,
										stringPerMostrat);

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
									iniciat = true;
								}

							}

							// si diu q no vol jugar una partida guarda
						} else if (preguntaSiVolJugarSudokuGuardat == JOptionPane.NO_OPTION) {
							try {
								intentarCrearSudoku(controlBBDD);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
								System.exit(0);
							}

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

	private void intentarCrearSudoku(ControlBBDD controlBBDD) throws Exception {
		try {
			controlBBDD.iniciarSudoku();

		} catch (Exception e) {
			controlBBDD.setEstatJuagdor();
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
			System.exit(0);
		}
	}
}
