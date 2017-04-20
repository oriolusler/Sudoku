package Domini;

public class Casella {

	private int valor;
	private boolean editable;
	final static int vDefecte = 0;

	public Casella() {
		this.editable = true;
		this.valor = vDefecte;
	}

	int getValor() {
		return valor;
	}

	boolean isEditable() {
		return editable;
	}

	void setValor(int valor) throws Exception {
		if (valor < 1 || valor > 9)
			throw new Exception("Error valor casella: fora de rang. Ha de ser 1..9");
		if (!editable)
			throw new Exception("Error valor casella: la casella no �s editable");
		this.valor = valor;
	}

	void setCasella(int valor) throws Exception {
		this.setValor(valor);
		this.editable = false;
	}

	void esborrarCasella() throws Exception {
		if (!editable)
			throw new Exception("Error valor casella: la casella no �s editable");
		this.valor = vDefecte;
	}
}
