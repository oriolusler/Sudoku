package Presentacio;

import Aplicacio.Control;
import Aplicacio.ControlBBDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;

import static java.awt.Color.*;

public class Presentacio implements ActionListener, FocusListener {

	private Control control;
	private JFrame frame;
	private JPanel tot = new JPanel(new GridLayout());
	private JPanel botons = new JPanel(new GridLayout());
	private JPanel panel = new JPanel(new GridLayout(3, 3));
	private JPanel[][] jpanel = new JPanel[3][3];
	private CasellaGrafica[][] textField = new CasellaGrafica[9][9];
	private JButton nouSudoku = new JButton("Generar Nou Sudoku");
	private JButton crear = new JButton("Crear Sudoku");
	private JButton sudokuV3 = new JButton("Sudoku estàndard");
	private int nEntrades = 0;

	// NOU

	private ControlBBDD controlBBDD;
	private JButton guardarPartida = new JButton("Guardar");
	private JLabel loggin = new JLabel("Introdueix el seu nom per jugar: ");
	private JPanel iniciar = new JPanel(new GridLayout());

	public Presentacio(final String nom) {

		// Concurrency in Swing
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (nom.equals("Anonim")) {
						iniciarAnonim();
						initComponents();
						guardarPartida.setEnabled(false);
						loggin.setText("Aquesta partida no serà guardada a la base de dades");
					} else {
						controlBBDD = new ControlBBDD(nom);
						iniciarAmbPersistencia(nom);
						loggin.setText("El jugador que actualment està jugant es: " + nom);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(new JFrame(), ex.getStackTrace());
				}

			}
		});
	}

	private void iniciarAnonim() {
		try {

			int res = JOptionPane.showConfirmDialog(new JFrame(),
					"Vols crear un Sudoku desde zero?\n - En cas contrari es crearà un Sudoku predefinit\n\nBONA SORT",
					"TRIA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (res == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Instruccions:\n - Un cop introduït un número, fer clic ENTER per confirmar"
								+ "\n - En cas d'introduir un 0, la casella no tindrà cap valor"
								+ "\n - Per finalitzar la creació premeu 'Crear Sudoku' a la part inferior");
				control = new Control();
				nouSudoku.setEnabled(false);
			} else if (res == JOptionPane.NO_OPTION) {
				control = new Control();
				control.inciarCaselles();
				crear.setEnabled(false);
				sudokuV3.setEnabled(false);
			} else {
				System.exit(0);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error en crear el sudoku");
		}
	}

	private void iniciarAmbPersistencia(String nom) {

		crear.setEnabled(false);
		sudokuV3.setEnabled(true);
		try {
			int usuari = controlBBDD.nouJugador(nom); // -2 No existeix || 1 SI

			if (usuari == -2) {
				iniciarAnonim();
				initComponents();
				controlBBDD.iniciarSudoku();
			} else {
				controlBBDD.iniciarSudoku();
				Timestamp[] recuperats = controlBBDD.getTimeStamps();
				if (recuperats.length == 0) {
					iniciarAnonim();
					initComponents();
					controlBBDD.iniciarSudoku();
					usuari = -2;
				} else if (recuperats.length == 1)
					controlBBDD.setSudokuID(controlBBDD.getIdFromTimeStamp(recuperats[0]));
				else {
					Timestamp input = (Timestamp) JOptionPane.showInputDialog(null, "Quin sudoku vols recuperar?",
							"Elecció sudoku", JOptionPane.QUESTION_MESSAGE, null, recuperats, recuperats);

					controlBBDD.setSudokuID(controlBBDD.getIdFromTimeStamp(input));
				}

				if (!(usuari == -2)) {
					control = new Control();
					initComponents();
					String[][] graella = controlBBDD.getTaulellBBDD();
					String[][] ediatbles = controlBBDD.getEditablesBBDD();
					try {
						elimanar();
						for (int f = 0; f < 9; f++) {
							for (int c = 0; c < 9; c++) {

								if (ediatbles[f][c] == "s") {
									textField[f][c].setText(graella[f][c]);
									textField[f][c].setForeground(BLACK);
									textField[f][c].setBackground(WHITE);
									if (!(graella[f][c] == null)) {
										control.setEntrada(f, c, graella[f][c]);
										control.setEditable(f, c, true);

									}
									textField[f][c].setEditable(true);

								} else {
									textField[f][c].setText(graella[f][c]);
									textField[f][c].setForeground(YELLOW);
									textField[f][c].setBackground(GRAY);
									control.setEntrada(f, c, graella[f][c]);
									control.setEditable(f, c, false);
									textField[f][c].setEditable(false);
								}

							}
						} // FI FOR
					} // FI TRY
					catch (Exception e) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Hi ha hagut un problema mostrant la partida guardada. \n");
					}
				}
			}
			actualitzar();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Hi ha hagut un problema recuperant el jugador de la base de dades. \n");
		}
	}

	private void initComponents() {

		frame = new JFrame("EL SUDOKU");
		sudokuV3.setEnabled(false);

		frame.getContentPane().setLayout(new BorderLayout());

		String[][] graella = control.getTaulell();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				jpanel[i][j] = new JPanel();
				panel.add(jpanel[i][j]);
				jpanel[i][j].setLayout(new GridLayout(3, 3));
				jpanel[i][j].setBorder(BorderFactory.createLineBorder(BLACK, 2));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					for (int j = 0; j < 3; j++) {
						int f = i * 3 + x, c = y * 3 + j;

						textField[f][c] = new CasellaGrafica(f, c);
						jpanel[i][y].add(textField[f][c]);
						textField[f][c].setBorder(BorderFactory.createLineBorder(BLACK));
						textField[f][c].setHorizontalAlignment(SwingConstants.CENTER);
						textField[f][c].setFont(new Font("Calibri", 3, 25));

						if (!control.esModificable(f, c)) {
							textField[f][c].setText(graella[f][c]);
							textField[f][c].setForeground(YELLOW);
							textField[f][c].setBackground(GRAY);
							textField[f][c].setEditable(false);
						} else {
							textField[f][c].addActionListener(this);
							textField[f][c].addFocusListener(this);
						}
					}
				}
			}
		}

		iniciar.add(loggin);
		frame.add(iniciar, BorderLayout.NORTH);
		botons.add(guardarPartida);
		botons.add(nouSudoku, BorderLayout.NORTH);
		botons.add(crear, BorderLayout.CENTER);
		botons.add(sudokuV3, BorderLayout.SOUTH);

		tot.add(panel);
		frame.add(botons, BorderLayout.SOUTH);
		frame.add(tot, BorderLayout.CENTER);

		guardarPartida.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int res = JOptionPane.showConfirmDialog(new JFrame(),
						"Vols guardar el sudoku?\n - En cas contrari es finalitzarÃ  el joc.", "TRIA",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (res == JOptionPane.YES_OPTION) {
					try {
						if (controlBBDD.sudokuBuit())
							controlBBDD.storeSudoku();

						if (!(controlBBDD.taulellBuit()))
							controlBBDD.actualitzarBBDD(control.getTTaulell());
						else
							controlBBDD.storeTaulell(control.getTTaulell());
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				} else if (res == JOptionPane.NO_OPTION) {
					System.exit(0);

				} else {
				}

			}
		});

		nouSudoku.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				elimanar();
				try {
					int res = JOptionPane.showConfirmDialog(new JFrame(), "Perdras el sudoku actual, vols continuar?",
							"ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {
						control.canviarTaulell();
						if (controlBBDD != null)
							controlBBDD.iniciarSudoku();
						actualitzar();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
				}
			}
		});

		crear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nEntrades >= 17)
					try {
						elimanar();
						control.iniciarUsuari();
						actualitzar();
						crear.setEnabled(false);
						nouSudoku.setEnabled(true);
						sudokuV3.setEnabled(true);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
					}
				else {
					JOptionPane.showMessageDialog(new JFrame(),
							"No es posible crear el taulell, has de tenir" + " 17 o mes numeros introduits"
									+ "\n(Quantitat de numeros introduits : " + nEntrades + ")");
				}
			}
		});

		sudokuV3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					control = new Control();
					control.inciarCaselles();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), "Error en crear el sudoku");
				}
				nouSudoku.setEnabled(true);
				crear.setEnabled(false);
				sudokuV3.setEnabled(false);
				actualitzar();
			}
		});

		frame.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame, "Vols tancar el joc?", "Tancament joc",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						if (controlBBDD != null)
							controlBBDD.setEstatJuagdor();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
					}
					System.exit(0);
				}
			}
		});

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setBounds(0, 0, 500, 500);
		frame.setVisible(true);
	}

	private void actualitzar() {
		String[][] graella = control.getTaulell();
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					for (int j = 0; j < 3; j++) {
						int f = i * 3 + x, c = y * 3 + j;
						if (!control.esModificable(f, c)) {
							textField[f][c].setText(graella[f][c]);
							textField[f][c].setForeground(YELLOW);
							textField[f][c].setBackground(GRAY);
							textField[f][c].setEditable(false);
						} else {
							textField[f][c].setText(graella[f][c]);
							textField[f][c].setForeground(BLACK);
							textField[f][c].setBackground(WHITE);
							textField[f][c].addActionListener(this);
							textField[f][c].addFocusListener(this);
							textField[f][c].setEditable(true);
						}
					}
				}
			}
		}

		frame.revalidate();
		frame.repaint();
	}

	public void elimanar() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				textField[i][j].removeActionListener(this);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {

		CasellaGrafica casella = (CasellaGrafica) e.getSource();
		casella.setFocus(false);
		int f = casella.getFila();
		int c = casella.getColumna();
		try {
			posarColor();
			if (casella.getText().equals("0")) {
				if (nEntrades > 0)
					nEntrades--;
				control.esborrarCasella(f, c);
				textField[f][c].setText(null);
			} else {
				control.setEntrada(f, c, casella.getText());
				nEntrades++;
			}

			if (control.isComplete()) {
				JOptionPane.showMessageDialog(new JFrame(), "JOC FINALITZAT FELICITATS");
				guardarPartida.setEnabled(false);
				if (!(controlBBDD.sudokuBuit()))
					controlBBDD.esborrarSudokuTaulell();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
			int[][] errors = control.error();
			if (errors[0][0] != -1)
				textField[errors[0][0]][errors[1][0]].setBackground(Color.RED);
			if (errors[0][1] != -1)
				textField[errors[0][1]][errors[1][1]].setBackground(Color.RED);
			if (errors[0][2] != -1)
				textField[errors[0][2]][errors[1][2]].setBackground(Color.RED);
			textField[f][c].setText(null);
		}
	}

	private void posarColor() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (!control.esModificable(x, y))
					textField[x][y].setBackground(GRAY);
				else if (textField[x][y].isFocus())
					textField[x][y].setBackground(GREEN);
				else
					textField[x][y].setBackground(WHITE);
			}
		}
	}

	public void focusGained(FocusEvent e) {
		CasellaGrafica casella = (CasellaGrafica) e.getSource();
		int f = casella.getFila();
		int c = casella.getColumna();
		if (control.esModificable(f, c) && casella.getText().equals("")) {
			textField[f][c].setBackground(GREEN);
			casella.setFocus(true);
		}
	}

	public void focusLost(FocusEvent e) {
		CasellaGrafica casella = (CasellaGrafica) e.getSource();
		int f = casella.getFila();
		int c = casella.getColumna();
		if (control.esModificable(f, c) && casella.getText().equals("")) {
			textField[f][c].setBackground(WHITE);
			casella.setFocus(false);
		}
	}
}