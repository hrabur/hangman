package com.smashx.hangman;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class HangmanServiceImpl implements HangmanService {

  public static final String[] WORDS_LIST = {
    "xenophobia", "euphemism", "triangulation", "bugaboo", "vellum"
  };

  public static class GameDescriptor {
    String gameId;
    String word;
    String maskedWord;
    Set<Character> guessed;
    Set<Character> remaining;

    public GameDescriptor(String gameId, String word, String maskedWord, Set<Character> guessed) {
      this.gameId = gameId;
      this.word = word;
      this.maskedWord = maskedWord;
      this.guessed = guessed;
    }

    public String getGameId() {
      return gameId;
    }

    public void setGameId(String gameId) {
      this.gameId = gameId;
    }

    public String getWord() {
      return word;
    }

    public void setWord(String word) {
      this.word = word;
    }

    public String getMaskedWord() {
      return maskedWord;
    }

    public void setMaskedWord(String maskedWord) {
      this.maskedWord = maskedWord;
    }

    public Set<Character> getGuessed() {
      return guessed;
    }

    public void setGuessed(Set<Character> guessed) {
      this.guessed = guessed;
    }

    public Set<Character> getRemaining() {
      return remaining;
    }

    public void setRemaining(Set<Character> remaining) {
      this.remaining = remaining;
    }
  }

  private Map<String, GameDescriptor> ongoingGames = new HashMap<>();

  @Override
  public Set<Character> getAlphabet() {
    return "abcdefghijklmnopqrstuvwxyz"
        .chars()
        .mapToObj(ch -> (char) ch)
        .collect(Collectors.toSet());
  }

  @Override
  public String startNewGame() {
    int wordIndex = new Random().nextInt(WORDS_LIST.length);
    String word = WORDS_LIST[wordIndex];
    return startNewGame(word);
  }

  public String startNewGame(String word) {
    String gameId = UUID.randomUUID().toString();
    String maskedWord = maskWord(word);
    Set<Character> guessed = initGuessedLetters(word);
    GameDescriptor descr = new GameDescriptor(gameId, word, maskedWord, guessed);
    ongoingGames.put(gameId, descr);
    descr.setRemaining(getRemainingLetters(gameId));
    return gameId;
  }

  private Set<Character> initGuessedLetters(String word) {
    Set<Character> guessed = new HashSet<>();
    guessed.add(word.charAt(0));
    guessed.add(word.charAt(word.length() - 1));
    return guessed;
  }

  static String maskWord(String word) {
    char first = word.charAt(0);
    char last = word.charAt(word.length() - 1);
    StringBuilder buff = new StringBuilder();
    for (char letter : word.toCharArray()) {
      buff.append((letter == first || letter == last) ? letter : '_');
    }
    return buff.toString();
  }

  @Override
  public String getWord(String gameId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Character> getRemainingLetters(String gameId) {
    var game = getGameDescriptor(gameId);
    Set<Character> guessed = game.getGuessed();
    Set<Character> remaining = getAlphabet();
    remaining.removeAll(guessed);
    return remaining;
  }

  @Override
  public String getMaskedWord(String gameId) {
    var game = getGameDescriptor(gameId);
    return game.getMaskedWord();
  }

  @Override
  public GameDescriptor getGameDescriptor(String gameId) {
    GameDescriptor game = ongoingGames.get(gameId);
    if (game == null) {
      throw new IllegalArgumentException("No ongoing game with id=" + gameId);
    }

    return game;
  }

  @Override
  public boolean makeTry(String gameId, char nextTry) {
    boolean found = false;
    var game = getGameDescriptor(gameId);
    game.getGuessed().add(nextTry);
    game.setRemaining(getRemainingLetters(gameId));
    String word = game.getWord();
    StringBuilder maskedWord = new StringBuilder(game.getMaskedWord());
    int pos = word.indexOf(nextTry);
    while (pos >= 0) {
      maskedWord.setCharAt(pos, nextTry);
      pos = word.indexOf(nextTry, pos + 1);
      found = true;
    }

    game.setMaskedWord(maskedWord.toString());
    return found;
  }
}
