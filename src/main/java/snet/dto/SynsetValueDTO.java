package snet.dto;

/**
 * Representa o synset já calculado.
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public class SynsetValueDTO {
	private String term;
	private String type;
	private double value;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
