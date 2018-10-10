// CS 101: PA 2
// Kalinda Fraser
// klfraser

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"List.h"

#define MAX_LEN 160

int main (int argc, char** argv) {

      int n=0;
      FILE *in, *out;
      char line[MAX_LEN];
      List L;

      //check that there are two command line arguments, and quit if there are more or fewer.
      if (argc != 3) {
         printf("Usage: %s input_file output_file.", argv[0]);
         exit(1);
      }

      // open files for reading and writing
      in = fopen(argv[1], "r");
      out = fopen(argv[2], "w");
      if( in==NULL ){
	 printf("Unable to open file %s for reading\n", argv[1]);
	 exit(1);
      }
      if( out==NULL ){
	 printf("Unable to open file %s for writing\n", argv[2]);
	 exit(1);
      }

      /* read each line of input file, then count and print tokens */

       while( fgets(line, MAX_LEN, in) != NULL)  {
             n++;
       }
       fclose(in);
       char** stringArray = calloc(n, sizeof(char*));
       in = fopen(argv[1], "r");
       for (int i = 0; i < n; i++) {
	   stringArray[i] = fgets(line, MAX_LEN, in);
       }
       fclose(in);

      //create a list whose elements are the indices of the above string array.
      //these indices should be arranged in an order that effectively sorts the array.
      //sort using compareTo()

      L = newList();
      for (int j = 0; j < n; j++) {
         char* key = stringArray[j];
         //insert stringArray[j] after the first node smaller than it
         if (j == 0) {
            append(L, j);
         }
         else { //j != 0
            moveFront(L);
            for (int k = 0; k < j; k++) {
               char* nextData = stringArray[get(L)];
               if (strcmp(key, nextData) > 0) { //if key > nextData, move cursor forward.
                  moveNext(L);
                  if (index(L) == -1) {
                     append(L, j);
                  }
               }
               else { // if key <= nextData, insert the new index j before the cursor.
                  insertBefore(L, j);
                  break;
               }
            }
         }
      }

      //print the array in alphabetical order

      moveFront(L);
      for (int i = 0; i < n; i++) {
         fprintf(out, "%s\n", stringArray[get(L)]);
         moveNext(L);
      }
      fclose(out);
}
