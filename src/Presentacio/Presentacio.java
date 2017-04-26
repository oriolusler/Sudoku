package Presentacio;

import Aplicacio.Control;
import Aplicacio.LoginControler;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import static java.awt.Color.*;

public class Presentacio implements ActionListener, FocusListener {

	// crear bdd
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

	private Presentacio() {
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
		initComponents();

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

		botons.add(random, BorderLayout.NORTH);
		botons.add(crear, BorderLayout.CENTER);
		botons.add(sudokuV3, BorderLayout.SOUTH);

		tot.add(panel);
		frame.add(botons, BorderLayout.SOUTH);
		frame.add(tot, BorderLayout.CENTER);

		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int res = JOptionPane.showConfirmDialog(new JFrame(),
							"Perdras el sudoku actual, vols continuar?",
							"ALERTA", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					if (res == JOptionPane.YES_OPTION) {
						control.canviarTaulell();
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
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Error en crear el sudoku");
				}
				random.setEnabled(true);
				crear.setEnabled(false);
				sudokuV3.setEnabled(false);
				actualitzar();
				System.out.print("HOLA0");

			}
		});

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
							textField[f][c].setEditable(true);
							System.out.print("HOLA1");
						}
					}
				}
			}
		}

		frame.revalidate();
		frame.repaint();

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

			// ////////////////////////////////////////////////

			if (control.taulellBuit())
				control.storeTaulell(control.getTTaulell());
			else
				control.updateTaulell(f, c, casella.getText(),
						control.getTTaulell());

			// ///////////////////////////////////////////////
			if (control.isComplete()) {
				JOptionPane.showMessageDialog(new JFrame(),
						"JOC FINALITZAT FELICITATS");
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

			System.out.print("HOLA2");
		}
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Presentacio p = new Presentacio();

				p.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				p.frame.setBounds(0, 0, 500, 500);
				p.frame.setVisible(true);
				try {
					new LoginControler().Login();

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