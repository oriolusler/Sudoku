package Presentacio;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LoginSudoku {

	private JLabel result_label = new JLabel(
			"Introdueix el nom d'usuari per jugar");
	private JLabel label_login = new JLabel("Usuari:");
	private JTextField login = new JTextField();

	public LoginSudoku() {

		boolean logat = DemanarCredencials();
		if (logat) {
			new Presentacio(login.getText());
		}

	}

	private boolean DemanarCredencials() {

		boolean logat = false;
		while (!logat) {

			Object[] array = { result_label, label_login, login };

			int res = JOptionPane.showConfirmDialog(null, array, "User Login",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (res == JOptionPane.OK_OPTION) {

				try {
					return true;
				} catch (Exception e) {
					result_label.setText("Usuari incorrecte.");
					result_label.setForeground(Color.RED);
					login.setText("");
				}
			} else
				System.exit(0);
		}
		return false;
	}

}
