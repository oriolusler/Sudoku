package Presentacio;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Aplicacio.Control;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener {

	private JMenu menuPartida, menuPersistencia, recuperarPArtides;
	private JMenu submenuPartida;
	private JMenuItem Sudoku0, SudokiIniciat, GuardarSudokuNou, GenerarSudokuAleatori, CrearSudokuUsuari;
	private JLabel nomJugadorActual = new JLabel("");
	private Presentacio pr;
	private Control cr;

	private JRadioButton[] fontButtons;
	private Map<Integer, Date> recuperats;

	private String nom;
	private String nomJugadorPerJLbael;

	public MenuBar(Presentacio p, Control c) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex1) {
		}

		this.pr = p;
		this.cr = c;
		this.nom = cr.getNomJugador();

		CrearSudokuUsuari = new JMenuItem(" Iniciar Sudoku");

		// ////////////////////// MENU PARTIDA ////////////////////////

		menuPartida = new JMenu("Partida");
		// SUBMENU PARTIDA //
		submenuPartida = new JMenu("Crear sudoku: ");

		Sudoku0 = new JMenuItem("Buit");
		submenuPartida.add(Sudoku0);

		SudokiIniciat = new JMenuItem("Estandard");
		submenuPartida.add(SudokiIniciat);
		menuPartida.add(submenuPartida);

		// FI SUBMENU //
		menuPartida.addSeparator();
		GenerarSudokuAleatori = new JMenuItem("Sudoku Aleatori");
		menuPartida.add(GenerarSudokuAleatori);
		// FI MENU PARTIDA

		// ////////////////////// MENU GUARDAR ////////////////////////

		menuPersistencia = new JMenu("Persistencia");

		GuardarSudokuNou = new JMenuItem("Guardar");
		menuPersistencia.add(GuardarSudokuNou);

		// ///////////////////// RECUPERAR PARTIDES MENU ///////////////////////

		recuperarPArtides = new JMenu("Recuperar partides");

		nomJugadorPerJLbael = "Jugador: " + nom;
		this.add(menuPartida);
		this.add(menuPersistencia);
		this.add(recuperarPArtides);
		this.add(nomJugadorActual);
		this.add(CrearSudokuUsuari);

		if ((!cr.estaBuit()))
			CrearSudokuUsuari.setVisible(false);

		if (cr.getNomJugador().equals("Anonim")) {
			menuPersistencia.setEnabled(false);
			recuperarPArtides.setEnabled(false);
		} else {
			nomJugadorActual.setText(nomJugadorPerJLbael + " ID: " + getIdSudokuActual());

		}

		menuPersistencia.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				if (!(pr.getHiHaAlgunaCasellaModificada())) {
					GuardarSudokuNou.setEnabled(false);
				} else {
					GuardarSudokuNou.setEnabled(true);
				}

			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub

			}
		});

		recuperarPArtides.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				try {
					mostratpartidesrecuperades();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});

		CrearSudokuUsuari.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pr.getNumeroEntrades() >= 17)
					try {
						pr.elimanar();
						cr.iniciarUsuari();
						pr.actualitzar();
						actualitzarNom();
						CrearSudokuUsuari.setVisible(false);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
					}
				else {
					JOptionPane.showMessageDialog(new JFrame(),
							"No es posible crear el taulell, has de tenir" + " 17 o mes numeros introduits"
									+ "\n(Quantitat de numeros introduits : " + pr.getNumeroEntrades() + ")");
				}
			}
		});

		GenerarSudokuAleatori.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pr.elimanar();
				try {
					int res = JOptionPane.showConfirmDialog(new JFrame(), "Perdras el sudoku actual, vols continuar?",
							"ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {
						cr.canviarTaulell();
						if (!(nom.equals("Anonim"))) {
							cr.iniciarSudoku();
							actualitzarNom();
						}
						pr.actualitzar();

					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
				}

			}
		});

		Sudoku0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(new JFrame(),
						"Instruccions:\n - Un cop introduit un numero, fer clic ENTER per confirmar"
								+ "\n - En cas d'introduir un 0, la casella no tindra cap valor"
								+ "\n - Per finalitzar la creacio premeu 'Iniciar Sudoku' en el menu");

				CrearSudokuUsuari.setVisible(true);
				pr.elimanar();
				try {
					cr.resetejarCasella();
					pr.actualitzar();
					if (!(nom.equals("Anonim"))) {
						cr.iniciarSudoku();
						actualitzarNom();
					}
				} catch (Exception error) {
					error.printStackTrace();
				}

			}
		});

		SudokiIniciat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				CrearSudokuUsuari.setVisible(false);
				pr.elimanar();
				try {
					cr.resetejarCasella();
					cr.inciarCaselles();
					pr.actualitzar();

					if (!(nom.equals("Anonim"))) {
						cr.iniciarSudoku();
						actualitzarNom();
					}
				} catch (Exception error) {
					error.getStackTrace();
				}

			}
		});

		GuardarSudokuNou.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cr.setTaulell(cr.getTaulellT());
				try {
					int res = JOptionPane.showConfirmDialog(new JFrame(), "Vols guardar el sudoku?\n", "TRIA",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {

						guardarSudoku();
						pr.setHiHaAlgunaCasellaModificada(false);
					}
				} catch (HeadlessException e1) {

				}

			}
		});

	}

	private String getIdSudokuActual() {
		return String.valueOf(cr.getSudokuID());
	}

	private void actualitzarNom() {
		nomJugadorActual.setText(nomJugadorPerJLbael + " ID: " + getIdSudokuActual());
	}

	private void guardarSudoku() {
		try {
			if (cr.sudokuBuit())
				cr.storeSudoku();

			if (!(cr.taulellBuit()))
				cr.actualitzarBBDD();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (pr.getHiHaAlgunaCasellaModificada()) {
			int res = JOptionPane.showConfirmDialog(new JFrame(), "Vols guardar el sudoku abans de canviar-lo?", "TRIA",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (res == JOptionPane.YES_OPTION) {
				cr.setTaulell(cr.getTaulellT());
				guardarSudoku();
				pr.setHiHaAlgunaCasellaModificada(false);
			}
		}
		String agafat = ((((JRadioButton) e.getSource()).getText()));
		String[] parts = agafat.split(" - ");
		try {
			cr.resetejarCasella();
			cr.setSudokuID(Integer.parseInt(parts[0]));
			pr.mostratSudokuRecuperat();
			pr.actualitzar();
			actualitzarNom();
		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	private void mostratpartidesrecuperades() throws Exception {
		// Preguntar si vols guardar partida

		int quinEstaSelecionat = cr.getSudokuID();

		if (!(nom.equals("Anonim"))) {
			recuperarPArtides.removeAll();
			recuperats = cr.getTimeStamps();

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
				try {
					fontButtons[quinEstaSelecionat - 1].setSelected(true);
				} catch (ArrayIndexOutOfBoundsException e) {

				}
			}
		}
	}

	public void setControl(Control nouControl) {
		this.cr = nouControl;
		if ((!cr.estaBuit()))
			CrearSudokuUsuari.setVisible(false);
	}
}