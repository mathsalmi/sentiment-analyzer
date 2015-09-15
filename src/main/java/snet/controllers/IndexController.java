package snet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import snet.model.entities.Language;
import snet.model.services.LanguageService;
import snet.model.services.SynsetService;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private SynsetService synService;

	@Autowired
	private LanguageService langService;

	@RequestMapping("/")
	public String index(@RequestParam(required=false) String phrase, Model model) {

		if(phrase != null) {
			Language lang = langService.findById("en");
			String phraseVal = synService.classifyPhrase(phrase, lang);

			model.addAttribute("phrase", phrase);
			model.addAttribute("phraseVal", phraseVal);
		}

		return "index";
	}
}
