package com.smashx.hangman;

import java.util.Set;

import com.smashx.hangman.HangmanServiceImpl.GameDescriptor;

public interface HangmanService {

  Set<Character> getAlphabet();

  String startNewGame();

  String getWord(String gameId);

  Set<Character> getRemainingLetters(String gameId);

  String getMaskedWord(String gameId);

  boolean makeTry(String gameId, char nextTry);

GameDescriptor getGameDescriptor(String gameId);
}
