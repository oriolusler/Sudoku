package Presentacio;

import Aplicacio.Control;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener {

	private JMenu menuPartida, menuPersistencia, recuperarPArtides;
	private JMenu submenuPartida;
	private JMenuItem sudokuBuit, sudokuEstandard, guardarSudoku, sudokuAleatori, crearSudokuBuit;
	private JLabel nomJugadorActual = new JLabel("");
	private Presentacio presentacioClasse;
	private Control controlClasse;
	private JRadioButton[] fontButtons;
	private Map<Integer, Date> recuperats;
	private String nom;
	private String nomJugadorPerJLbael;

	public MenuBar(Presentacio presentacio, Control control) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex1) {
			JOptionPane.showMessageDialog(new JFrame(), "MenuBar/Line36\n" + ex1.getMessage());
		}

		this.presentacioClasse = presentacio;
		this.controlClasse = control;
		this.nom = controlClasse.getNomJugador();

		crearSudokuBuit = new JMenuItem(" Iniciar Sudoku");

		// ////////////////////// MENU PARTIDA ////////////////////////

		menuPartida = new JMenu("Partida");

		// SUBMENU PARTIDA //

		submenuPartida = new JMenu("Crear sudoku: ");

		sudokuBuit = new JMenuItem("Buit");
		submenuPartida.add(sudokuBuit);

		sudokuEstandard = new JMenuItem("Estandard");
		submenuPartida.add(sudokuEstandard);
		menuPartida.add(submenuPartida);

		// FI SUBMENU //

		menuPartida.addSeparator();
		sudokuAleatori = new JMenuItem("Sudoku Aleatori");
		menuPartida.add(sudokuAleatori);

		// FI MENU PARTIDA

		// ////////////////////// MENU GUARDAR ////////////////////////

		menuPersistencia = new JMenu("Persistencia");

		guardarSudoku = new JMenuItem("Guardar");
		menuPersistencia.add(guardarSudoku);

		// ///////////////////// RECUPERAR PARTIDES MENU ///////////////////////

		recuperarPArtides = new JMenu("Recuperar partides");

		nomJugadorPerJLbael = "Jugador: " + nom;

		this.add(menuPartida);
		this.add(menuPersistencia);
		this.add(recuperarPArtides);
		this.add(nomJugadorActual);
		this.add(crearSudokuBuit);

		if ((!controlClasse.estaBuit()))
			crearSudokuBuit.setVisible(false);
		else {
			setMenuPersistenica(false);
			setSudokuAleatori(false);
		}

		if (controlClasse.getNomJugador().equals("Anonim")) {
			setMenuPersistenica(false);
			recuperarPArtides.setEnabled(false);
		} else {
			actualitzarNom();
		}
		recuperarPArtides.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				mostratpartidesrecuperades();

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});

		crearSudokuBuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (presentacioClasse.getNumeroEntrades() >= 17)
					try {
						setSudokuAleatori(true);
						controlClasse.iniciarUsuari();
						presentacioClasse.actualitzar();
						actualitzarNom();
						crearSudokuBuit.setVisible(false);
						if (!(nom.equals("Anonim")))
							setMenuPersistenica(true);
					} catch (Exception eCrearSudokuUsuari) {
						JOptionPane.showMessageDialog(new JFrame(),
								"MenuBar/CrearSudokuUsuari\n" + eCrearSudokuUsuari.getMessage());
					}
				else {
					setMenuPersistenica(false);
					JOptionPane.showMessageDialog(new JFrame(),
							"No es posible crear el taulell, has de tenir" + " 17 o mes numeros introduits"
									+ "\n(Quantitat de numeros introduits : " + presentacioClasse.getNumeroEntrades()
									+ ")");
				}
			}
		});

		sudokuAleatori.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				presentacioClasse.setNumeroEntraddes(0);
				presentacioClasse.elimanar();
				try {
					int res = JOptionPane.showConfirmDialog(new JFrame(), "Perdras el sudoku actual, vols continuar?",
							"ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {
						controlClasse.canviarTaulell();
						if (!(nom.equals("Anonim"))) {
							// intentarIntentarSudoku();
							actualitzarNom();
							setMenuPersistenica(true);
						}
						presentacioClasse.actualitzar();

					}
				} catch (Exception eSudokuAleatori) {
					JOptionPane.showMessageDialog(new JFrame(),
							"MenuBar/GenerarSudokuAleatori\n" + eSudokuAleatori.getMessage());
				}

			}
		});

		sudokuBuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(new JFrame(),
						"Instruccions:\n - Un cop introduit un numero, fer clic ENTER per confirmar"
								+ "\n - En cas d'introduir un 0, la casella no tindra cap valor"
								+ "\n - Per finalitzar la creacio premeu 'Iniciar Sudoku' en el menu");

				setSudokuAleatori(false);
				setMenuPersistenica(false);
				presentacioClasse.setNumeroEntraddes(0);
				crearSudokuBuit.setVisible(true);
				presentacioClasse.elimanar();
				try {
					controlClasse.inciarSudokuBuit();
					presentacioClasse.actualitzar();

					if (!(nom.equals("Anonim"))) {
						intentarIntentarSudoku();
						actualitzarNom();
					}
				} catch (Exception eSudoku0) {
					JOptionPane.showMessageDialog(new JFrame(), "MenuBar/Sudoku0\n" + eSudoku0.getMessage());
				}

			}
		});

		sudokuEstandard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSudokuAleatori(true);
				crearSudokuBuit.setVisible(false);
				presentacioClasse.elimanar();
				try {
					controlClasse.inciarSudokuBuit();
					controlClasse.iniciarSudokuPredefinit();
					presentacioClasse.actualitzar();
					if (!(nom.equals("Anonim"))) {
						intentarIntentarSudoku();
						actualitzarNom();
						setMenuPersistenica(true);
					}
				} catch (Exception eSudokuiniciar) {
					JOptionPane.showMessageDialog(new JFrame(),
							"MenuBar/SudokiIniciat\n" + eSudokuiniciar.getMessage());
				}

			}
		});

		guardarSudoku.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(new JFrame(), "Vols guardar el sudoku?\n", "TRIA",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (res == JOptionPane.YES_OPTION) {
					try {

						controlClasse.storeSudoku();
						actualitzarNom();
					} catch (Exception eGuardarSudoku) {
						JOptionPane.showMessageDialog(new JFrame(),
								"MenuBar/GuardarSudokuNou\n" + eGuardarSudoku.getMessage());
					}
				}
			}
		});

	}

	private String getIdSudokuActual() {
		return String.valueOf(controlClasse.getIdSudoku());
	}

	private void actualitzarNom() {
		if (controlClasse.getIdSudoku() == -1)
			nomJugadorActual.setText(nomJugadorPerJLbael + " Sudoku no persistit");
		else
			nomJugadorActual.setText(nomJugadorPerJLbael + " ID: " + getIdSudokuActual());
	}

	public void actionPerformed(ActionEvent e) {

		int res = JOptionPane.showConfirmDialog(new JFrame(), "Vols guardar el sudoku abans de canviar-lo?", "TRIA",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (res == JOptionPane.YES_OPTION) {

			try {
				controlClasse.storeSudoku();
			} catch (Exception eActionPerfomedStore) {
				JOptionPane.showMessageDialog(new JFrame(),
						"MenuBar/actualitzarNom\n" + eActionPerfomedStore.getMessage());
			}
		}

		String agafat = ((((JRadioButton) e.getSource()).getText()));
		String[] parts = agafat.split(" - ");
		try {
			controlClasse.inciarSudokuBuit();
			controlClasse.setIdSudoku(Integer.parseInt(parts[0]));
			presentacioClasse.mostratSudokuRecuperat();
			presentacioClasse.actualitzar();
			actualitzarNom();
		} catch (Exception eActionPerfomed280) {

			JOptionPane.showMessageDialog(new JFrame(), "MenuBar/actionPerformed\n" + eActionPerfomed280.getMessage());
		}

	}

	private void mostratpartidesrecuperades() {

		menuPersistencia.setEnabled(true);
		int quinEstaSelecionat = controlClasse.getIdSudoku();

		if (!(nom.equals("Anonim"))) {
			recuperarPArtides.removeAll();
			recuperats = controlClasse.getPartidesRecuperades();

			if (recuperats != null) {
				fontButtons = new JRadioButton[recuperats.size()];
				ButtonGroup fontGroup = new ButtonGroup();

				int i = 0;
				for (Entry<Integer, Date> entry : recuperats.entrySet()) {

					fontButtons[i] = new JRadioButton();
					fontButtons[i].setText(entry.getKey() + " - " + entry.getValue());
					fontButtons[i].addActionListener(this);
					fontGroup.add(fontButtons[i]);
					recuperarPArtides.add(fontButtons[i]);
					i++;
				}

				if (controlClasse.getIdSudoku() != -1)
					fontButtons[quinEstaSelecionat - 1].setSelected(true);

			}
		}
	}

	public void setMenuPersistenica(boolean estat) {
		menuPersistencia.setEnabled(estat);
	}

	public void setSudokuAleatori(boolean estat) {
		sudokuAleatori.setEnabled(estat);
	}

	public void setControlClasse(Control control) {
		this.controlClasse = control;

	}

	private void intentarIntentarSudoku() {
		controlClasse.iniciarSudoku();

	}
}