package snet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import snet.enums.SynsetTypeEnum;
import snet.model.entities.Language;
import snet.model.entities.Synset;
import snet.model.services.LanguageService;
import snet.model.services.SynsetService;

@Controller
@RequestMapping("synset")
public class SynsetController extends AbstractController {

	@Autowired
	private SynsetService synService;

	@Autowired
	private LanguageService langService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	private void setFieldValues(Model model) {
		List<Language> langs = langService.listAllActive();
		model.addAttribute("langs", langs);
		model.addAttribute("types", SynsetTypeEnum.values());
	}

	@RequestMapping("new")
	public String doNew(Model model) {
		setFieldValues(model);

		return "synset/edit";
	}

	@RequestMapping("edit/{id}")
	public String doEdit(@PathVariable int id, Model model) {
		setFieldValues(model);

		Synset synset = synService.getById(id);
		model.addAttribute("synset", synset);

		return "synset/edit";
	}

	@RequestMapping("save")
	public String save(@Validated ModelAndView mv) {
		return "redirect:/";
	}
}
