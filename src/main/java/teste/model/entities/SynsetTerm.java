package teste.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "synset_term")
public class SynsetTerm {

	@Id
	@GeneratedValue
	private int id;

	@Column(length = 20)
	private String term;

	@Column
	private int senseNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getSenseNumber() {
		return senseNumber;
	}

	public void setSenseNumber(int senseNumber) {
		this.senseNumber = senseNumber;
	}
}
