package com.smashx.hangman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smashx.hangman.HangmanServiceImpl.GameDescriptor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class HangmanApi {

  @Autowired private HangmanService hangmanService;

  @PostMapping("/games")
  public GameDescriptor checkLetter() {
    String gameId = hangmanService.startNewGame();
    return hangmanService.getGameDescriptor(gameId);
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
