package com.smashx.hangman;

import java.util.Set;

public interface HangmanService {

  Set<Character> getAlphabet();

  String startNewGame();

  String getWord(String gameId);

  Set<Character> getRemainingLetters(String gameId);

  String getMaskedWord(String gameId);

  boolean makeTry(char nextTry);
}
