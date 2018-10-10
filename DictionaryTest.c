//-----------------------------------------------------------------------------
// DictionaryTest.c
// tests various functions within Dictionary.c
//-----------------------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include "Dictionary.h"

int main(void) {
   Dictionary testDict = newDictionary();
   insert(testDict, "Bill", "48");
   insert(testDict, "Kar", "51");
   insert(testDict, "Ken", "16");
   printDictionary(stdout, testDict);
   printf("%s\n", lookup(testDict, "Kar"));
   printf("%d\n", size(testDict));
   delete(testDict, "Ken");
   printDictionary(stdout, testDict);
   makeEmpty(testDict);
   printf("%d\n",isEmpty(testDict));
   freeDictionary(&testDict);

   return(EXIT_SUCCESS);
}
