package Aplicacio;

import Persistencia.LoginBBDD;

public class LoginControler {

	public void Login(String nom, String password) throws Exception {

		LoginBBDD.login(nom, password);

	}
}
