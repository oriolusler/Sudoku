package Presentacio;

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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;

import Aplicacio.Control;
import Aplicacio.ControlBBDD;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener {

	private JMenu menuPartida, menuPersistencia, recuperarPArtides;
	private JMenu submenuPartida;
	private JMenuItem Sudoku0, SudokiIniciat, GuardarSudokuNou,
			GenerarSudokuAleatori, CrearSudokuUsuari;
	private JLabel nomJugadorActual = new JLabel("");
	private Presentacio pr;
	private Control cr;
	private ControlBBDD crb;

	private JRadioButton[] fontButtons;
	private Timestamp[] recuperats;

	private String nom;

	public MenuBar(Presentacio p, Control c, final ControlBBDD cb) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex1) {
		}

		this.pr = p;
		this.cr = c;
		this.crb = cb;
		this.nom = p.getNom();
		this.recuperats = p.getRecuperats();

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

		this.add(menuPartida);
		this.add(menuPersistencia);
		this.add(recuperarPArtides);
		this.add(nomJugadorActual);
		this.add(CrearSudokuUsuari);

		if ((!cr.estaBuit()))
			CrearSudokuUsuari.setVisible(false);

		if (nom.equals("Anonim")) {
			menuPersistencia.setEnabled(false);
			recuperarPArtides.setEnabled(false);
		} else {
			nomJugadorActual.setText("Jugador: " + nom);

		}

		recuperarPArtides.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				mostratpartidesrecuperades();

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

		CrearSudokuUsuari.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pr.getNumeroEntrades() >= 17)
					try {
						pr.elimanar();
						cr.iniciarUsuari();
						pr.actualitzar();
						CrearSudokuUsuari.setVisible(false);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(new JFrame(),
								ex.getMessage());
					}
				else {
					JOptionPane.showMessageDialog(new JFrame(),
							"No es posible crear el taulell, has de tenir"
									+ " 17 o mes numeros introduits"
									+ "\n(Quantitat de numeros introduits : "
									+ pr.getNumeroEntrades() + ")");
				}
			}
		});

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

				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"Instruccions:\n - Un cop introduit un numero, fer clic ENTER per confirmar"
										+ "\n - En cas d'introduir un 0, la casella no tindra cap valor"
										+ "\n - Per finalitzar la creacio premeu 'Iniciar Sudoku' en el menu");

				CrearSudokuUsuari.setVisible(true);
				pr.elimanar();
				try {
					cr.resetejarCasella();
					pr.actualitzar();
					if (crb != null)
						crb.iniciarSudoku();
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
					if (crb != null)
						crb.iniciarSudoku();

				} catch (Exception error) {
					error.getStackTrace();
				}

			}
		});

		GuardarSudokuNou.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int res = JOptionPane.showConfirmDialog(new JFrame(),
							"Vols guardar el sudoku?\n", "TRIA",
							JOptionPane.YES_NO_OPTION,
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

	private void mostratpartidesrecuperades() {
		// Preguntar si vols guardar partida

		if (!(nom.equals("Anonim"))) {

			recuperarPArtides.removeAll();
			recuperats = pr.getRecuperats();
			if (recuperats != null) {
				fontButtons = new JRadioButton[recuperats.length];
				ButtonGroup fontGroup = new ButtonGroup();

				for (int i = 0; i < recuperats.length; i++) {
					fontButtons[i] = new JRadioButton();
					fontButtons[i].setText(recuperats[i].toString());
					fontButtons[i].addActionListener(this);
					fontGroup.add(fontButtons[i]);
					recuperarPArtides.add(fontButtons[i]);
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