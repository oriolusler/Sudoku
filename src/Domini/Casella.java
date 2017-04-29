package Domini;

public class Casella {

	private int valor,idCasella,idC;
	
	
	public int getIdC() {
		return idC;
	}

	public void setIdC(int idC) {
		this.idC = idC;
	}

	public int getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(int idCasella) {
		this.idCasella = idCasella;
	}

	private boolean editable;
	final static int vDefecte = 0;
	

	public Casella() {
		this.editable = true;
		this.valor = vDefecte;
	}

	public int getValor() {
		return valor;
	}

	public boolean isEditable() {
		return editable;
	}

	void setValor(int valor) throws Exception {
		if (valor < 1 || valor > 9)
			throw new Exception("Error valor casella: fora de rang. Ha de ser 1..9");
		if (!editable)
			throw new Exception("Error valor casella: la casella no es editable");
		this.valor = valor;
	}

	void setCasella(int valor) throws Exception {
		this.setValor(valor);
		this.editable = false;
	}

	void esborrarCasella() throws Exception {
		if (!editable)
			throw new Exception("Error valor casella: la casella no es editable");
		this.valor = vDefecte;
	}

	
}
