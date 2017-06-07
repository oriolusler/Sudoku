package Persistencia;

class LoginBBDD {

	private static ConnectionBBDD connection;

	static void login(String user, String password) throws Exception {
		if (connection == null) {
			connection = new ConnectionBBDD(user, password);
		}

	}

	static ConnectionBBDD getConnection() throws Exception {
		if (connection == null)
			throw new Exception("No s'ha iniciat sessio");
		return connection;

	}

}
