/**
 * The shift-algorithm for the encryption/decryption. It shifts each letter by the specified number
 * according to its order in the alphabet. Encodes only English letters — from "a" to "z" and from
 * "A" to "Z". In other words, after "z" comes "a", after "Z" comes "A".
 */
package encryptdecrypt.encryptor;

public class ShiftEncryptor extends Encryptor {

  @Override
  String shift(String msg, int key) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < msg.length(); i++) {
      char c = msg.charAt(i);
      if (c >= 'a' && c <= 'z') {
        stringBuilder.append((char) ((c + key - 'a' + 26) % 26 + 'a'));
      } else if (c >= 'A' && c <= 'Z') {
        stringBuilder.append((char) ((c + key - 'A' + 26) % 26 + 'A'));
      } else {
        stringBuilder.append(c);
      }
    }
    return stringBuilder.toString();
  }
}
