import java.util.Scanner;

public class Blackjack{

	public static void main(String[] args){
		Deck deck = new Deck();
		Hand player,house;
		String input = "", str;
		boolean winner, stay, valid;
		Scanner keys = new Scanner(System.in);

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

			System.out.println("\n*********************");
			System.out.println("Welcome to Blackjack!");
			System.out.println("*********************");

			winner = false;
			while(!winner){
				// 'winner' iff someone busts, or both stay
				stay = true;

				System.out.println("\nHouse hand:\n(House's hole card)\n" + 
					house.toString(1) + "\nHouse score: ***\n");
				System.out.println("Your hand:\n" + player.toString() +
					"\nYour score: " + player.getScore());

				// PLAYER CHOOSES
				valid = false;
				while(!valid){
					if(player.getScore() <= 21){
						System.out.print("You may (h)it or (s)tay.\n> ");
						input = keys.next();

						if(input.equals("h")){
							player.drawCard();
							valid = true;
							stay = false;
						}
						else if(input.equals("s")){
							valid = true;
						}
						else
							System.out.println("Invalid input. Try again.");
					}
					else{
						System.out.print("You have a busted hand. " +
							"(Press enter...)\n> ");
						keys.nextLine();
						keys.nextLine();

						valid = true;
						winner = true;
					}
				}

				System.out.println("\n***************************************");

				// Pseudo-AI
				if(house.getScore() <= 17 && player.getScore() <= 21){
					System.out.println("\nThe house hits");
					house.drawCard();
					stay = false;
				}
				else{
					System.out.println("\nThe house stays");
				}

				if(stay)
					winner = true;
			}

			// FINAL 'SCORE'
			System.out.println("\nHouse hand:\n(House's hole card)\n" + 
				house.toString() + "\nHouse score: " + house.getScore() + 
				"\n");
			System.out.println("Your hand:\n" + player.toString() +
				"\nYour score: " + player.getScore() + "\n");

			// PRINT WINNER
			if((player.getScore() > 21 && house.getScore() <= 21) ||
					(player.getScore() < house.getScore() && 
					house.getScore() <= 21))
				System.out.println("The house wins!");
			else if((house.getScore() > 21 && player.getScore() <= 21) ||
					(house.getScore() < player.getScore() && 
					player.getScore() <= 21))
				System.out.println("You win!");
			else
				System.out.println("You and the house both have busted hands.");

			//Play again?
			System.out.print("Would you like to play again? (y/n)\n> ");
			again = keys.next();
		}
	}
}