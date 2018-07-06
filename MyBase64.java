import java.util.ArrayList;

interface IMyBase64 {
  public String encode(String s);

  public String encode(byte[] xs);

  public String decode(String s);

  public byte[] decodeToBytes(String s);
}

public class MyBase64 implements IMyBase64 {

  private static String base64Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

  /**
   * 编码函数
   * 
   * @param s The {@code String} to be encoded.
   */
  public String encode(String s) {
    return encodeBytes(stringToBytes(s));
  }

  public String encode(byte[] xs) {
    return encodeBytes(xs);
  }

  private static String encodeBytes(byte[] xs) {
    // 转换 byte[]
    byte[] bs = convertBytes(xs);
    String encodedString = "";
    for (byte b : bs) {
      // 查表输出对应的 char
      encodedString += encodeByte(b);
    }

    // 如果不是4的倍数，3*8=4*6，补"="
    if (encodedString.length() % 4 != 0) {
      int need = (int) Math.ceil((double) encodedString.length() / 4) * 4 - encodedString.length();
      for (int i = 0; i < need; i++)
        encodedString += "=";
    }

    return encodedString;
  }

  /**
   * 解码函数，解码到{@code String}
   * 
   * @param s The {@code String} to be decoded.
   */
  public String decode(String s) {
    String decodeString = "";
    String result = "";

    // 删掉不需要的 "="
    s = s.replace("=", "");

    // byte转为6位String
    for (int i = 0; i < s.length(); i++)
      decodeString += byteToString(decodeChar(s.charAt(i)), 6);

    // 每8位转回对应的ASCII的char
    for (int i = 0; i + 8 <= decodeString.length(); i += 8)
      result += Character.toString((char) Integer.parseInt(decodeString.substring(i, i + 8), 2));

    return result;
  }

  /**
   * 解码函数，解码到{@code byte}[]
   * 
   * @param s The {@code String} to be decoded.
   */
  public byte[] decodeToBytes(String s) {
    String decodeString = "";
    ArrayList<Byte> list = new ArrayList<Byte>();

    // 删掉不需要的 "="
    s = s.replace("=", "");

    // byte转为6位String
    for (int i = 0; i < s.length(); i++)
      decodeString += byteToString(decodeChar(s.charAt(i)), 6);

    // 每8位转回对应的byte
    for (int i = 0; i + 8 <= decodeString.length(); i += 8)
      list.add((byte) Integer.parseInt(decodeString.substring(i, i + 8), 2));

    return byteListToByteArray(list);
  }

  /**
   * 转换byte[]的函数。 byte[] 当成一整个 String 来处理，每六位为一个新的 byte。
   * 
   * @param xs The {@code byte[]} to be converted.
   */
  private static byte[] convertBytes(byte[] xs) {
    String str = "";
    for (byte b : xs)
      // 将每一个byte补齐前面的零拼到str上
      str += byteToString(b, 8);

    ArrayList<Byte> list = new ArrayList<Byte>();
    for (int i = 0; i < str.length(); i += 6) {
      // 每6位一组转换成byte，不足六位在后面补0
      String s;
      if (i + 6 <= str.length()) {
        s = str.substring(i, i + 6);
      } else {
        s = str.substring(i);
        int need = 6 - s.length();
        for (int j = 0; j < need; j++) {
          s += "0";
        }
      }

      list.add((byte) Integer.parseInt(s, 2));
    }

    return byteListToByteArray(list);
  }

  /**
   * 将字符串转成 byte[]
   * 
   * @param s The {@code String} to be converted.
   */
  private static byte[] stringToBytes(String s) {
    // ArrayList<Byte> list = new ArrayList<Byte>();
    // for (int i = 0; i < s.length(); i++) {
    // list.add((byte) s.charAt(i));
    // }

    // return byteListToByteArray(list);
    return s.getBytes();
  }

  /**
   * 查表转换byte
   * 
   * @param b The {@code Byte} to be encoded.
   */
  private static String encodeByte(byte b) {
    // base64 字母表
    return base64Alphabet.substring((int) b, (int) b + 1);
  }

  /**
   * 字符解码
   * 
   * @param ch The {@code char} to be decoded.
   */
  private static byte decodeChar(char ch) {
    return (byte) base64Alphabet.indexOf(ch);
  }

  /**
   * 将 {@link ArrayList} 转换成 {@code byte[]}
   * 
   * @param list The {@code ArrayList<Byte>} to be converted to {@code byte[]}
   */
  private static byte[] byteListToByteArray(ArrayList<Byte> list) {
    byte[] bs = new byte[list.size()];
    for (int i = 0; i < list.size(); i++)
      bs[i] = list.get(i);
    return bs;
  }

  /**
   * 补零输出{@code byte}对应位数的{@code String}
   * 
   * @param b   The {@code byte} to be converted.
   * @param len The length of String
   */
  private static String byteToString(byte b, int len) {
    return String.format("%" + len + "s", Integer.toBinaryString(b & 0xff)).replace(' ', '0');
  }
}