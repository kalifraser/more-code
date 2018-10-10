//-----------------------------------------------------------------------------
// DictionaryTest.c
// Code for testing the Dictionary ADT
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"Dictionary.h"

int main(int argc, char* argv[]){
   Dictionary A = newDictionary();

   insert(A, "summer", "peach");
   insert(A, "spring", "apricot");
   insert(A, "fall", "apple");
   insert(A, "winter", "lemon");

   printDictionary(stdout, A);

   printf("%s\n", lookup(A, "fall"));
   if (lookup (A, "fakeSeason") != NULL) {
      printf("fakeSeason should be NULL!\n");
   }

   delete(A, "spring");

   printDictionary(stdout, A);

   printf("%s\n", (isEmpty(A)?"true":"false"));
   printf("%d\n", size(A));
   makeEmpty(A);
   printf("%s\n", (isEmpty(A)?"true":"false"));

   freeDictionary(&A);

   return(EXIT_SUCCESS);
}
