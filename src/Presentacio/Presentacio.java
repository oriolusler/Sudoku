package Presentacio;

import Aplicacio.Control;
import Aplicacio.ControlBBDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.awt.Color.*;

public class Presentacio implements ActionListener, FocusListener {

	// ININCIAL
	private JFrame frame;
	private JPanel tot = new JPanel(new GridLayout());
	private JPanel panel = new JPanel(new GridLayout(3, 3));
	private JPanel[][] jpanel = new JPanel[3][3];
	private CasellaGrafica[][] textField = new CasellaGrafica[9][9];
	private int nEntrades = 0;
	private boolean iniciar;
	// CONTROL
	private Control control;
	// MENU
	private MenuBar menu;

	public Presentacio(ControlBBDD controlBBDD) {

		iniciar = controlBBDD.getInciar();
		control = new Control(controlBBDD);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (control.getJugador().getNom().equals("Anonim") || !iniciar) {
						iniciarAnonim();
						initGraella();
						initComponents();
					} else {
						initGraella();
						mostratSudokuRecuperat();
						initComponents();
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
								+ "\n - Per finalitzar la creacio premeu 'Iniciar Sudoku' en el menu");

			} else if (res == JOptionPane.NO_OPTION) {

				control.iniciarSudokuPredefinit();

			} else {
				if (!(control.getJugador().getNom().equals("Anonim")))
					control.setEstatJuagdor();
				System.exit(0);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error en crear el sudoku");
		}
	}

	public void mostratSudokuRecuperat() {
		try {
			control.recuperarTaulell();
			actualitzar();

		} // FI TRY
		catch (Exception e) {
			// JOptionPane.showMessageDialog(new JFrame(), "Hi ha hagut un
			// problema mostrant la partida guardada. \n");
			e.printStackTrace();
		}
	}

	private void initGraella() {

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
	}

	private void initComponents() {
		frame = new JFrame("EL SUDOKU");

		frame.getContentPane().setLayout(new BorderLayout());

		menu = new MenuBar(this, control);
		tot.add(panel);
		frame.add(tot, BorderLayout.CENTER);
		frame.setJMenuBar(menu);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame, "Vols tancar el joc?", "Tancament joc",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						if (!(control.getJugador().getNom().equals("Anonim")))
							control.setEstatJuagdor();
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

		elimanar();
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
				control.esborrarSudokuTaulell();
				System.exit(0);
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

	public int getNumeroEntrades() {
		return this.nEntrades;
	}

	public void setNumeroEntraddes(int nouNumero) {
		this.nEntrades = nouNumero;
	}
}