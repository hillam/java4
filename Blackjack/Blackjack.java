import java.util.Scanner;

public class Blackjack{

	public static void main(String[] args){
		Deck deck = new Deck();
		Hand player,house;
		String input = "";
		boolean valid;
		Scanner keys = new Scanner(System.in);
		//System.out.println(deck.drawCard().getName());

		String again = "y";
		while(again.equals("y")){
			deck.shuffle();
			player = new Hand();
			player.setDeck(deck);
			house = new Hand();
			house.setDeck(deck);

			player.drawCard();
			player.drawCard();
			house.drawCard();
			house.drawCard();

			System.out.println("Welcome to Blackjack!");

			while(true){
				System.out.println("\nHouse hand:\n" + player.toString() +
					"\nHouse score: " + house.getScore() + "\n");
				System.out.println("Your hand:\n" + player.toString() +
					"\nYour score: " + player.getScore());

				valid = false;
				while(!valid){
					if(player.getScore() < 21){
						System.out.print("You may (h)it or (s)tay.\n> ");
					
						input = keys.next();

						if(input.equals("h")){
							player.drawCard();
							valid = true;
						}
						else if(input.equals("s"))
							valid = true;
						else
							System.out.println("Invalid input. Try again.");
					}
					//Other scenarios..
				}
			}

			//Play again?
			//System.out.print("Would you like to play again? (y/n)\n> ");
			//again = keys.next();
		}
	}
}