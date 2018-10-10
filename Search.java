/* Search.java
* Kalinda Fraser
* klfraser
* pa2
* Searches an array using Merge Sort and Binary Search.
*/

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Search {
   
   public static void main(String[] args) throws FileNotFoundException {
      // check number of command line arguments
      if(args.length <= 1){
         System.err.println("Usage: Search file target1 [target2 ..]");
         System.exit(1);
      }
      
      // count the number of lines in file
      Scanner in = new Scanner(new File(args[0]));
      in.useDelimiter("\\Z"); // matches the end of file character
      String s = in.next();  // read in whole file as a single String
      in.close();
      String[] lines = s.split("\n");  // split s into individual lines
      int lineCount = lines.length;  // extract length of the resulting array
      
      Word[] words = new Word[lines.length];
      for (int i = 0; i < lines.length; i++) {
         words[i] = new Word(lines[i], i+1);
      }
      mergeSort(words, 0, words.length-1);
      for(int i = 1; i < args.length; i++) {
         int result = binarySearch(words, 0, words.length-1, args[i]);
         if (result == -1) {
             System.out.println(args[i]+" not found");
         } else {
             System.out.println(args[i]+" found on line " +result);
         }
      }
   }
   
   // mergeSort()
   // sort subarray A[p...r]
   public static void mergeSort(Word[] wordmerger, int p, int r){
      int q;
      if(p < r) {
         q = (p+r)/2;
         // System.out.println(p+" "+q+" "+r);
         mergeSort(wordmerger, p, q);
         mergeSort(wordmerger, q+1, r);
         merge(wordmerger, p, q, r);
      }
   }

   // merge()
   // merges sorted subarrays A[p..q] and A[q+1..r]
   public static void merge(Word[] A, int p, int q, int r){
      int n1 = q-p+1;
      int n2 = r-q;
      Word[] L = new Word[n1];
      Word[] R = new Word[n2];
      int i, j, k;

      for(i=0; i<n1; i++) L[i] = A[p+i];
      for(j=0; j<n2; j++) R[j] = A[q+j+1];
      i = 0; j = 0;
      for(k=p; k<=r; k++){
         if( i<n1 && j<n2 ){
            if( L[i].compareTo(R[j]) < 0){
               A[k] = L[i];
               i++;
            }else{
               A[k] = R[j];
               j++;
            }
         }else if( i<n1 ){
            A[k] = L[i];
            i++;
         }else{ // j<n2
            A[k] = R[j];
            j++;
         }
      }
   }

   // binarySearch()
   // pre: Array A[p..r] is sorted
   public static int binarySearch(Word[] A, int p, int r,  String target){
      int q;
      if(p > r) {
         return -1;
      }else{
         q = (p+r)/2;
         if(target.compareTo(A[q].data) == 0){
            return A[q].line;
         }else if(target.compareTo(A[q].data) < 0){
            return binarySearch(A, p, q-1, target);
         }else{ // target > A[q]
            return binarySearch(A, q+1, r, target);
         }
      }
   }
}

class Word implements Comparable<Word> {
   
   String data;
   int line;

   Word(String data, int line) {
      this.data = data;
      this.line = line;
   }

   public int compareTo(Word other) {
      return this.data.compareTo(other.data);
   }

}
