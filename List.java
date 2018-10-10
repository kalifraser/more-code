// CS 101: PA 1
// Kalinda Fraser
// klfraser

import java.util.ArrayList;

public class List {
   
   private class Node {
      int data;
      Node next;
      Node prev;
      // Constructor for Node
      Node(int data) {
         this.data = data;
         next = null;
         prev = null;
      }
      
      public String toString() {
         return Integer.toString(data);
      }
   }
   
   Node head;
   Node tail;
   int length;
   int cursorIndex;
   Node cursor;
   
   // Constructor for list
   List() { // Creates a new empty list.
      clear();
   }
   
   // Access functions
   
   int length() { // Returns the number of elements in this list
      return length;
   }
   
   int index() { // If cursor is defined, returns the index of the cursor element.
               // otherwise returns -1
      return cursorIndex;
   }
   
   int front() { // Returns front element. Pre: length() > 0
      if (length() > 0) {
         return head.data;
      }
      else throw new RuntimeException();
   }
   
   int back() { // Returns back element. Pre: length() > 0
      if (length() > 0) {
         return tail.data;
      }
      else throw new RuntimeException();
   }
   
   int get() { // Returns cursor element. Pre: length() > 0, index() >= 0
      if (length() > 0 && cursorIndex >= 0) {
         return cursor.data;
      }
      else throw new RuntimeException();
   }
   
   boolean equals(List L) { // Returns true if this List and L are the same integer
                            // sequence. The cursor is ignored in both lists.
      // Loop through this list and compare each element to each element of L.
      // If any are different in length or data, return false.
      // Else, return true.
      int position = 0;
      if (L.length() == length) {
         for (Node H = head; H != null; H = H.next) {
            if (H.data == L.getByPos(position)) {
               position++;
            }
            else {
               return false;
            }
         }
         return true;
      }
      return false;
   }
   
   int getByPos(int pos) {
      int sum = 0;
      for (Node J = head; J != null; J = J.next) {
         if (sum == pos) {
            return J.data;
         }
         sum++;
      }
      throw new RuntimeException();
   }
   
   // Manipulation procedures

   void clear() { // Resets this List to its original empty state.
      head = null;
      tail = null;
      cursor = null;
      length = 0;
      cursorIndex = -1;
   }
   
   void moveFront() { // If List is non-empty, places the cursor under the front element
                      // otherwise does nothing.
      if (length != 0) {
         cursor = head;
         cursorIndex = 0;
         return;
      }
      else return;
   }
   
   void moveBack() { // If List is non-empty, places the cursor under the back element
                     // otherwise does nothing.
      if (length != 0) {
         cursor = tail;
         cursorIndex = length - 1;
      }
   }  
   
   void movePrev() { // If cursor is defined and not at front, moves cursor one step toward
                     // front of this List, if cursor is defined and at front, cursor becomes
                     // undefined, if cursor is undefined does nothing.
      if (cursor != null) {
         if (cursorIndex != 0) {
            cursor = cursor.prev;
            cursorIndex--;
         }
         else {
            cursor = null;
            cursorIndex = -1;
         }
      }
   }

   void moveNext() { // If cursor is defined and not at back, moves cursor one step toward
                     // back of this List, if cursor is defined and at back, cursor becomes
                     // undefined, if cursor is undefined does nothing.
      if (cursor != null) {
         if (cursorIndex != length-1) {
            cursor = cursor.next;
            cursorIndex++;
         }
         else {
            cursor = null;
            cursorIndex = -1;
         }
      }
   }
   
   void prepend(int data) { // Insert new element into this List. If List is non-empty,
                             // insertion takes place before front element.
      Node newNode = new Node(data);
      newNode.next = head;
      if (length == 0) {
         tail = newNode;
      }
      else {
         head.prev = newNode;
      }
      head = newNode;
      length++;
      if (cursorIndex != -1) {
         cursorIndex++;
      }
   }
   
   void append(int data) { // Insert new element into this List. If List is non-empty,
                           // insertion takes place after back element.
      Node newNode = new Node(data);
      newNode.prev = tail;
      if (length == 0) {
         head = newNode;
      }
      else {
         tail.next = newNode;
      }
      tail = newNode;
      length++;   
   }
   
   void insertBefore(int data) { // Insert new element before cursor.
                                 // Pre: length()>0, index()>=0
      if (length > 0 && cursorIndex >= 0) {
         Node newNode = new Node(data);
         if (cursor.prev == null) {
            newNode.prev = null;
            newNode.next = cursor;
            cursor.prev = newNode;
            head = newNode;
         }
         else {
            Node temp = cursor.prev;
            cursor.prev = newNode;
            newNode.prev = temp;
            newNode.next = cursor;
            temp.next = newNode;
         }
         cursorIndex++;
         length++;
      }
   }
   
   void insertAfter(int data) { // Inserts new element after cursor.
                                // Pre: length()>0, index()>=0
      if (length > 0 && cursorIndex >= 0) {
         Node newNode = new Node(data);
         if (cursor.next == null) {
            newNode.next = null;
            newNode.prev = cursor;
            cursor.next = newNode;
            tail = newNode;
         }
         else {
            Node temp = cursor.next;
            cursor.next = newNode;
            newNode.next = temp;
            newNode.prev = cursor;
            temp.prev = newNode;
         }
         length++;
      }
   }

   void deleteFront() { // Deletes the front element. Pre: length()>0
      if (length > 0) {
         //if the cursor is on the head, make it undefined.
         //else, leave the cursor on the data it is pointing to.
         if (cursor == head) {
            cursor = null;
            cursorIndex = -1;
         }
         else {
            cursorIndex--;
         }
         // store head.next as temp, then delete head
         Node temp = head.next;
         if (temp != null) {
            temp.prev = null;
         }
         else {
            tail = null;
         }
         head = temp;
         length--;
      }
   }

   void deleteBack() { // Deletes the back element. Pre: length()>0
      if (length > 0) {
         //if the cursor is on the tail, make it undefined.
         //else, leave the cursor on the data it is pointing to.
         if (cursor == tail) {
            cursor = null;
            cursorIndex = -1;
         }
         // store tail.prev as temp, then delete tail
         Node temp = tail.prev;
         if (temp != null) {
            temp.next = null;
         }
         else {
            head = null;
         }
         tail = temp;
         length--;
      }
   }

   void delete() { // Deletes cursor element, making cursor undefined.
                   // Pre: length()>0, index()>=0
      if (length > 0 && cursorIndex >= 0) {
         if (cursor != head) {
            if (cursor != tail) { //cursor is on neither head or nor tail
               Node tempA = cursor.prev;
               Node tempB = cursor.next;
               cursor = null;
               cursorIndex = -1;
               tempA.next = tempB;
               tempB.prev = tempA;
            }
            else { //cursor == tail
               Node temp = cursor.prev;
               if (temp != null) {
                  temp.next = null;
               }
               else {
                  head = null;
               }
               cursor = temp;
               cursor = null;
               cursorIndex = -1;
            }
         }
         else { //cursor == head
            Node temp = cursor.next;
            if (temp != null) {
               temp.prev = null;
            }
            else {
               tail = null;
            }
            cursor = temp;
            cursor = null;
            cursorIndex = -1;
         }
         length--;
      }
   }


   // Other methods
   
   public String toString() { // Overrides Object's toString method. Returns a String
                              // representation of this List consisting of a space
                              // separated sequence of integers, with front on left.
      String s = "";
      for (Node i = head; i != null; i = i.next) {
         s = s + i.data + " ";
      }
      return s;
   }
   
   List copy() { // Returns a new List representing the same integer sequence as this
                 // List. The cursor in the new list is undefined, regardless of the
                 // state of the cursor in this List. This List is unchanged.
      List newList = new List();
      for(Node H = head; H != null; H = H.next) {
         newList.append(H.data);
      }
      return newList;
   }
   
   List concat(List L) { // Returns a new List which is the concatenation of
                         // this list followed by L. The cursor in the new List
                         // is undefined, regardless of the states of the cursors
                         // in this List and L. The states of this List and L are
                         // unchanged.
      List newList = new List();
      for(Node H = head; H != null; H = H.next) {
         newList.append(H.data);
      }
      for(Node H = L.head; H != null; H = H.next) {
         newList.append(H.data);
      }
      return newList;
   }
}
