package Aplicacio;

import Persistencia.LoginBBDD;


public class LoginControler {

	public void Login() throws Exception{
	
	LoginBBDD.login("osoler", "38878280");
	//LoginBBDD.login("nguerrero", "v39958032");
	}
}
