package src.MonopolyGame;

public class Const {
  public static final String PRINT_PADDING = "|\t";
  public static final String MONOPOLY_LOGO = "\n" +
      PRINT_PADDING + " __  __   ____   _   _   ____   _____    ____   _   __     __\n" +
      PRINT_PADDING + "|  \\/  | / __ \\ | \\ | | / __ \\ |  __ \\  / __ \\ | |  \\ \\   / /\n" +
      PRINT_PADDING + "| \\  / || |  | ||  \\| || |  | || |__) || |  | || |   \\ \\_/ / \n" +
      PRINT_PADDING + "| |\\/| || |  | || . ` || |  | ||  ___/ | |  | || |    \\   /  \n" +
      PRINT_PADDING + "| |  | || |__| || |\\  || |__| || |     | |__| || |____ | |   \n" +
      PRINT_PADDING + "|_|  |_| \\____/ |_| \\_| \\____/ |_|      \\____/ |______||_|\n" +
      PRINT_PADDING + "\n";
  public static final String DEFAULT_LANG = "English";
  public static final String SAVES_PATH = "./config/oldGames/";
  public static final String LANGUAGES_PATH = "./config/languages/";
  public static final String CONFIG_PATH = "./config/";
  public static final String CONFIG_FILENAME = "MonopolyCode.txt";
  public static final String CONFIG_CODES_FILE_PATH = CONFIG_PATH + CONFIG_FILENAME;
  public static final String CURRENCY_SYMBOL = "€";
  public static final int STARTING_MONEY = 1500;
}
