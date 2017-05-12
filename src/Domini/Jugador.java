package Domini;

public class Jugador {

	private String nom;
	private int estat;

	public Jugador(String nom, int estat) {
		this.nom = nom;
		this.estat = estat;

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getEstat() {
		return estat;
	}

	public void setEstat(int estat) throws Exception {

		this.estat = estat;

	}

}
