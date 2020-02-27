package com.smashx.hangman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

  @Autowired private HangmanService hangmanService;

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @PostMapping("/games")
  public String checkLetter() {
    String gameId = hangmanService.startNewGame();
    return "redirect:/games/" + gameId;
  }

  @GetMapping("/games/{gameId}")
  public String game(@PathVariable String gameId, ModelMap model) {
    var maskedWord = hangmanService.getMaskedWord(gameId);
    model.addAttribute("word", maskedWord);
    var remaining = hangmanService.getRemainingLetters(gameId);
    model.addAttribute("remaining", remaining);
    model.addAttribute("gameId", gameId);
    return "hangman";
  }
}
