package teste.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "synset_term")
public class SynsetTerm {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "synset_term_id_gen")
	@SequenceGenerator(name = "synset_term_id_gen", sequenceName = "sq_synset_term_id", allocationSize = 1)
	private int id;

	@ManyToOne
	@JoinColumn(name = "synset_id", nullable = false)
	private Synset synset;

	@Column
	private String term;

	@Column(name = "sense_number")
	private int senseNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Synset getSynset() {
		return synset;
	}

	public void setSynset(Synset synset) {
		this.synset = synset;
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
