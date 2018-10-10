/* LetterHome.java
* Kalinda Fraser
* klfraser
* lab5
* Generates a letter based on a template and a data file.
* In the original version, there were three errors:
* - A "greater than or equal to" (>=) which should have been "strict greater than" (>) on line 126 causes some valid sentences to be read as invalid.
* - An extra semicolon on line 166 causes the "else" clause to activate in all cases except when the variable is 3 ("money").
* - An extra semicolon on line 185 causes the word to be "foggy" if it would otherwise be "great" by disabling the "if" that would ordinarily prevent that.
* (Line numbers reference the original file.)
*/
class LetterHome{

   static final int MAX_CODE = 5;

   public static void main(String[] args) throws Exception{
      Scanner in = new Scanner(new File(args[0]));
      String phrase;
      int sentenceCode, modifierCode;

      // print heading
      System.out.println("Dear Mom and Dad:\n");

      // print encoded sentences
      while( in.hasNext() ){

         // read a pair of sentence and modifier codes
         sentenceCode = in.nextInt();
         modifierCode = in.nextInt();

         // make sure the sentenceCode is within range
         if( (sentenceCode < 1) || (sentenceCode > MAX_CODE) ) {
            System.out.println(sentenceCode + " is not a valid sentence code");
            continue;
         }

         // print the appropriate sentence with modifier
         if( sentenceCode == 1 ){
            if( modifierCode == 1 ){
               phrase = "great";
            }else if( modifierCode == 2 ){
               phrase = "ok";
            }else{
               phrase = "ERROR";
            }
            System.out.println("My classes are going " + phrase + ".");

         }else if( sentenceCode == 2 ){
            phrase = weatherModifier(modifierCode);
            System.out.println("The weather here has been " + phrase + ".");

         }else if( sentenceCode == 3 ){
            if( modifierCode == 1 ){
               phrase = "after the quarter ends";
            }else if( modifierCode == 2 ){
               phrase = "in a few weeks";
            }else if( modifierCode == 3 ){
               phrase = "next weekend";
            }else{
               phrase = "ERROR";
            }
            System.out.println("I plan to come home for a visit " + phrase + ".");

         }else if( sentenceCode == 4 ){
            System.out.println("Do you think you could send me $" + modifierCode + "?");
            System.out.println("I have to buy another book for one of my classes.");

         }else if( sentenceCode == 5 ){
            if( modifierCode == 1 ){
               phrase = "cookies";
            }else if( modifierCode == 2 ){
               phrase = "stuff";
            }else if( modifierCode == 3 ){
               phrase = "money";
            }else{
               phrase = "ERROR";
            }
            System.out.println("Thanks for the " + phrase + " you sent.");
         }
      }
   }

   // weatherModifier()
   // returns the correct word for a weather sentence
   static String weatherModifier(int m) {
      String word=null;

      // select weather modifier
      if(m == 1)
         word = "great";
      if(m == 2)
         word = "foggy";
      if(m == 3)
         word = "hot";
      if(m == 4)
         word = "cold";
      if(m == 5)
         word = "variable";
      if( m<1 || m>5)
         word = "ERROR";

      return word;
   }
}
