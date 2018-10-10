public class ListTest {
   public static void main(String[] args) {
      List<String> testList = new List<String>();
      testList.add(1, "a");
      testList.add(2, "c");
      testList.add(2, "b");
      System.out.println(testList.isEmpty()); //false
      System.out.println(testList); // prints "a b c"
      testList.remove(2);
      System.out.println(testList.size()); //prints 2
      System.out.println(testList.get(1));
      testList.removeAll();
      System.out.println(testList.isEmpty()); //true
   }
}
