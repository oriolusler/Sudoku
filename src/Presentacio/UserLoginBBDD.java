package Presentacio;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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

	private boolean DemanarCredencials() {

		boolean logat = false;
		while (!logat) {

			Object[] array = { label_login, login, label_password, password,
					result_label };

			int res = JOptionPane.showConfirmDialog(null, array, "Login BBDD",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (res == JOptionPane.OK_OPTION) {

				try {
					new LoginControler().Login(login.getText().trim(),
							new String(password.getPassword()));
					return true;
				} catch (Exception e) {
					result_label.setText("Usuari i/o contrasenya incorrecte.");
					result_label.setForeground(Color.RED);
					login.setText("");
					password.setText("");
				}
			} else
				System.exit(0);
		}
		return false;
	}

}
