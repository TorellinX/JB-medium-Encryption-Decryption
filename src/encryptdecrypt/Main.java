/**
 * The project uses very basic ideas of encryption and decryption. It allows to encrypt/decrypt one
 * message. It works with command-line arguments, reads the message from "-data" argument value or
 * from a file specified in the "-in" argument value. The order of the arguments might be
 * different.
 * <p>
 * The program parse these arguments from args:
 * <ul>
 *   <li>"-mode": the program's mode, "enc" for encryption, "dec" for decryption;
 *   <li>"-key": an integer key for the encryption algorithm to modify the message;
 *   <li>"-data": a text or ciphertext to encrypt/decrypt;
 *   <li>"-in": the full name of a file to read the data;
 *   <li>"-out": the full name of a file to write the result;
 *   <li>"-alg": encryption/decryption algorithm ("shift" or "unicode");
 * </ul>
 *
 * <ul>
 *   <li>If there is no -mode, the program should work in the enc mode;
 *   <li>If there is no -key, the program should consider that key is 0;
 *   <li>If there is no -data and no -in the program should assume that the data is an empty string;
 *   <li>If there is no -out argument, the program must print data to the standard output;
 *   <li>If there are both -data and -in arguments, your program should prefer -data over -in.
 *   <li>If there is something strange (an input file does not exist, or an argument doesn't have a value),
 *    the program should not fail. Instead, it must display a clear message about the problem and stop
 *    successfully. The message should contain the word Error.
 *    <li>If there is no -alg argument, default it to shift.
 * </ul>
 * <p>
 * If there is something strange (an input file does not exist, or an argument doesn't have a value),
 * the program displays a message about the problem and stops.
 */

/* test args:
-mode enc -key 5 -in "./Encryption-Decryption/task/res/text" -data "Welcome!" -out "./Encryption-Decryption/task/res/result"

Example 1: reading and writing to files; the arguments are:
-mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode
This command must get data from road_to_treasure.txt, encrypt the data with the key of 5, create
protected.txt, and write ciphertext into it.

Example 2: encryption with the unicode algorithm; the arguments are:
-mode enc -key 5 -data "Welcome to hyperskill!" -alg unicode
Output: \jqhtrj%yt%m~ujwxpnqq&

Example 3: decryption with the unicode algorithm; the arguments are:
-key 5 -alg unicode -data "\jqhtrj%yt%m~ujwxpnqq&" -mode dec
Output: Welcome to hyperskill!

Example 4: encryption with the shift algorithm; the arguments are:
-key 5 -alg shift -data "Welcome to hyperskill!" -mode enc
Output: Bjqhtrj yt mdujwxpnqq!

Example 5: decryption with the shift algorithm; the arguments are:
-key 5 -alg shift -data "Bjqhtrj yt mdujwxpnqq!" -mode dec
Output: Welcome to hyperskill!
 */

package encryptdecrypt;

import encryptdecrypt.encryptor.Encryptor;
import encryptdecrypt.encryptor.ShiftEncryptor;
import encryptdecrypt.encryptor.UnicodeEncryptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  Encryptor encryptor;
  boolean running;
  /**
   * Encryption or decryption mode.
   */
  Mode mode;
  /**
   * Encryption/decryption algorithm.
   */
  Algorithm algorithm;
  int key;
  /**
   * Message to be encrypted/decrypted.
   */
  String data;
  /**
   * The path to a file with message to be encrypted/decrypted.
   */
  String inPath;
  /**
   * The path to a file for encrypted/decrypted result message.
   */
  String outPath;
  File out;

  Main() {
    this.running = true;
    // default settings
    this.encryptor = new ShiftEncryptor();
    this.mode = Mode.ENC;
    this.algorithm = Algorithm.SHIFT;
    this.data = "";
    this.key = 0;
  }

  public static void main(String[] args) {
    Main app = new Main();
    app.start(args);
  }

  private void start(String[] args) {
    try {
      parseArguments(args);
      setEncryptor();
      loadDataFromFile();
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      running = false;
    }
    if (running) {
      processRequest();
    }
  }

  private void parseArguments(String[] args) throws IllegalArgumentException {
    if (args.length % 2 != 0) {
      throw new IllegalArgumentException("Error! Odd number of arguments!");
    }
    for (int i = 0; i < args.length; i = i + 2) {
      parseOneArgument(args[i], args[i + 1]);
    }
  }

  private void parseOneArgument(String command, String value) throws IllegalArgumentException {
    switch (command) {
      case "-mode" -> mode = parseMode(value);
      case "-key" -> key = parseKey(value);
      case "-data" -> data = parseString(value);
      case "-in" -> inPath = parseString(value);
      case "-out" -> outPath = parseString(value);
      case "-alg" -> algorithm = parseAlgorithm(value);
      default -> throw new IllegalArgumentException("Error! Wrong argument: " + command);
    }

  }

  private Mode parseMode(String token) throws IllegalArgumentException {
    return switch (token) {
      case "enc" -> Mode.ENC;
      case "dec" -> Mode.DEC;
      default -> throw new IllegalArgumentException("Error! Wrong mode: " + token);
    };
  }

  private Algorithm parseAlgorithm(String token) throws IllegalArgumentException {
    return switch (token) {
      case "shift" -> Algorithm.SHIFT;
      case "unicode" -> Algorithm.UNICODE;
      default -> throw new IllegalArgumentException("Error! Wrong encryption algorithm: " + token);
    };
  }

  private int parseKey(String token) {
    try {
      return Integer.parseInt(token);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error! Wrong key value: " + token +
          ". Please specify an integer number.");
    }
  }

  private String parseString(String token) {
    return token.strip();
  }

  private void setEncryptor() {
    switch (algorithm) {
      case SHIFT -> encryptor = new ShiftEncryptor();
      case UNICODE -> encryptor = new UnicodeEncryptor();
    }
  }


  private void loadDataFromFile() throws IllegalArgumentException {
    // test args for in:  -in "./Encryption-Decryption/task/res/text.otd"
    if (data == null || "".equals(data)) {
      try {
        data = (new String(Files.readAllBytes(Paths.get(inPath)))).strip();
      } catch (IOException e) {
        throw new IllegalArgumentException("Error! Wrong file-in path: " + inPath);
      }
    }
  }

  private void processRequest() {
    switch (mode) {
      case ENC -> handleEncode();
      case DEC -> handleDecode();
      default -> throw new IllegalArgumentException("Wrong mode: " + mode);
    }
  }

  private void handleEncode() {
    String encryptedMsg = this.encryptor.encode(data, key);
    outputProcessedMsg(encryptedMsg);
  }

  private void handleDecode() {
    String decryptedMsg = this.encryptor.decode(data, key);
    outputProcessedMsg(decryptedMsg);
  }

  private void outputProcessedMsg(String msg) {
    if (outPath == null || outPath.isBlank()) {
      System.out.println(msg);
    } else {
      out = new File(outPath);
      try (FileWriter writer = new FileWriter(out)) {
        writer.write(msg);
        writer.flush();
      } catch (IOException e) {
        System.out.println("Error! " + e.getMessage());
        running = false;
      }
    }
  }

}




