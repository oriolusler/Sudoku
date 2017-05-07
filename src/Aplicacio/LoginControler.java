package Aplicacio;

import Persistencia.LoginBBDD;

public class LoginControler {

	public void Login(String nom, String password) throws Exception {

		LoginBBDD.login(nom, password);
		// LoginBBDD.login("osoler", "38878280");
		// LoginBBDD.login("nguerrero", "v39958032");
	}
}
