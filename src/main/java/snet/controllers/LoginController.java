package snet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import snet.annotations.MasterView;

@Controller
@RequestMapping("adm")
@MasterView(name="index-adm")
public class LoginController extends AbstractController {

	@RequestMapping("login")
	public String login() {
		return "login/login";
	}

	@RequestMapping("login-error")
	public String loginError(Model m) {
		m.addAttribute("loginError", true);

		return "login/login";
	}

	@RequestMapping("logout-success")
	public String logoutSuccess(Model m) {
		m.addAttribute("logoutSuccess", true);

		return "login/login";
	}

	@RequestMapping("teste")
	public String teste() {
		return "adm/teste";
	}

	@RequestMapping("teste2")
	public String teste2() {
		return "adm/teste2";
	}
}
