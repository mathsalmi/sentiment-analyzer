package snet.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import snet.model.services.SynsetService;

@Controller
@RequestMapping("/")
public class IndexController extends AbstractController {

	@Autowired
	private SynsetService synService;

	@RequestMapping("/")
	public String index(@RequestParam(required=false) String phrase, Model model) {
		if(StringUtils.isNotBlank(phrase)) {
			String phraseVal = synService.classifyPhrase(phrase, currLang());

			model.addAttribute("phrase", phrase);
			model.addAttribute("phraseVal", phraseVal);
		}

		return "index";
	}
}
