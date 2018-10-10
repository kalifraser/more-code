// CS 101: PA 2
// Kalinda Fraser
// klfraser

#include "List.h"
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct NodeObj* Node;

typedef struct NodeObj{
  int data;
  Node next;
  Node prev;
} NodeObj;

typedef struct ListObj{
  Node head;
  Node tail;
  int length;
  int cursorIndex;
  Node cursor;
} ListObj;

Node newNode(int data){
  Node N;
  N = malloc(sizeof(NodeObj));
  N->data = data;
  N->next = NULL;
  N->prev = NULL;
  return N;
}

void freeNode (Node* pN) {
  if (pN != NULL && *pN != NULL) {
      freeNode (&((*pN)->next));
      free(*pN);
      *pN = NULL;
  }
}

// Constructor for list
List newList() {
  List L;
  L = malloc(sizeof(ListObj));
  L->head = NULL;
  clear(L);
  return L;
}

void freeList (List* pL) {
  if (pL != NULL && *pL != NULL) {
      clear(*pL);
      free(*pL);
      *pL = NULL;
  }
}

// Access functions

int length(List L) { // Returns the number of elements in this list
  return L->length;
}

int index(List L) { // If cursor is defined, returns the index of the cursor element.
		   // otherwise returns -1
  return L->cursorIndex;
}

int front(List L) { // Returns front element. Pre: length(L) > 0
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
      return 0;
  }
  if (length(L) > 0) {
	 return L->head->data;
  }
  else {
      printf("List Error: Precondition not met.");
      exit(1);
      return 0;
  }
  return 0;
}

int back(List L) { // Returns back element. Pre: length(L) > 0
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
      return 0;
  }
  if (length(L) > 0) {
	 return L->tail->data;
  }
  else {
      printf("List Error: Precondition not met.");
      exit(1);
      return 0;
  }
  return 0;
}

int get(List L) { // Returns cursor element. Pre: length(L) > 0, index() >= 0
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
      return 0;
  }
  if (length(L) > 0 && L->cursorIndex >= 0) {
	 return L->cursor->data;
  }
  else {
      printf("List Error: Precondition not met.");
      exit(1);
      return 0;
  }
  return 0;
}

int getByPos(List L, int pos) {
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
      return 0;
  }
  int sum = 0;
  for (Node J = L->head; J != NULL; J = J->next) {
	 if (sum == pos) {
		return J->data;
	 }
	 sum++;
  }
  printf("List Error: Precondition not met.");
  exit(1);
  return 0;
}

int equals(List A, List B) { // Returns true if this List and L are the same integer
						// sequence. The cursor is ignored in both lists.
  // Loop through this list and compare each element to each element of L.
  // If any are different in length or data, return false.
  // Else, return true.
  if (A==NULL || B==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
      return 0;
  }
  int position = 0;
  if (length(A) == length(B)) {
	 for (Node H = A->head; H != NULL; H = H->next) {
		if (H->data == getByPos(B, position)) {
		   position++;
		}
		else {
		   return 0;
		}
	 }
	 return 1;
  }
  return 0;
}

// Manipulation procedures

void clear(List L) { // Resets this List to its original empty state.
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  freeNode(&L->head);
  L->head = NULL;
  L->tail = NULL;
  L->cursor = NULL;
  L->length = 0;
  L->cursorIndex = -1;
}

void moveFront(List L) { // If List is non-empty, places the cursor under the front element
				  // otherwise does nothing.
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->length != 0) {
	 L->cursor = L->head;
	 L->cursorIndex = 0;
	 return;
  }
  else return;
}

void moveBack(List L) { // If List is non-empty, places the cursor under the back element
				 // otherwise does nothing.
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->length != 0) {
	 L->cursor = L->tail;
	 L->cursorIndex = L->length - 1;
  }
}

void movePrev(List L) { // If cursor is defined and not at front, moves cursor one step toward
				 // front of this List, if cursor is defined and at front, cursor becomes
				 // undefined, if cursor is undefined does nothing.
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->cursor != NULL) {
	 if (L->cursorIndex != 0) {
		L->cursor = L->cursor->prev;
		L->cursorIndex--;
	 }
	 else {
		L->cursor = NULL;
		L->cursorIndex = -1;
	 }
  }
}

void moveNext(List L) { // If cursor is defined and not at back, moves cursor one step toward
				 // back of this List, if cursor is defined and at back, cursor becomes
				 // undefined, if cursor is undefined does nothing.
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->cursor != NULL) {
	 if (L->cursorIndex != L->length-1) {
		L->cursor = L->cursor->next;
		L->cursorIndex++;
	 }
	 else {
		L->cursor = NULL;
		L->cursorIndex = -1;
	 }
  }
}

void prepend(List L, int data) { // Insert new element into this List. If List is non-empty,
						 // insertion takes place before front element.
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  Node N = newNode(data);
  N->next = L->head;
  if (L->length == 0) {
	 L->tail = N;
  }
  else {
	 L->head->prev = N;
  }
  L->head = N;
  L->length++;
  if (L->cursorIndex != -1) {
	 L->cursorIndex++;
  }
}

void append(List L, int data) { // Insert new element into this List. If List is non-empty,
					   // insertion takes place after back element.
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  Node N = newNode(data);
  N->prev = L->tail;
  if (L->length == 0) {
	 L->head = N;
  }
  else {
	 L->tail->next = N;
  }
  L->tail = N;
  L->length++;
}

void insertBefore(List L, int data) { // Insert new element before cursor.
							 // Pre: length(L)>0, index()>=0
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->length > 0 && L->cursorIndex >= 0) {
	 Node N = newNode(data);
	 if (L->cursor->prev == NULL) {
		N->prev = NULL;
		N->next = L->cursor;
		L->cursor->prev = N;
		L->head = N;
	 }
	 else {
		Node temp = L->cursor->prev;
		L->cursor->prev = N;
		N->prev = temp;
		N->next = L->cursor;
		temp->next = N;
	 }
	 L->cursorIndex++;
	 L->length++;
  }
}

void insertAfter(List L, int data) { // Inserts new element after cursor.
							// Pre: length()>0, index()>=0
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->length > 0 && L->cursorIndex >= 0) {
	 Node N = newNode(data);
	 if (L->cursor->next == NULL) {
		N->next = NULL;
		N->prev = L->cursor;
		L->cursor->next = N;
		L->tail = N;
	 }
	 else {
		Node temp = L->cursor->next;
		L->cursor->next = N;
		N->next = temp;
		N->prev = L->cursor;
		temp->prev = N;
	 }
	 L->length++;
  }
}

void deleteFront(List L) { // Deletes the front element. Pre: length(L)>0
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->length > 0) {
	 //if the cursor is on the head, make it undefined.
	 //else, leave the cursor on the data it is pointing to.
	 if (L->cursor == L->head) {
		L->cursor = NULL;
		L->cursorIndex = -1;
	 }
	 else {
		L->cursorIndex--;
	 }
	 // store head->next as temp, then delete head
	 Node temp = L->head->next;
	 if (temp != NULL) {
		temp->prev = NULL;
	 }
	 else {
		L->tail = NULL;
	 }
	 L->head->next = NULL;
	 freeNode(&(L->head));
	 L->head = temp;
	 L->length--;
  }
}

void deleteBack(List L) { // Deletes the back element. Pre: length(L)>0
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->length > 0) {
     //if the cursor is on the head, make it undefined.
     //else, leave the cursor on the data it is pointing to.
     if (L->cursor == L->tail) {
	L->cursor = NULL;
	L->cursorIndex = -1;
     }
     // store head->next as temp, then delete head
     Node temp = L->tail->prev;
     if (temp != NULL) {
	temp->next = NULL;
     }
     else {
	L->head = NULL;
     }
     L->tail->next = NULL;
     freeNode(&(L->tail));
     L->tail = temp;
     L->length--;
  }
}

void delete(List L) { // Deletes cursor element, making cursor undefined.
			   // Pre: length(L)>0, index(L)>=0
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
  }
  if (L->length > 0 && L->cursorIndex >= 0) {
	 if (L->cursor != L->head) {
		if (L->cursor != L->tail) { //cursor is on neither head or nor tail
		   Node tempA = L->cursor->prev;
		   Node tempB = L->cursor->next;
		   L->cursor = NULL;
		   L->cursorIndex = -1;
		   tempA->next = tempB;
		   tempB->prev = tempA;
		}
		else { //cursor == tail
		   Node temp = L->cursor->prev;
		   if (temp != NULL) {
		      temp->next = NULL;
		   }
		   else {
		      L->head = NULL;
		   }
		   L->cursor->next = NULL;
		   freeNode(&(L->cursor));
		   L->cursor = NULL;
		   L->cursorIndex = -1;
		}
	 }
	 else { //cursor == head
		Node temp = L->cursor->next;
		if (temp != NULL) {
		   temp->prev = NULL;
		}
		else {
		   L->tail = NULL;
		}
		L->cursor->next = NULL;
		freeNode(&(L->cursor));
		L->cursor = NULL;
		L->cursorIndex = -1;
	 }
	 L->length--;
  }
}


// Other methods

void printList(FILE* out, List L) { //prints list
  if (L==NULL) {
      printf("List Error: Attempting to print NULL List.");
      exit(1);
  }
  for (Node i = L->head; i != NULL; i = i->next) {
	 fprintf(out, "%d ", i->data);
  }
}

List copyList(List L) { // Returns a new List representing the same integer sequence as this
			 // List. The cursor in the new list is undefined, regardless of the
			 // state of the cursor in this List. This List is unchanged.
  if (L==NULL) {
      printf("List Error: Attempting to call NULL List reference.");
      exit(1);
      return NULL;
  }
  List newL = newList();
  for(Node H = L->head; H != NULL; H = H->next) {
	 append(newL, H->data);
  }
  return newL;
}
