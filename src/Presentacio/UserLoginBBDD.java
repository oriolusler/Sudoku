package Presentacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import Aplicacio.LoginControler;

public class UserLoginBBDD {

	private JLabel label_login = new JLabel("Usuari:");
	private JTextField login = new JTextField("G2GEILAB1");
	private JLabel label_password = new JLabel("Password:");
	private JPasswordField password = new JPasswordField("oriolusoler");
	private JLabel result_label = new JLabel("");

	public static void main(String[] args) {

		UserLoginBBDD login = new UserLoginBBDD();
		boolean logat = login.DemanarCredencials();

		if (logat) {
			new LoginSudoku();
		}
	}

	private JPanel getPanel() {
		SpringLayout sl_panel = new SpringLayout();
		sl_panel.putConstraint(SpringLayout.WEST, password, 39, SpringLayout.EAST, label_password);
		sl_panel.putConstraint(SpringLayout.EAST, label_login, -27, SpringLayout.WEST, login);
		sl_panel.putConstraint(SpringLayout.NORTH, result_label, 40, SpringLayout.SOUTH, password);
		sl_panel.putConstraint(SpringLayout.SOUTH, login, -5, SpringLayout.NORTH, password);
		sl_panel.putConstraint(SpringLayout.SOUTH, label_login, -11, SpringLayout.NORTH, label_password);
		sl_panel.putConstraint(SpringLayout.NORTH, password, -3, SpringLayout.NORTH, label_password);
		JPanel panel = new JPanel(sl_panel);
		sl_panel.putConstraint(SpringLayout.EAST, password, -10, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, label_login, 22, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, login, 99, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, result_label, 52, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, result_label, -49, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, result_label, -43, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, label_password, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, login, -10, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, label_password, 74, SpringLayout.NORTH, panel);

		panel.add(label_login, BorderLayout.NORTH);
		panel.add(login, BorderLayout.NORTH);
		panel.add(label_password, BorderLayout.CENTER);
		panel.add(password, BorderLayout.EAST);
		panel.add(result_label, BorderLayout.SOUTH);
		panel.setPreferredSize(new Dimension(200, 200));
		return panel;
	}

	private boolean DemanarCredencials() {

		boolean logat = false;
		while (!logat) {

			Object[] options = { "Login In", "Jugar sense persistencia", "Cancelar" };

			int value = JOptionPane.showOptionDialog(null, getPanel(), "Connexio a la base de dades",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

			if (value == 0) {

				try {
					new LoginControler().Login(login.getText().trim(), new String(password.getPassword()));
					logat = true;
					return true;
				} catch (Exception e) {
					result_label.setText("Usuari i/o contrasenya incorrecte.");
					result_label.setForeground(Color.RED);
					login.setText("");
					password.setText("");
				}
			} else if (value == 1) {
				new Presentacio("Anonim");
				logat = true;
			} else
				System.exit(0);
		}
		return false;
	}

	/*
	 * private JLabel label_login = new JLabel("Usuari:"); private JTextField
	 * login = new JTextField("G2GEILAB1"); private JLabel label_password = new
	 * JLabel("Password:"); private JPasswordField password = new
	 * JPasswordField("oriolusoler"); private JLabel result_label = new
	 * JLabel("");
	 * 
	 * public static void main(String[] args) {
	 * 
	 * UserLoginBBDD login = new UserLoginBBDD(); boolean logat =
	 * login.DemanarCredencials(); if (logat) { new LoginSudoku();
	 * 
	 * } }
	 * 
	 * private boolean DemanarCredencials() {
	 * 
	 * boolean logat = false; while (!logat) {
	 * 
	 * Object[] array = { label_login, login, label_password, password,
	 * result_label };
	 * 
	 * int res = JOptionPane.showConfirmDialog(null, array, "Login BBDD",
	 * JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	 * 
	 * if (res == JOptionPane.OK_OPTION) {
	 * 
	 * try { new LoginControler().Login(login.getText().trim(), new
	 * String(password.getPassword())); return true; } catch (Exception e) {
	 * result_label.setText("Usuari i/o contrasenya incorrecte.");
	 * result_label.setForeground(Color.RED); login.setText("");
	 * password.setText(""); } } else System.exit(0); } return false; }
	 */

}
