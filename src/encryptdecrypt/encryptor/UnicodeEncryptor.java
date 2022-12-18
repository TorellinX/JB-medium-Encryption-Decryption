/**
 * The unicode-algorithm for the encryption/decryption. It shifts each letter by the specified
 * number according to the Unicode table.
 */
package encryptdecrypt.encryptor;

public class UnicodeEncryptor extends Encryptor {

  @Override
  String shift(String msg, int key) {
    StringBuilder stringBuilder = new StringBuilder(msg);
    for (int i = 0; i < stringBuilder.length(); i++) {
      char c = stringBuilder.charAt(i);
      stringBuilder.setCharAt(i, (char) (c + key));
    }
    return stringBuilder.toString();
  }
}
