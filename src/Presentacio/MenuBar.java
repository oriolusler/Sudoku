package Presentacio;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import Aplicacio.Control;
import Aplicacio.ControlBBDD;

public class MenuBar extends JMenuBar implements ActionListener {

	JMenu menuPartida, menuPersistencia, recuperarPArtides;
	JMenu submenuPartida, submenuPersistencia;
	JMenuItem Sudoku0, SudokiIniciat, GuardarSudokuNou, GenerarSudokuAleatori;
	JLabel nomJugadorActual = new JLabel("");
	Presentacio pr;
	Control cr;
	ControlBBDD crb;

	private Timestamp[] recuperats;

	private String nom;

	public MenuBar(Presentacio p, Control c, final ControlBBDD cb) {

		this.pr = p;
		this.cr = c;
		this.crb = cb;
		this.nom = p.getNom();
		this.recuperats = p.getRecuperats();

		// ////////////////////// MENU PARTIDA ////////////////////////
		menuPartida = new JMenu("Partida");
		// SUBMENU PARTIDA
		submenuPartida = new JMenu("Crear sudoku: ");

		Sudoku0 = new JMenuItem("Buit");
		submenuPartida.add(Sudoku0);

		SudokiIniciat = new JMenuItem("Estandard");
		submenuPartida.add(SudokiIniciat);
		menuPartida.add(submenuPartida);

		// FI SUBMENU
		menuPartida.addSeparator();
		GenerarSudokuAleatori = new JMenuItem("Sudoku Aleatori");
		menuPartida.add(GenerarSudokuAleatori);
		// FI MENU PARTIDA

		// ////////////////////// MENU GUARDAR ////////////////////////
		menuPersistencia = new JMenu("Persistencia");

		GuardarSudokuNou = new JMenuItem("Guardar");
		menuPersistencia.add(GuardarSudokuNou);

		// FI SUBMENU

		// ///////////////////// RECUPERAR PARTIDES MENU ///////////////////////

		recuperarPArtides = new JMenu("Recuperar partides");

		this.add(menuPartida);
		this.add(menuPersistencia);
		this.add(recuperarPArtides);
		this.add(nomJugadorActual);

		if (nom.equals("Anonim")) {
			menuPersistencia.setEnabled(false);
			recuperarPArtides.setEnabled(false);
		} else {
			nomJugadorActual.setText("Jugador: " + nom);
			JRadioButton[] fontButtons = new JRadioButton[recuperats.length];
			ButtonGroup fontGroup = new ButtonGroup();

			for (int i = 0; i < recuperats.length; i++) {
				fontButtons[i] = new JRadioButton();
				fontButtons[i].setText(recuperats[i].toString());
				fontButtons[i].addActionListener(this);
				fontGroup.add(fontButtons[i]);
				recuperarPArtides.add(fontButtons[i]);
			}

		}

		GenerarSudokuAleatori.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pr.elimanar();
				try {
					int res = JOptionPane.showConfirmDialog(new JFrame(),
							"Perdras el sudoku actual, vols continuar?",
							"ALERTA", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {
						cr.canviarTaulell();
						if (crb != null)
							crb.iniciarSudoku();
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

				if (!(nom.equals("Anonim"))) {
					pr.setBotonsShow(true);
					pr.elimanar();
					try {
						cr.resetejarCasella();
						pr.actualitzar();
						crb.iniciarSudoku();
					} catch (Exception error) {
						error.printStackTrace();
					}
				}
			}
		});

		SudokiIniciat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!(nom.equals("Anonim"))) {
					pr.setBotonsShow(false);
					pr.elimanar();
					try {
						cr.resetejarCasella();
						cr.inciarCaselles();
						pr.actualitzar();
						crb.iniciarSudoku();

					} catch (Exception error) {
						error.getStackTrace();
					}

				}
			}
		});

		GuardarSudokuNou.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int res = JOptionPane
							.showConfirmDialog(
									new JFrame(),
									"Vols guardar el sudoku?\n - En cas contrari es finalitzarà el joc.",
									"TRIA", JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {
						try {
							if (crb.sudokuBuit())
								crb.storeSudoku();

							if (!(crb.taulellBuit()))
								crb.actualitzarBBDD(cr.getTTaulell());
							else
								crb.storeTaulell(cr.getTTaulell());
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				} catch (HeadlessException e1) {

				}

			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		Timestamp a = Timestamp.valueOf(((JRadioButton) e.getSource())
				.getText());
		try {
			cr.resetejarCasella();
			crb.setSudokuID(crb.getIdFromTimeStamp(a));
			pr.mostratSudokuRecuperat();
			pr.actualitzar();
		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}
}