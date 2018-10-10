/* Queens.java
* Kalinda Fraser
* klfraser
* pa5
* Provides a solution to the N-queens problem.
*/
public class Queens {
   static int n = -1;
   public static void main(String[] args) {
      boolean verbose = false;
      for(int i=0; i<args.length; i++){
         if(args[i].equals("-v")) {
            verbose = true;
            continue;
         }
         try{
            if(n < 0) {
               n = Integer.parseInt(args[i]);
               continue;
            }
         }catch(NumberFormatException e1){
         }
         printUsage();
         return;
      }
      int[] permutation = new int[n + 1];
      for(int i=1; i<n+1; i++) {
         permutation[i] = i;
      }
      int limit = factorial(n);
      int tally = 0;
      for(int i=0; i<limit; i++) {
         if(isSolution(permutation)) {
            tally++;
            if(verbose) {
               printPermutation(permutation);
            }
         }
         nextPermutation(permutation);
      }
      System.out.println(n + "-Queens has " + tally + " solutions");
   }
   static boolean isSolution(int[] permutation) {
      for(int i=1; i<n; i++){
         for(int j=i+1; j<=n; j++){
            if (j-i == Math.abs(permutation[i] - permutation[j])) {
               return false;
            }
         }
      }
      return true;  
   }
   static void nextPermutation(int[] permutation) {
      int pivot = findPivot(permutation);
      if (pivot == 0) {
         reverseArray(permutation, 1, n);
         return;
      }
      int successor = findSuccessor(permutation, pivot);
      int temp = permutation[pivot];
      permutation[pivot] = permutation[successor];
      permutation[successor] = temp;
      reverseArray(permutation, pivot+1, n);
      return;
   }
   private static int findSuccessor(int[] permutation, int pivot) {
      for(int i=n; i>=2; i--) {
         if(permutation[i] > permutation[pivot]) {
            return i;
         }
      }
      return 0;
   }
   private static void reverseArray(int[] permutation, int start, int end) {
      while(start<end){
         int temp = permutation[start];
         permutation[start] = permutation[end];
         permutation[end] = temp;
         start++;
         end--;
      }
   }
   private static void printPermutation(int[] permutation) {
      System.out.print("(");
      for(int i = 1; i<=n; i++) {
         System.out.print(permutation[i]);
         if(i<n) {
            System.out.print(", ");
         }
      }
      System.out.print(")\n");
   }
   private static int findPivot(int[] permutation) {
      for(int i=n-1; i>=1; i--) {
         if(permutation[i] < permutation[i+1]) {
            return i;
         }
      }
      return 0;
   }
   private static int factorial(int n) {
      int fact = 1;
      for(int i=1; i<=n; i++) {
         fact *= i;
      }
      return fact;
   }

   private static void printUsage() {
      System.out.println("Usage: Queens [-v] number");
      System.out.println("Option: -v   verbose output, print all solutions");
   }
}
