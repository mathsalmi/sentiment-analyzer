package teste.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teste.model.entities.Teste;
import teste.model.services.TesteService;

@Controller
public class IndexController {

	@Autowired
	private TesteService service;

	@RequestMapping("/")
	public String index(Model model) {

		List<Teste> el = service.list();

		model.addAttribute("list", el);

		return "salmi2";
	}

	@RequestMapping("salmi1")
	public String salmi1(@RequestParam(value = "name", required = false, defaultValue = "No name") String name, Model model) {

		model.addAttribute("name", name);

		return "redirect:/";
	}
}
