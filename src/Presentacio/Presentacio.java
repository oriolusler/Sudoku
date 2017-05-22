package Presentacio;

import Aplicacio.Control;
import Aplicacio.ControlBBDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;

import static java.awt.Color.*;

public class Presentacio implements ActionListener, FocusListener {

	// ININCIAL
	private Control control;
	private JFrame frame;
	private JPanel tot = new JPanel(new GridLayout());
	private JPanel botons = new JPanel(new GridLayout());
	private JPanel panel = new JPanel(new GridLayout(3, 3));
	private JPanel[][] jpanel = new JPanel[3][3];
	private CasellaGrafica[][] textField = new CasellaGrafica[9][9];
	private int nEntrades = 0;
	private Timestamp[] recuperats;

	private String nom;

	// NOU

	private ControlBBDD controlBBDD;

	// MENU

	private MenuBar menu;

	public Presentacio(String nomUsuari) {

		this.nom = nomUsuari;
		// Concurrency in Swing
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					botons.setVisible(false);

					if (nom.equals("Anonim")) {
						iniciarAnonim();
						initComponents();
						
					} else {
						controlBBDD = new ControlBBDD(nom);
						iniciarAmbPersistencia(nom);

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
					"Vols crear un Sudoku desde zero?\n - En cas contrari es creara un Sudoku predefinit\n\nBONA SORT",
					"TRIA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (res == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Instruccions:\n - Un cop introduit un numero, fer clic ENTER per confirmar"
								+ "\n - En cas d'introduir un 0, la casella no tindra cap valor"
								+ "\n - Per finalitzar la creacio premeu 'Crear Sudoku' a la part inferior");
				control = new Control();
			} else if (res == JOptionPane.NO_OPTION) {
				control = new Control();
				control.inciarCaselles();

			} else {
				System.exit(0);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error en crear el sudoku");
		}
	}

	private void iniciarAmbPersistencia(String nom) {

		try {
			int usuari = controlBBDD.nouJugador(nom); // -2 No existeix || 1 SI

			if (usuari == -2) {
				iniciarAnonim();
				initComponents();
				controlBBDD.iniciarSudoku();

			} else {
				controlBBDD.iniciarSudoku();
				recuperats = controlBBDD.getTimeStamps();
				if (recuperats.length == 0) {
					iniciarAnonim();
					initComponents();
					controlBBDD.iniciarSudoku();
					usuari = -2;
				} else if (recuperats.length == 1)
					controlBBDD.setSudokuID(controlBBDD.getIdFromTimeStamp(recuperats[0]));
				else {
					Timestamp input = (Timestamp) JOptionPane.showInputDialog(null, "Quin sudoku vols recuperar?",
							"Eleccio sudoku", JOptionPane.QUESTION_MESSAGE, null, recuperats, recuperats);

					controlBBDD.setSudokuID(controlBBDD.getIdFromTimeStamp(input));
				}

				if (!(usuari == -2)) {
					control = new Control();
					initComponents();
					mostratSudokuRecuperat();
					menu.setControl(control);
				}
				actualitzar();

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Hi ha hagut un problema recuperant el jugador de la base de dades. \n");
		}
	}

	public void mostratSudokuRecuperat() {
		try {

			String[][] graella = controlBBDD.getTaulellBBDD();
			String[][] ediatbles = controlBBDD.getEditablesBBDD();

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
			JOptionPane.showMessageDialog(new JFrame(), "Hi ha hagut un problema mostrant la partida guardada. \n");
		}
	}

	private void initComponents() {

		frame = new JFrame("EL SUDOKU");

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

		menu = new MenuBar(this, control, controlBBDD);
		tot.add(panel);
		frame.add(tot, BorderLayout.CENTER);
		frame.setJMenuBar(menu);

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
		frame.setBounds(0, 0, 550, 550);
		frame.setVisible(true);
	}

	public void actualitzar() {
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

	public Timestamp[] getRecuperats() {
		return recuperats;
	}

	public String getNom() {
		return this.nom;
	}

	public int getNumeroEntrades() {
		return this.nEntrades;
	}

}