#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Dictionary.h"

//structs and fields for NodeObj and DictionaryObj
typedef struct NodeObj {
   char* key;
   char* value;
   struct NodeObj* next;
} NodeObj;

typedef NodeObj* Node;

struct DictionaryObj {
   Node head;
   int numPairs;
} DictionaryObj;

void freeNode(Node* pN);

//Constructor for Dictionary
Dictionary newDictionary(void) {
   Dictionary D = malloc(sizeof(DictionaryObj));
   D->head = NULL;
   D->numPairs = 0;
   return D;
}

// freeDictionary()
// destructor for the Dictionary type
void freeDictionary(Dictionary* pD) {
   if(*pD != NULL) {
      freeNode(&((*pD)->head));
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

//Returns Node which contains key, else returns NULL.
Node findKey(Dictionary dict, char* key) {
   Node N = dict->head;
   for(int i = 0; i < dict->numPairs; i++) {
      if(strcmp(N->key, key) == 0) {
         return N;
      }
      N = N->next;
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
   for(Node A = dict->head; A != NULL; A = A->next) {
      if(strcmp(A->key, key) == 0) {
         fprintf(stderr, "cannot insert duplicate keys");
         exit(EXIT_FAILURE);
      }
   }
   Node B = newNode(key, value);
   B->next = dict->head;
   dict->head = B;
   dict->numPairs++;
}

// delete()
// deletes pair with the key "key"
// pre: lookup(D, key)!=NULL
void delete(Dictionary D, char* key){
   Node previous = NULL;
   Node A;
   for(A = D->head; A != NULL; A = A->next) {
      if(strcmp(A->key, key) == 0) {
         if (A == D->head) {
            Node B = D->head;
            D->head = D->head->next;
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
   freeNode(&(D->head));
   D->numPairs = 0;
}

// printDictionary()
// pre: none
// prints a text representation of D to the file pointed to by out
void printDictionary(FILE* out, Dictionary D){
   Node N;
   for (N = D->head; N != NULL; N = N->next) {
      fprintf(out, "%s %s\n", N->key, N->value);
   }
}
