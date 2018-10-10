/* Guess.java
* Kalinda Fraser
* klfraser
* pa2
* Lets user guess a number between 1 and 10.
*/

public class Guess{
   public static void main(String[] args) {
      //create scanner
      Scanner scan = new Scanner (System.in);
      //create number to be guessed
      int secret = (int)((Math.random() * 10) + 1);
      //ask for first guess
      System.out.println("I'm thinking of an integer in the range 1 to 10.  " +
            "You have three guesses.");
      //create a string array for each of the three guesses
      String[] ordinal = {"first", "second", "third"};
      for (int i=0; i<3; i++) {
         //asks for and stores guess
         System.out.print("Enter your " + ordinal[i] + " guess: ");
         int guess = scan.nextInt();
         //tells user if guess was too high, too low, or correct
         if (guess > secret) {
            System.out.println("Your guess is too high.");
         } else if (guess < secret) {
            System.out.println("Your guess is too low.");
         } else {
            System.out.println("You win!");
            return;
         }
         //prints a blank line
         System.out.println();
      }
      //after the third guess, informs user of loss
      System.out.println("You lose.  The number was " + secret + ".");
   }
}
