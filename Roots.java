/* Roots.java
* Kalinda Fraser
* klfraser
* pa4
* Finds the roots of a polynomial function within a given range.
*/

import java.util.Scanner;

public class Roots {

   public static double poly(double[] C, double x) {
      double sum = 0;
      for(int i=0; i<C.length; i++) {
         sum += C[i]*Math.pow(x, i);
      }
      return sum;
   }

   public static double[] diff(double[] C) {
      double[] modified = new double[C.length-1];
      for (int i=0; i<modified.length; i++)
      {
         modified[i] = C[i+1]*(i+1);
      }
      return modified;
   }
   
   public static double findRoot(double[] C, double a, double b, double tolerance) {
      if (poly(C, a) > 0) {
         if (poly(C, b) > 0) {
            throw new RuntimeException ();
         }
      }
      else if (poly(C, a) < 0) {
         if (poly(C, b) < 0) {
            throw new RuntimeException ();
         }
      }
      double mid = a;
      double width = b-a;
      while (width > tolerance) {
         mid = (a+b)/2;
         if (poly(C, a) * poly(C, mid) <= 0) {
            b = mid;
         } else {
            a = mid;
         }
         width = b-a;
      }
      return mid;
   }

   private static void printRoot(double root) {
      System.out.printf("Root found at %.5f%n", root);
   }

   public static void main(String[] args) {
      final double resolution = Math.pow(10, -2);
      final double tolerance = Math.pow(10, -7);
      final double threshold = Math.pow(10, -3);

      Scanner scan = new Scanner(System.in);
      System.out.print("Enter the degree: ");
      int degree = scan.nextInt();
      double[] orco = new double[degree+1];
      System.out.print("Enter " + orco.length + " coefficients: ");
      for(int i=0; i<orco.length; i++) {
         orco[i] = scan.nextDouble();
      }
      System.out.print("Enter the left and right endpoints: ");
      double L = scan.nextDouble();
      double R = scan.nextDouble();

      double[] diffco = diff(orco);

      double a = L;
      double b = a + resolution;
      boolean none = true;
      while (a <= R) {
         double resultA = poly(orco, a);
         double resultB = poly(orco, b);
         double diffA = poly(diffco, a);
         double diffB = poly(diffco, b);

         if ((resultA >= 0 && resultB <= 0) ||
               (resultB >= 0 && resultA <= 0)) {
            double found = findRoot (orco, a, b, tolerance);
            printRoot(found);
            none = false;
         }
         else if ((diffA >= 0 && diffB <= 0) ||
               (diffA >= 0 && diffB <= 0)) {
            double found = findRoot (diffco, a, b, tolerance);
            if (Math.abs(poly(orco, found)) < threshold) {
               printRoot(found);
               none = false;
            }
         }
         a = b;
         b = a + resolution;
      }
      if (none) {
         System.out.println("No roots were found in the specified range.");
      }
   }
}
