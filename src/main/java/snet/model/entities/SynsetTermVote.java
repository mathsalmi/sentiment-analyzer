package snet.model.entities;

import java.util.Date;

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
@Table(name = "synset_term_votes")
public class SynsetTermVote {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "synset_term_votes_id_gen")
	@SequenceGenerator(name = "synset_term_votes_id_gen", sequenceName = "sq_synset_term_votes_id", allocationSize = 1)
	private int id;

	@ManyToOne
	@JoinColumn(name = "synset_term_id", nullable = false)
	private SynsetTerm synsetTerm;

	@Column(name = "positive_score")
	private float positiveScore;

	@Column(name = "negative_score")
	private float negativeScore;

	@Column(name = "date")
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SynsetTerm getSynsetTerm() {
		return synsetTerm;
	}

	public void setSynsetTerm(SynsetTerm synsetTerm) {
		this.synsetTerm = synsetTerm;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}