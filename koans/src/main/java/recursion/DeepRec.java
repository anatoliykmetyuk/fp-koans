package recursion;

public class DeepRec {
  public static void main(String[] args) {
    System.out.println("Hello");
    helloCounter(0);
  }

  public static void helloCounter(int i) {
    System.out.println(i);
    helloCounter(i + 1);
  }
}
