public class HelloWorld {

  public static void main(String[] args) {
    MyBase64 base64 = new MyBase64();

    // System.out.println(base64.encode("1klsajdfklajs"));
    // System.out.println(base64.decode(base64.encode("1klsajdfklajs")));

    // byte[] bs = base64.decodeToBytes(base64.encode("1"));
    // for (byte b : bs) {
    //   System.out.println(String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0'));
    // }

    byte[] bs2 = { 1, 2, 3, -7, -9, 111 };
    System.out.println(base64.encode(bs2));

    System.out.println();

    for (byte b : base64.decodeToBytes(base64.encode(bs2))) {
      System.out.println((int)b);
    }
  }
}