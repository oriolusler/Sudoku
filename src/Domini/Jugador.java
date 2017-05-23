package Domini;

public class Jugador {

	private String nom;
	private boolean estat;

	public Jugador(String nom) {
		this.nom = nom;
	}

	public Jugador(String nom, boolean estat) {
		this.nom = nom;
		this.estat = estat;

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean getEstat() {
		return estat;
	}

	public void setEstat(boolean estat) throws Exception {

		this.estat = estat;

	}

}
