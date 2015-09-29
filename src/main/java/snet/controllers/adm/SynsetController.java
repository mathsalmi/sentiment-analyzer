package snet.controllers.adm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import snet.annotations.MasterView;
import snet.controllers.AbstractController;
import snet.enums.SynsetTypeEnum;
import snet.model.entities.Language;
import snet.model.entities.Synset;
import snet.model.entities.SynsetTerm;
import snet.model.services.LanguageService;
import snet.model.services.SynsetService;

@Controller
@RequestMapping("adm/synset")
@MasterView(name="index-adm")
public class SynsetController extends AbstractController {

	@Autowired
	private SynsetService synService;

	@Autowired
	private LanguageService langService;

	@RequestMapping("")
	public String index(Model model) {

		List<Synset> synsets = synService.getByLangId(currLang().getId());
		model.addAttribute("synsets", synsets);

		return "synset/list";
	}

	/**
	 * Envia para a view valores utilizados por várias telas
	 */
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
	public String save(@ModelAttribute Synset synset, Errors errors) {
		if( ! errors.hasErrors()) {
			// fix relationship ids
			List<SynsetTerm> terms = synset.getTerms();
			if(terms != null && terms.size() > 0) {
				for(SynsetTerm term : terms) {
					if(term.getSynset() == null) {
						term.setSynset(synset);
					}
				}
			}

			synService.save(synset);
		}

		return "redirect:/adm/synset/";
	}

	@RequestMapping("delete/{id}")
	public String delete(@PathVariable int id) {
		//synService.delete(id);

		return "redirect:/adm/synset/";
	}

	@RequestMapping("refresh/{id}")
	public String refreshValues(@PathVariable int id) {
		return "redirect:/adm/synset/";
	}
}
