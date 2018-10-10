//-----------------------------------------------------------------------------
//Dictionary.java
//Binary Search Tree implementation of the Dictionary ADT
//-----------------------------------------------------------------------------

public class Dictionary implements DictionaryInterface {
   
   //Definition of Node
   private class Node {
      String key;
      String value;
      Node left;
      Node right;
      Node (String k, String v) {
         key = k;
         value = v;
         left = null;
         right = null;
      }
   }
   
   private int numPairs; //number of key/value pairs in the tree
   private Node root; //root of the tree
  

   // findKey()
   // returns the Node containing key k in the subtree rooted at R, or returns
   // null if no such Node exists
   private Node findKey(Node R, String k) {
      if (R == null || k.compareTo(R.key) == 0) {
         return R;
      }
      else if (k.compareTo(R.key) < 0) {
         return findKey(R.left, k);
      }
      else { //k.compareTo(R.key) > 0)
         return findKey(R.right, k);
      }
   }

   // findParent()
   // returns the parent of N in the subtree rooted at R, or returns null 
   // if N is equal to R. (pre: R != null)
   Node findParent(Node N, Node R) {
      Node P = null;
      if (N != R) {
         P = R;
         while ((P.left != N) && (P.right != N)) {
            if (N.key.compareTo(P.key) < 0) {
               P = P.left;
            }
            else
               P = P.right;
         }
      }
      return P;
   }
   
   // findLeftmost()
   // returns the leftmost Node in the subtree rooted at R, or null if R is null.
   Node findLeftmost (Node R) {
      if (R == null) {
         return R;
      }
      return findLeftmost(R.left);
   }
   
   // returns a string in order by location in the tree
   
   String stringInOrder(Node R) {
      String s = "";
      if (R != null) {
         s += stringInOrder(R.left);
         s += R.key+ " " +R.value+ "\n";
         s += stringInOrder(R.right);
      }
      return s;
   }
   
   public String toString () {
      return stringInOrder(root);
   }
   
   // deleteAll()
   // deletes all Nodes in the subtree rooted at N.
   void deleteAll (Node N) {
      if (N != null) {
         deleteAll(N.left);
         deleteAll(N.right);
      }
   } 
   
   // public functions -----------------------------------------------------------
   
   // Dictionary()
   // constructor for the Dictionary type

   public Dictionary() {
      root = null;
      numPairs = 0;
   }
   
   // isEmpty()
   // returns 1 (true) if D is empty, 0 (false) otherwise
   // pre: none
   public boolean isEmpty() {
      return (numPairs == 0);
   }
   
   // size()
   // returns the number of (key, value) pairs in D
   // pre: none
   public int size() {
      return(numPairs);
   }
   
   // lookup()
   // returns the value v such that (k, v) is in D, or returns NULL if no 
   // such value v exists.
   // pre: none
   public String lookup(String k) {
      Node N;
      N = findKey(root, k);
      return (N == null ? null : N.value);
   }
   
   // insert()
   // inserts new (key,value) pair into D
   // pre: lookup(k) = null
   public void insert(String k, String v) throws DuplicateKeyException {
      Node N, A, B;
      if (findKey(root, k) != null) {
         throw new DuplicateKeyException ("Dictionary Error: cannot insert() duplicate key: " + k);
      }
      N = new Node(k, v);
      B = null;
      A = root;
      while (A != null) {
         B = A;
         if (k.compareTo(A.key) < 0) {
            A = A.left;
         }
         else A = A.right;
      }
      if (B == null) {
         root = N;
      }
      else if (k.compareTo(B.key) < 0) {
         B.left = N;
      }
      else {
         B.right = N;
      }
      numPairs++;
   }
   
   // delete()
   // deletes pair with the key k
   // pre: lookup(D, k) != null
   public void delete (String k) throws KeyNotFoundException {
      Node N, P, S;
      N = findKey(root, k);
      if (N == null) {
         throw new KeyNotFoundException ("Dictionary Error: cannot delete() non-existent key: " + k);
      }
      if (N.left == null && N.right == null) {  // case 1 (no children)
         if (N == root){
            root = null;
         } else {
            P = findParent(N, root);
            if (P.right == N) {
               P.right = null;
            }
            else {
               P.left = null;
            }
         }
      } else if (N.right == null) {  // case 2 (left but no right child)
         if (N == root) {
            root = N.left;
         } else {
            P = findParent(N, root);
            if (P.right == N) {
               P.right = N.left;
            }
            else {
               P.left = N.left;
            }
         }
      } else if (N.left == null) {  // case 2 (right but no left child)
         if (N == root) {
            root = N.right;
         } else {
            P = findParent(N, root);
            if (P.right == N) {
               P.right = N.right;
            }
            else {
               P.left = N.right;
            }
         }
      } else {                     // case3: (two children: N->left!=NULL && N->right!=NULL)
         S = findLeftmost(N.right);
         N.key = S.key;
         N.value = S.value;
         P = findParent(S, N);
         if (P.right == S) {
            P.right = S.right;
         }
         else {
            P.left = S.right;
         }
      }
      numPairs--;
   }
   
   // makeEmpty()
   // re-sets D to the empty state.
   // pre: none
   public void makeEmpty() {
      deleteAll(root);
      root = null;
      numPairs = 0;
   }
}
