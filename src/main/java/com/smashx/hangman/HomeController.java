package com.smashx.hangman;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	public static class Name {
		private String fname;
		private String lname;

		public String getFname() {
			return fname;
		}

		public void setFname(String fname) {
			this.fname = fname;
		}

		public String getLname() {
			return lname;
		}

		public void setLname(String lname) {
			this.lname = lname;
		}

	}

	@GetMapping("/")
	public String home(ModelMap model) {
		char[] alphabet = "ABCDEF".toCharArray();
		model.addAttribute("alphabet", alphabet);
		return "home";
	}

	@PostMapping("/try")
	public String checkLetter(Name name, ModelMap model) {
		if (name.getFname().length() == 0 || name.getLname().length() == 0) {
			model.addAttribute("error", "First and last name are mandatory");
			return "redirect:/";
		}

		model.addAttribute("name", name);
		return "greeting";
	}
}
