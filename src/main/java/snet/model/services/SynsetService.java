package snet.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snet.dto.SynsetValueDTO;
import snet.model.entities.Language;
import snet.model.entities.Synset;
import snet.model.repositories.SynsetRepository;

@Service
public class SynsetService {

	@Autowired
	private SynsetRepository synRepo;

	public List<SynsetValueDTO> getPhraseTokens(String phrase, Language lang) {
		if(phrase != null) {
			String[] terms = phrase.split(" ");
			if(terms != null && terms.length > 0) {
				for(int i = 0; i < terms.length; i++) {
					terms[i] = terms[i].toLowerCase();
				}
			}

			return synRepo.listTokensWithValue(terms, lang);
		}

		return null;
	}

	public String classifyPhrase(String phrase, Language lang) {
		double total = 0;
		List<SynsetValueDTO> tokens = getPhraseTokens(phrase, lang);
		if(tokens != null && tokens.size() > 0) {
			for(SynsetValueDTO token : tokens) {
				total += token.getValue();
			}
		}

		String out;
		if(total >= 0.75) {
			out = "very positive";
		}else if(total > 0.25 && total < 0.5) {
			out = "positive";
		}else if(total >= 0.5) {
			out = "positive";
		}else if(total < 0 && total >= -0.25) {
			out = "negative";
		}else if(total < -0.25 && total >= -0.5) {
			out = "negative";
		}else if(total <= -0.75) {
			out = "very negative";
		}else {
			out = "neutral";
		}

		return out;
	}

	public Synset getById(int id) {
		return synRepo.getById(id);
	}
}
