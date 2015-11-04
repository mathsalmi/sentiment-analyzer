package snet.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import snet.dto.SynsetValueDTO;
import snet.enums.PhraseScoreEnum;
import snet.model.entities.Language;
import snet.model.entities.Synset;
import snet.model.entities.SynsetTerm;
import snet.model.repositories.SynsetRepository;
import snet.model.repositories.SynsetTermRepository;

@Service
public class SynsetService {

	@Autowired
	private SynsetRepository synRepo;
	@Autowired
	private SynsetTermRepository synTermRepo;

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

	public PhraseScoreEnum classifyPhrase(String phrase, Language lang) {
		double total = 0;
		List<SynsetValueDTO> tokens = getPhraseTokens(phrase, lang);
		if(tokens != null && tokens.size() > 0) {
			for(SynsetValueDTO token : tokens) {
				total += token.getValue();
			}
		}

		PhraseScoreEnum out;
		if(total >= 0.75) {
			out = PhraseScoreEnum.VERY_POSITIVE;
		}else if(total > 0.25 && total < 0.5) {
			out = PhraseScoreEnum.POSITIVE;
		}else if(total >= 0.5) {
			out = PhraseScoreEnum.POSITIVE;
		}else if(total < 0 && total >= -0.25) {
			out = PhraseScoreEnum.NEGATIVE;
		}else if(total < -0.25 && total >= -0.5) {
			out = PhraseScoreEnum.NEGATIVE;
		}else if(total <= -0.75) {
			out = PhraseScoreEnum.VERY_NEGATIVE;
		}else {
			out = PhraseScoreEnum.NEUTRAL;
		}

		return out;
	}

	public Synset getById(int id) {
		return synRepo.getById(id);
	}

	public List<Synset> getByLangId(String langId) {
		return synRepo.getByLangId(langId);
	}

	public Page<Synset> getByLangId(String langId, Pageable pageable) {
		return synRepo.getByLangId(langId, pageable);
	}

	public boolean save(Synset synset) {

		// somente salva termos em lowercase
		for(SynsetTerm st : synset.getTerms()) {
			st.setTerm(st.getTerm().toLowerCase());
		}

		try {
			synRepo.save(synset);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean delete(int id) {
		try {
			synTermRepo.deleteBySynsetId(id);
			synRepo.delete(id);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public SynsetTerm getTermRandomlyByLang(Language lang) {
		return synRepo.getTermRandomlyByLang(lang);
	}

	public boolean refreshValues(int id, Language lang) {
		try {
			synRepo.refreshValues(id, lang);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean refreshAllValues(Language lang) {
		try {
			synRepo.refreshAllValues(lang);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
