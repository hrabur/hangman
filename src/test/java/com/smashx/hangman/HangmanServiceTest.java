package com.smashx.hangman;

import static com.smashx.hangman.HangmanServiceImpl.WORDS_LIST;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HangmanServiceTest {

  HangmanServiceImpl hangman;

  @BeforeEach
  public void setUp() {
    hangman = new HangmanServiceImpl();
  }

  @Test
  void testMaskWord() {
    assertThat(HangmanServiceImpl.maskWord("jazz")).isEqualTo("j_zz");
    assertThat(HangmanServiceImpl.maskWord("xenophobia")).isEqualTo("x________a");
    assertThat(HangmanServiceImpl.maskWord("bugaboo")).isEqualTo("b___boo");
  }

  @Test
  void testMakeTry() {
    String gameId = hangman.startNewGame("abcd");
    assertThat(hangman.makeTry(gameId, 'b')).isTrue();
    assertThat(hangman.getMaskedWord(gameId)).isEqualTo("ab_d");
    assertThat(hangman.getRemainingLetters(gameId)).hasSize(23);
    assertThat(hangman.makeTry(gameId, 'f')).isFalse();
    assertThat(hangman.getRemainingLetters(gameId)).hasSize(22);
  }

  @Test
  void test() {
    String gameId = hangman.startNewGame();
    assertThat(gameId).isNotNull();

    String word = hangman.getMaskedWord(gameId);
    assertThat(word).isIn(WORDS_LIST);

    var remainingLetters = hangman.getRemainingLetters(gameId);
    Stream.of(remainingLetters)
        .forEach(
            letter -> {
              // assertThat(word).doesNotContain(remainingLetters);
            });

    char nextTry = random(remainingLetters);
    boolean success = hangman.makeTry(gameId, nextTry);
    assertThat(hangman.getRemainingLetters(gameId).size()).isEqualTo(remainingLetters.size() - 1);
  }

  private char random(Set<Character> remainingLetters) {
    // TODO Auto-generated method stub
    return 0;
  }
}
