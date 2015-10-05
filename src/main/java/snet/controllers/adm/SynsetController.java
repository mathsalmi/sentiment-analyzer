package snet.controllers.adm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		return index(model, PAGINATION_FIRST_PAGE);
	}

	@RequestMapping("{page}")
	public String index(Model model, @PathVariable int page) {

		Pageable pageable = new PageRequest(page - 1, PAGINATION_TOTAL_PER_PAGE);

		Page<Synset> currentResults = synService.getByLangId(currLang().getId(), pageable);
		model.addAttribute("currentResults", currentResults);

		// Pagination variables
		int begin = Math.max(1, page - 4);

		// how many pages to display in the pagination bar
		int end = Math.min(begin + 9, currentResults.getTotalPages());

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", page);

		model.addAttribute("url", "/adm/synset/");

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

	// TODO: make it generic
	@RequestMapping("save")
	public String save(@ModelAttribute Synset synset, Errors errors) {
		if( ! errors.hasErrors()) {
			synService.save(synset);
		}

		return "redirect:/adm/synset/";
	}

	@RequestMapping("delete/{id}")
	public String delete(@PathVariable int id) {
		synService.delete(id);

		return "redirect:/adm/synset/";
	}

	@RequestMapping("refresh/{id}")
	public String refreshValues(@PathVariable int id) {
		return "redirect:/adm/synset/";
	}
}
