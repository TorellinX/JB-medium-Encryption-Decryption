/**
 * The template class for Encryption/decryption algorithm.
 */
package encryptdecrypt.encryptor;

public abstract class Encryptor {

  public String encode(String msg, int key) {
    return shift(msg, key);
  }

  public String decode(String msg, int key) {
    return shift(msg, -key);
  }

  abstract String shift(String msg, int key);
}
