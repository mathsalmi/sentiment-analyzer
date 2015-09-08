package teste.model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import teste.enums.SynsetTypeEnum;

@Entity
@Table(name = "synset")
public class Synset {

	@Id
	@GeneratedValue
	private int id;

	@Column(length = 1)
	@Enumerated(EnumType.STRING)
	private SynsetTypeEnum type;

	@Column(length = 10, unique = true)
	private String originalId;

	@Column
	private float positiveScore;

	@Column
	private float negativeScore;

	@OneToMany
	private List<SynsetTerm> terms;

	@Column
	private String gloss;

	@ManyToOne
	private Language language;

	@Transient
	public float getNeutralScore() {
		return 1 - (getPositiveScore() - getNegativeScore());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SynsetTypeEnum getType() {
		return type;
	}

	public void setType(SynsetTypeEnum type) {
		this.type = type;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public float getPositiveScore() {
		return positiveScore;
	}

	public void setPositiveScore(float positiveScore) {
		this.positiveScore = positiveScore;
	}

	public float getNegativeScore() {
		return negativeScore;
	}

	public void setNegativeScore(float negativeScore) {
		this.negativeScore = negativeScore;
	}

	public List<SynsetTerm> getTerms() {
		return terms;
	}

	public void setTerms(List<SynsetTerm> terms) {
		this.terms = terms;
	}

	public String getGloss() {
		return gloss;
	}

	public void setGloss(String gloss) {
		this.gloss = gloss;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
