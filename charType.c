#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>

void analyzeLine(char* s, int line, FILE* out);
void fail();
void cleanup();

int main(int argc, char* argv[]) {
   /* set up local variables */
   FILE* in;
   FILE* out;
   int line;
   char word[256];
   /* check for correct number of command line arguments */
   if(argc != 3) {
      printf("Usage: %s <input file> <output file>\n", argv[0]);
      fail();
   }
   /* open input file in read mode */
   in = fopen(argv[1], "r");
   if(in == NULL) {
      printf("Unable to read from file %s\n", argv[1]);
      fail();
   }
   /* open output file in write mode */
   out = fopen(argv[2], "w");
   if(out == NULL) {
      printf("Unable to write to file %s\n", argv[2]);
      fail();
   }
   /* push lines into the line analyzer for processing and printing */
   line = 1;
   while(fscanf(in, "%255[^\n]\n", word) != EOF) {
      analyzeLine(word, line, out);
      line++;
   }
   return EXIT_SUCCESS;
}

void analyzeLine(char* s, int line, FILE* out) {
   /* set up local variables */
   int i;
   int alpha = 0;
   int digit = 0;
   int punct = 0;
   int space = 0;
   char alphas[256] = "";
   char digits[256] = "";
   char puncts[256] = "";
   char spaces[256] = "";
   /* count up all the characters and put them into their categories */
   for(i=0; i<strlen(s); i++) {
      char chara = s[i];
      if(isalpha(chara)) {
         alphas[alpha] = chara;
         alpha++;
         alphas[alpha] = 0;
      }
      if(isdigit(chara)) {
         digits[digit] = chara;
         digit++;
         digits[digit] = 0;
      }
      if(ispunct(chara)) {
         puncts[punct] = chara;
         punct++;
         puncts[punct] = 0;
      }
      if(isspace(chara)) {
         spaces[space] = chara;
         space++;
         spaces[space] = 0;
      }
   }
   /* line x contains: */
   fprintf(out, "line %d contains:\n", line);
   /* the analysis of line x */
   fprintf(out, "%d alphabetic character", alpha);
   if(alpha != 1) {
      fprintf(out, "s");
   }
   fprintf(out, ": %s\n", alphas);
   fprintf(out, "%d numeric character", digit);
   if(digit != 1) {
      fprintf(out, "s");
   }
   fprintf(out, ": %s\n", digits);
   fprintf(out, "%d punctuation character", punct);
   if(punct != 1) {
      fprintf(out, "s");
   }
   fprintf(out, ": %s\n", puncts);
   space++; /* for the swallowed carriage return */
   fprintf(out, "%d whitespace character", space);
   if(space != 1) {
      fprintf(out, "s");
   }
   fprintf(out, ": %s\n", spaces);
   fprintf(out, "\n");
}

void fail() {
   exit(EXIT_FAILURE);
}
