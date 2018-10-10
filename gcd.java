/* GDC.java
* Kalinda Fraser
* klfraser
* pa3
* Computes GCD of two given numbers.
*/
public class GCD {
   public static void main (String[] args) {
      //make a scanner and ask for two positive integers
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter a positive integer: ");
  //  int number1 = scan.nextInt();
      int number1 = safeInt(scan);
      System.out.print("Enter another positive integer: ");
  //  int number2 = scan.nextInt();
      int number2 = safeInt(scan);
      //store the larger in large
      //and the smaller in small
      int large;
      int small;
      if(number1 > number2) {
         large = number1;
         small = number2;
      } else {
         large = number2;
         small = number1;
      }
      //perform the Euclidean algorithm
      while (small != 0) {
         int remainder = large%small;
         large = small;
         small = remainder;
      }
      //print results (large is the GCD)
      System.out.println("The GCD of " + number1 + " and " + number2 + " is " + large);
   }

   private static int safeInt(Scanner scan) {
      while(true) {
         try {
            String input = scan.nextLine();
            int value = Integer.parseInt(input);
            if(value > 0) {
               return value;
            }
         }
         catch(Exception e) {
         }
         System.out.print("Please enter a positive integer: ");
      }
   }
}
