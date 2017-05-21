package Domini;

public class Casella {

	private int valor;
	private boolean editable;
	public final static int vDefecte = 0;

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
	
	public void setEditable(boolean v) {
		this.editable = v;
	}

	public void setValor(int valor) throws Exception {
		if (valor < 1 || valor > 9)
			throw new Exception("Error valor casella: fora de rang. Ha de ser 1 a 9");
		if (!editable)
			throw new Exception("Error valor casella: la casella no es editable");
		this.valor = valor;
	}

	public void setCasella(int valor) throws Exception {
		this.setValor(valor);
		this.editable = false;
	}

	public void esborrarCasella() throws Exception {
		if (!editable)
			throw new Exception("Error valor casella: la casella no es editable");
		this.valor = vDefecte;
	}
}
