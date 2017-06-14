package Presentacio;

import javax.swing.*;

import Aplicacio.ControlBBDD;

import java.awt.*;

public class UserLoginBBDD {

	private JLabel label_login = new JLabel("Usuari:");
	private JTextField login = new JTextField("G2GEILAB1");
	private JLabel label_password = new JLabel("Password:");
	private JPasswordField password = new JPasswordField("G2GEILAB14");
	private JLabel result_label = new JLabel("");
	private ControlBBDD controlBBDD;

	public static void main(String[] args) {

		UserLoginBBDD login = new UserLoginBBDD();
		login.iniciarJOC();
	}

	private void iniciarJOC() {

		controlBBDD = new ControlBBDD(null);
		boolean logat = demanarCredencials();
		if (logat)
			new LoginSudoku(controlBBDD);

	}

	private JPanel getPanel() {
		SpringLayout sl_panel = new SpringLayout();
		sl_panel.putConstraint(SpringLayout.NORTH, result_label, 10, SpringLayout.SOUTH, password);
		sl_panel.putConstraint(SpringLayout.SOUTH, login, -5, SpringLayout.NORTH, password);
		sl_panel.putConstraint(SpringLayout.EAST, password, 0, SpringLayout.EAST, login);
		sl_panel.putConstraint(SpringLayout.SOUTH, label_login, -11, SpringLayout.NORTH, label_password);
		sl_panel.putConstraint(SpringLayout.EAST, label_login, -6, SpringLayout.WEST, login);
		sl_panel.putConstraint(SpringLayout.NORTH, password, -3, SpringLayout.NORTH, label_password);
		JPanel panel = new JPanel(sl_panel);
		sl_panel.putConstraint(SpringLayout.WEST, result_label, 52, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, result_label, -49, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, result_label, -43, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, label_password, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, label_login, 22, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, login, -10, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, password, 90, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, login, 92, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, label_password, 74, SpringLayout.NORTH, panel);

		panel.add(label_login, BorderLayout.NORTH);
		panel.add(login, BorderLayout.NORTH);
		panel.add(label_password, BorderLayout.CENTER);
		panel.add(password, BorderLayout.EAST);
		panel.add(result_label, BorderLayout.SOUTH);
		panel.setPreferredSize(new Dimension(200, 170));

		return panel;
	}

	private boolean demanarCredencials() {

		boolean logat = false;
		while (!logat) {

			Object[] options = { "Login In", "Jugar sense persistencia", "Cancelar" };

			int value = JOptionPane.showOptionDialog(null, getPanel(), "Connexio a la base de dades",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

			if (value == 0) {

				try {

					controlBBDD.logIn(login.getText().trim(), new String(password.getPassword()));
					logat = true;
					return true;
				} catch (Exception e) {
					result_label.setText(e.getMessage());
					result_label.setForeground(Color.RED);
					login.setText("");
					password.setText("");
				}
			} else if (value == 1) {
				logat = true;
				controlBBDD.setJugadorNom("Anonim");
				new Presentacio(controlBBDD, false);
			} else
				System.exit(0);
		}
		return false;
	}
}
