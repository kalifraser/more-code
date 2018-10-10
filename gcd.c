//finds the greatest common denominator of two integers

#include<stdio.h>
#include<stdlib.h>

int main() {
   char* request="Enter a positive integer: ";
   char* polite="Please enter a positive integer: ";
   char str[100];
   int arraylen=2;
   int numbers[arraylen];
   int i=0;
   for(i=0; i<arraylen; i++) {
      int attempts=0;
      while(1) {
         if(attempts>0) {
            printf("%s", polite);
         }
         else {
            printf("%s", request);
         }
         int status=scanf(" %d", &(numbers[i]));
         if (status!=1 || numbers[i]<=0) {
            if (status!=1) {
               scanf(" %*s");
            }
            attempts ++;
            continue;
         } else {
            break;
         }
      }
   }
   int large=-1;
   int small=-1;
   if(numbers[0] > numbers[1]) {
      large=numbers[0];
      small=numbers[1];
   } else {
      large=numbers[1];
      small=numbers[0];
   }
   while (small != 0) {
      int remainder=large%small;
      large=small;
      small=remainder;
   }
   printf("The GCD of %d and %d is %d\n", numbers[0], numbers[1], large);
   return 0;
}
