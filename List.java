//An ADT list for a general Object of type T.

public class List <T> implements ListInterface<T> {
   
   private static final int INITIAL_SIZE = 1;
   private Object[] item; // array of list items
   private int numItems;
   
   //constructor for List <T>
   public List() {
      item = new Object[INITIAL_SIZE];
      numItems = 0;
   }
   
   //arrayIndex()
   // transforms a List index to an Array index
   private int arrayIndex(int listIndex){
      return listIndex-1;
   }
   
   //doubleItemArray()
   //doubles the size of the underlying array item[]
   private void doubleItemArray() {
      int doubleLength = item.length * 2;
      Object[] doubledArray = new Object[doubleLength];
      for (int i = 0; i < numItems; i++) {
         doubledArray[i] = item[i];
      }
      item = doubledArray;
   }
   
   // isEmpty()
   // pre: none
   // post: returns true if this List is empty, false otherwise
   @Override
   public boolean isEmpty() {
      return (numItems == 0);
   }
   
   // size()
   // pre: none
   // post: returns the number of elements in this List
   @Override
   public int size() {
      return numItems;
   }
   
   // get()
   // pre: 1 <= index <= size()
   // post: returns item at position index
   @SuppressWarnings("unchecked")
   @Override
   public T get(int index) throws ListIndexOutOfBoundsException {
      if (index < 1 || index > numItems) {
         throw new ListIndexOutOfBoundsException(
               "IntegerList Error: get() called on invalid index");
      }
      return (T) item[arrayIndex(index)];
   }
  
   // add()
   // inserts newItem in this List at position index
   // pre: 1 <= index <= size()+1
   // post: !isEmpty(), items to the right of newItem are renumbered
   @Override
   public void add(int index, T newItem) throws ListIndexOutOfBoundsException {
      if( index<1 || index>(numItems+1) ){
         throw new ListIndexOutOfBoundsException(
            "IntegerList Error: add() called on invalid index");
      }
      
      if( numItems == item.length) {
         doubleItemArray();
      }
      
      for(int i=numItems; i>=index; i--) {
         item[arrayIndex(i+1)] = item[arrayIndex(i)];
      }
      item[arrayIndex(index)] = newItem;
      numItems++;
   }
   
   // remove()
   // deletes item from position index
   // pre: 1 <= index <= size()
   // post: items to the right of deleted item are renumbered
   @Override
   public void remove(int index) throws ListIndexOutOfBoundsException {
      if( index<1 || index>numItems ){
         throw new ListIndexOutOfBoundsException(
            "IntegerList Error: remove() called on invalid index");
      }
      
      for(int i=index+1; i<=numItems; i++){
         item[arrayIndex(i-1)] = item[arrayIndex(i)];
      }
      numItems--;
   }
   
   // removeAll()
   // pre: none
   // post: isEmpty()
   @Override
   public void removeAll() {
      numItems = 0;
   }
   
   // toString()
   // pre: none
   // post:  creates a string containing the current state
   // Overrides Object's toString() method
   public String toString(){
      int i;
      String s = "";

      for(i=0; i<numItems; i++) s += item[i] + " ";
      return s;
   }
}
