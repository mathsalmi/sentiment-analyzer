package snet.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.AutoPopulatingList;
import org.springframework.util.AutoPopulatingList.ElementFactory;

import snet.converters.hibernate.SynsetTypeEnumConverter;
import snet.enums.SynsetTypeEnum;

@Entity
@Table(name = "synset")
public class Synset {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "synset_id_gen")
	@SequenceGenerator(name = "synset_id_gen", sequenceName = "sq_synset_id", allocationSize = 1)
	private int id;

	@Column(name = "pos_speech")
	@Convert(converter = SynsetTypeEnumConverter.class)
	private SynsetTypeEnum type;

	@Column(name = "original_id")
	private String originalId;

	@Column(name = "positive_score")
	private float positiveScore;

	@Column(name = "negative_score")
	private float negativeScore;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "synset")
	private final List<SynsetTerm> terms = new AutoPopulatingList<>(new ElementFactory<SynsetTerm>() {
		@Override
		public SynsetTerm createElement(int index) {
			SynsetTerm st = new SynsetTerm();
			st.setSynset(Synset.this);
			return st;
		}
	});

	@Column
	private String gloss;

	@ManyToOne
	@JoinColumn(name = "language_id", nullable = false)
	private Language language = new Language();

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
