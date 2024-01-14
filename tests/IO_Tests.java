package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.IO.LanguageManager;

public class IO_tests {

  // =========================[ LanguageManager ]=========================>>>
  /*
   * Test if all languages are loading right
   */
  @Test
  public void allLanguagesLoading() {
    LanguageManager lang = new LanguageManager();

    lang.load("English");
    assertEquals("WELCOME TO THE MONOPOLY GAME (Digital Bank Edition)", lang.get("WELCOME"));
    lang.load("Español");
    assertEquals("BIENVENIDO AL MONOPOLY (Edición Banco Digital)", lang.get("WELCOME"));
    lang.load("Català");
    assertEquals("BENVINGUT AL MONOPOLY (Edició Banc Digital)", lang.get("WELCOME"));
    lang.load("Euskara");
    assertEquals("ONGI ETORRI MONOPOLY (Digital Bank Edition)", lang.get("WELCOME"));
    lang.load("Galego");
    assertEquals("BENVIDO AO MONOPOLY (Edición Banco Digital)", lang.get("WELCOME"));
  }

  /*
   * Test if all the language files have the same number of keys
   */
  @Test
  public void allLanguagesKeys() {
    LanguageManager lang = new LanguageManager();

    lang.load("English");
    int englishKeys = lang.getGameStrings().size();
    lang.load("Español");
    int spanishKeys = lang.getGameStrings().size();
    lang.load("Català");
    int catalanKeys = lang.getGameStrings().size();
    lang.load("Euskara");
    int basqueKeys = lang.getGameStrings().size();
    lang.load("Galego");
    int galicianKeys = lang.getGameStrings().size();

    assertEquals(englishKeys, spanishKeys);
    assertEquals(englishKeys, catalanKeys);
    assertEquals(englishKeys, basqueKeys);
    assertEquals(englishKeys, galicianKeys);
  }

  // =========================[ IOManager ]=========================>>>
  /*
   * Test if the IOManager throws an exception when no language is loaded
   */
  @Test
  public void noLanguageLoaded() {
    assertThrows(RuntimeException.class, () -> IOManager.getMsg("WELCOME"));
  }

  /*
   * Test if the IOManager returns the right string
   */
  @Test
  public void languageLoaded() {
    IOManager.setLanguage("English");
    assertEquals("WELCOME TO THE MONOPOLY GAME (Digital Bank Edition)", IOManager.getMsg("WELCOME"));
  }

}
