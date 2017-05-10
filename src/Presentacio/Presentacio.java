package Presentacio;

import javax.swing.*;

import Aplicacio.Control;
import Aplicacio.LoginControler;

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
	private JButton random = new JButton("Generar Nou Sudoku");
	private JButton crear = new JButton("Crear Sudoku");
	private JButton sudokuV3 = new JButton("Sudoku V3");
	private int nEntrades = 0;
	private JTextField textLog = new JTextField();
	private JButton guardarPartida = new JButton("Guardar");
	private int quinSudoku = 0;
	private JLabel loggin = new JLabel("Introdueix el seu nom per jugar: ");
	private JPanel iniciar = new JPanel(new GridLayout());

	public Presentacio() {

		try {
			control = new Control(true);
			quinSudoku = control.quantsTaulells() + 1;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		guardarPartida.setEnabled(false);
		crear.setEnabled(false);
		sudokuV3.setEnabled(false);

		initComponents();
	}

	private void inici() {
		try {

			int sudokuUsuari = control.nouJugador(textLog.getText());

			if (!(sudokuUsuari == -2)) {
				Timestamp[] Ids = control.getTimeStamps();

				if (Ids.length == 0) {
					control = new Control(false);
					actualitzar();
					quinSudoku = control.quantsTaulells() + 1;
					sudokuUsuari = -2;

				} else if (Ids.length == 1)
					quinSudoku = control.getIdFromTimeStamp(Ids[0]);
				else {
					Timestamp input = (Timestamp) JOptionPane.showInputDialog(
							null, "Choose now...", "The Choice of a Lifetime",
							JOptionPane.QUESTION_MESSAGE, null, Ids, Ids);

					quinSudoku = control.getIdFromTimeStamp(input);
				}
			}
			textLog.setEditable(false);
			loggin.setText("El jugador actualment jugant és:");

			if (!(sudokuUsuari == -2)) {
				tot.setVisible(true);
				botons.setVisible(true);
				String[][] graella = control.getTaulellBBDD(quinSudoku);
				String[][] ediatbles = control.getEditablesBBDD(quinSudoku);
				control = new Control(true);

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
				}

			} else {

				try {
					int res = JOptionPane
							.showConfirmDialog(
									new JFrame(),
									"Vols crear un Sudoku desde zero?\n - En cas contrari es creara un Sudoku predefinit\n\nBONA SORT",
									"TRIA", JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Instruccions:\n - Un cop introduit un numero, fer clic ENTER per confirmar"
												+ "\n - En cas d'introduir un 0, la casella no tindra cap valor"
												+ "\n - Per finalitzar la creacio premeu 'Crear Sudoku' a la part inferior");
						control = new Control(true);
						random.setEnabled(false);
						crear.setEnabled(true);

					} else if (res == JOptionPane.NO_OPTION) {
						control = new Control(false);
						crear.setEnabled(false);
						sudokuV3.setEnabled(false);

					} else {
						System.exit(0);
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Error en crear el sudoku");
				}

				quinSudoku = control.quantsTaulells() + 1;
				actualitzar();
				tot.setVisible(true);
				botons.setVisible(true);

			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(),
					"Error lectura BBDD", JOptionPane.ERROR_MESSAGE);
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
				jpanel[i][j]
						.setBorder(BorderFactory.createLineBorder(BLACK, 2));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					for (int j = 0; j < 3; j++) {
						int f = i * 3 + x, c = y * 3 + j;

						textField[f][c] = new CasellaGrafica(f, c);
						jpanel[i][y].add(textField[f][c]);
						textField[f][c].setBorder(BorderFactory
								.createLineBorder(BLACK));
						textField[f][c]
								.setHorizontalAlignment(SwingConstants.CENTER);
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

		textLog.setText("Introdueix el teu nom");
		iniciar.add(loggin);
		iniciar.add(textLog);

		frame.add(iniciar, BorderLayout.NORTH);
		botons.add(guardarPartida);
		botons.add(random, BorderLayout.NORTH);
		botons.add(crear, BorderLayout.CENTER);
		botons.add(sudokuV3, BorderLayout.SOUTH);

		tot.add(panel);
		tot.setVisible(false);
		botons.setVisible(false);
		frame.add(botons, BorderLayout.SOUTH);
		frame.add(tot, BorderLayout.CENTER);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane
						.showConfirmDialog(frame,
								"Estàs segur que vols tancar el joc?",
								"Tancament joc", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						control.setEstatJuagdor();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		});

		guardarPartida.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int res = JOptionPane
						.showConfirmDialog(
								new JFrame(),
								"Vols guardar el sudoku?\n - En cas contrari es finalitzarà el joc.",
								"TRIA", JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE);

				if (res == JOptionPane.YES_OPTION) {
					try {
						if (control.sudokuBuit(quinSudoku))
							control.storeSudoku(quinSudoku);
						// BBDD////////////////////////////////////////////////

						if (!(control.taulellBuit(quinSudoku)))
							control.actualitzarBBDD(control.getTTaulell(),
									quinSudoku);
						else
							control.storeTaulell(control.getTTaulell(),
									control.quantsTaulells());
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				} else if (res == JOptionPane.NO_OPTION) {
					System.exit(0);

				} else {
				}

			}
		});

		textLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				inici();
			}

		});

		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				guardarPartida.setEnabled(false);
				try {
					int res = JOptionPane.showConfirmDialog(new JFrame(),
							"Perdras el sudoku actual, vols continuar?",
							"ALERTA", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {
						control.canviarTaulell();
						control = new Control(control.getTTaulell());
						quinSudoku = control.quantsTaulells() + 1;
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
						control.iniciarUsuari();
						actualitzar();
						crear.setEnabled(false);
						random.setEnabled(true);
						sudokuV3.setEnabled(true);
						guardarPartida.setEnabled(true);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(new JFrame(),
								ex.getMessage());
					}
				else {
					JOptionPane.showMessageDialog(new JFrame(),
							"No es posible crear el taulell, has de tenir"
									+ " 17 o mes numeros introduits"
									+ "\n(Quantitat de numeros introduits : "
									+ nEntrades + ")");
				}

			}
		});

		sudokuV3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					control = new Control(false);
					quinSudoku = control.quantsTaulells() + 1;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Error en crear el sudoku");
				}
				random.setEnabled(true);
				crear.setEnabled(false);
				sudokuV3.setEnabled(false);
				actualitzar();
			}
		});

	}

	private void actualitzar() {
		String[][] graella = control.getTaulell();

		for (int f = 0; f < graella.length; f++) {
			for (int c = 0; c < graella.length; c++) {
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
					textField[f][c].setEditable(true);
				}
			}
		}

		frame.revalidate();
		frame.repaint();

	}

	public void actionPerformed(ActionEvent e) {
		guardarPartida.setEnabled(true);
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
				JOptionPane.showMessageDialog(new JFrame(),
						"FELICITATS! JOC FINALITZAT");
				frame.setEnabled(false);
				if (!(control.sudokuBuit(quinSudoku)))
					control.esborrarSudokuTaulell(quinSudoku);

				// ESBORRAR JOC BBDD
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

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				try {
					boolean logat = false;
					do {
						try {
							JLabel label_login = new JLabel("Usuari:");
							JTextField login = new JTextField();

							JLabel label_password = new JLabel("Password:");
							JPasswordField password = new JPasswordField();

							Object[] array = { label_login, login,
									label_password, password };

							int res = JOptionPane.showConfirmDialog(null,
									array, "Login BBDD ORACLE",
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.PLAIN_MESSAGE);

							if (res == JOptionPane.OK_OPTION) {
								// new
								// LoginControler().Login(login.getText().trim(),new
								// String(password.getPassword()));
								new LoginControler()
										.Login("osoler", "38878280");
								logat = true;
							} else
								System.exit(0);
						} catch (Exception e) {
							JOptionPane
									.showMessageDialog(new JFrame(),
											"Usuari i/o contrasenya incorrecte.\nTorna a provar.");
						}
					} while (!logat);

					Presentacio p = new Presentacio();

					p.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					p.frame.setBounds(0, 0, 500, 500);
					p.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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