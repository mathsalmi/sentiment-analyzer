package snet.controllers;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;

//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

//import snet.enums.SynsetTypeEnum;
//import snet.model.entities.Language;
//import snet.model.entities.Synset;
//import snet.model.entities.SynsetTerm;

@Controller
@RequestMapping("migrador")
@Transactional(readOnly = false)
public class MigradorController {
	// @Autowired
	// private SessionFactory sessionFactory;

	@RequestMapping("")
	public String migrar() throws IOException {
		return "";

		// BufferedReader br = null;
		//
		// try {
		// FileInputStream fis = new FileInputStream(new
		// File("/Users/salmi/Desktop/t.txt"));
		// br = new BufferedReader(new InputStreamReader(fis));
		//
		// Session session = sessionFactory.getCurrentSession();
		//
		// Query q = session.createQuery("select l from Language l where
		// l.id='en'");
		// Language lang = (Language) q.uniqueResult();
		//
		// String line = null;
		// while ((line = br.readLine()) != null) {
		//
		// // ignores comments
		// if (line.startsWith("#")) {
		// continue;
		// }
		//
		// String[] cols = line.split("\t");
		//
		// // ignore wrong formatted columns
		// if (cols.length != 6) {
		// continue;
		// }
		//
		// String type = cols[0];
		// String originalId = cols[1];
		// String posStr = cols[2];
		// String negStr = cols[3];
		// String terms = cols[4];
		// String gloss = cols[5];
		//
		// Synset s = new Synset();
		// s.setType(SynsetTypeEnum.findByCode(type));
		// s.setOriginalId(originalId);
		// s.setPositiveScore(Float.parseFloat(posStr));
		// s.setNegativeScore(Float.parseFloat(negStr));
		// s.setTerms(new ArrayList<SynsetTerm>());
		// s.setGloss(gloss);
		//
		// String[] termsArr = terms.split(" ");
		// if (termsArr != null) {
		// for (String term : termsArr) {
		// String[] termData = term.split("#");
		// String termName = termData[0];
		// int senseNumber = Integer.parseInt(termData[1]);
		//
		// SynsetTerm st = new SynsetTerm();
		// st.setTerm(termName);
		// st.setSenseNumber(senseNumber);
		// st.setSynset(s);
		//
		// s.getTerms().add(st);
		// }
		// }
		//
		// s.setLanguage(lang);
		//
		// session.save(s);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (br != null) {
		// br.close();
		// }
		// }
		//
		// return "salmi1";
	}
}
