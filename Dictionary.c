//Dictionary.c
//Creates a dictionary ADT using a  hash table

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Dictionary.h"

const int tableSize = 11;

//structs and fields for NodeObj and DictionaryObj
typedef struct NodeObj {
   char* key;
   char* value;
   struct NodeObj* next;
} NodeObj;

typedef NodeObj* Node;

struct DictionaryObj {
   Node* hash;
   int numPairs;
} DictionaryObj;

void freeNode(Node* pN);

//Constructor for Dictionary
Dictionary newDictionary(void) {
   Dictionary D = malloc(sizeof(DictionaryObj));
   D->hash = calloc(tableSize, sizeof(NodeObj));
   D->numPairs = 0;
   return D;
}

// freeDictionary()
// destructor for the Dictionary type
void freeDictionary(Dictionary* pD) {
   int i;
   if(*pD != NULL) {
      for(i = 0; i < tableSize; i++) {
         freeNode(&((*pD)->hash[i]));
      }
      free(*pD);
      *pD = NULL;
   }
}

//Constructor for Nodes
Node newNode(char* key, char* value) {
   Node N = malloc(sizeof(NodeObj));
   N->key = key;
   N->value = value;
   return N;
}

//destructor for Nodes; frees Node to avoid memory leaks.
void freeNode(Node* pN) {
   if(*pN != NULL) {
       freeNode(&((*pN)->next));
       free(*pN);
       *pN = NULL;
   }
}

//rotate_left ()
//rotate the bits in an unsigned int
unsigned int rotate_left (unsigned int value, int shift) {
   int sizeInBits = 8*sizeof(unsigned int);
   shift = shift & (sizeInBits - 1);
   if (shift == 0) {
      return value;
   }
   return (value << shift) | (value >> (sizeInBits - shift));
}

//pre_hash()
//turn a string into an unsigned int
unsigned int pre_hash (char* input) {
   unsigned int result = 0xBAE86554;
   while (*input) {
      result ^= *input++;
      result = rotate_left (result, 5);
   }
   return result;
}

//hash()
//turns a string into an int in the range 0 to tableSize - 1
int hash(char* key) {
   return pre_hash(key) % tableSize;
}

//Returns Node which contains key, else returns NULL.
Node findKey(Dictionary dict, char* key) {
   Node N = dict->hash[hash(key)];
   for(int i = 0; i < dict->numPairs; i++) {
      if(N != NULL) {
         if(strcmp(N->key, key) == 0) {
            return N;
         }
         N = N->next;
      }
   }
   return NULL;
}

//Returns true if the dictionary contains no pairs; false otherwise.
int isEmpty(Dictionary dict) {
   return (size(dict) == 0);
}

int size(Dictionary dict) {
   return dict->numPairs;
}

// lookup()
// returns the value v such that (key, v) is in D, or returns NULL if no
// such value v exists.
// pre: none
char* lookup(Dictionary D, char* key) {
   if(findKey(D, key) == NULL) {
      return NULL;
   }
   else {
      return findKey(D, key)->value;
   }
}

//Adds the pair (key,value) if there is no matching key; else creates an error.
void insert(Dictionary dict, char* key, char* value) {
   for(Node A = dict->hash[hash(key)]; A != NULL; A = A->next) {
      if(strcmp(A->key, key) == 0) {
         fprintf(stderr, "cannot insert duplicate keys");
         exit(EXIT_FAILURE);
      }
   }
   Node B = newNode(key, value);
   B->next = dict->hash[hash(key)];
   dict->hash[hash(key)] = B;
   dict->numPairs++;
}

// delete()
// deletes pair with the key "key"
// pre: lookup(D, key)!=NULL
void delete(Dictionary D, char* key){
   Node previous = NULL;
   Node A;
   for(A = D->hash[hash(key)]; A != NULL; A = A->next) {
      if(strcmp(A->key, key) == 0) {
         if (A == D->hash[hash(key)]) {
            Node B = D->hash[hash(key)];
            D->hash[hash(key)] = D->hash[hash(key)]->next;
            B->next = NULL;
            free(B);
         }
         else {
            previous->next = A->next;
            A->next = NULL;
            free(A);
         }
         break;
      }
      else if(A->next == NULL) {
         fprintf(stderr, "cannot delete non-existent key");
         exit(EXIT_FAILURE);
      }
      previous = A;
   }
   D->numPairs--;
}

// makeEmpty()
// re-sets D to the empty state.
// pre: none
void makeEmpty(Dictionary D) {
   int i;
   for(i = 0; i < tableSize; i++) {
      freeNode(&(D->hash[i]));
   }
   D->numPairs = 0;
}

// printDictionary()
// pre: none
// prints a text representation of D to the file pointed to by out
void printDictionary(FILE* out, Dictionary D){
   Node N;
   int i;
   for (i = 0; i < tableSize; i++) {
      for (N = D->hash[i]; N != NULL; N = N->next) {
         fprintf(out, "%s %s\n", N->key, N->value);
      }
   }
}
