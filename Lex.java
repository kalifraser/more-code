// CS 101: PA 1
// Kalinda Fraser
// klfraser

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Lex {
   public static void main(String[] args) throws Exception {
      
      //check that there are two command line arguments, and quit if there are more or fewer.
      if (args.length != 2) {
         System.err.println("Usage: java Lex input_file output_file.");
         System.exit(1);
      }
      
      //count the number of lines n in the input file.
      //create a String array of length n and read in the lines as Strings
      int n = 0;
      Scanner in = new Scanner(new File(args[0]));
      while(in.hasNextLine() ){
         n++;
         in.nextLine();
      }
      in.close();
      String[] stringArray = new String[n];
      in = new Scanner(new File(args[0]));
      for (int i = 0; i < n; i++) {
         stringArray[i] = in.nextLine();
         
      }
      in.close();
      
      //create a list whose elements are the indices of the above string array.
      //these indices should be arranged in an order that effectively sorts the array.
      //sort using compareTo()
      
      List newList = new List();
      for (int j = 0; j < n; j++) {
         String key = stringArray[j];
         //insert stringArray[j] after the first node smaller than it
         if (j == 0) {
            newList.append(j);
         }
         else { //j != 0
            newList.moveFront();
            for (int k = 0; k < j; k++) {
               String nextData = stringArray[newList.get()];
               if (key.compareTo(nextData) > 0) { //if key > nextData, move cursor forward.
                  newList.moveNext();
                  if (newList.index() == -1) {
                     newList.append(j);
                  }
               }
               else { // if key <= nextData, insert the new index j before the cursor.
                  newList.insertBefore(j);
                  break;
               }
            }
         }
      }
      
      //print the array in alphabetical order
      
      PrintWriter out = new PrintWriter(new FileWriter(args[1]));
      newList.moveFront();
      for (int i = 0; i < n; i++) {
         out.println(stringArray[newList.get()]);
         newList.moveNext();
      }
      out.close();
   }
}
