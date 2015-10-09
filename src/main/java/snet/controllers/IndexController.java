package snet.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import snet.annotations.MasterView;
import snet.dto.FlashMsg;
import snet.enums.FlashMsgTypeEnum;
import snet.enums.PhraseScoreEnum;
import snet.model.entities.SynsetTerm;
import snet.model.entities.SynsetTermVote;
import snet.model.services.SynsetService;
import snet.model.services.SynsetTermVoteService;

@Controller
@RequestMapping("/")
@MasterView
public class IndexController extends AbstractController {

	@Autowired
	private SynsetService synService;

	@Autowired
	private SynsetTermVoteService voteService;

	@RequestMapping("/")
	public String index(@RequestParam(required=false) String phrase, Model model) {
		if(StringUtils.isNotBlank(phrase)) {
			PhraseScoreEnum phraseScore = synService.classifyPhrase(phrase, currLang());

			model.addAttribute("phrase", phrase);
			model.addAttribute("phraseScore", phraseScore);
		}

		return "index";
	}

	@RequestMapping("contribute")
	public String contribute(Model model) {
		SynsetTerm randTerm = synService.getTermRandomlyByLang(currLang());
		model.addAttribute("synsetTerm", randTerm);

		return "contribute";
	}

	@RequestMapping(value="contribute", method=RequestMethod.POST)
	public String saveContribution(@ModelAttribute SynsetTermVote vote, Errors errors, RedirectAttributes ra) {
		boolean success = ! errors.hasErrors();
		if(success) {
			success = voteService.save(vote);
		}

		FlashMsg msg = null;
		if(success) {
			msg = new FlashMsg("Voto computado com sucesso. Obrigado! ;)", FlashMsgTypeEnum.SUCCESS);
		} else {
			msg = new FlashMsg("Erro ao computar voto =(", FlashMsgTypeEnum.ERROR);
		}

		ra.addFlashAttribute("message", msg);
		return "redirect:/contribute";
	}
}
