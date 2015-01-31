import java.util.Scanner;
import java.lang.Thread;

public class Blackjack{

	public static void main(String[] args) throws InterruptedException{
		Deck deck = new Deck();
		Hand player,house;
		String input = "", str;
		boolean winner, stay, valid;
		Scanner keys = new Scanner(System.in);
		
		// Player data:
		String name = "Player";
		int cash = 100, bet;

		System.out.println("\n*************************");
		System.out.println("* Welcome to Blackjack! *");
		System.out.println("*************************");

		System.out.print("Please enter your name: \n> ");
		name = keys.nextLine();

		System.out.println("\n" + name + "'s cash: $" + cash);

		String again = "y";
		while(again.equals("y")){
			deck.shuffle();

			System.out.print("\nShuffling cards");
			for (int i = 0;i < 7;i++){
				System.out.print("...");
				Thread.sleep(300);
			}
			System.out.print("(done)\n");

			player = new Hand(deck);
			house = new Hand(deck);

			player.drawCard();
			player.drawCard();
			house.drawCard();
			house.drawCard();

			bet = 0;

			if(cash > 0){
				while(bet <= 0 || bet > cash){
					System.out.println("\nPlease enter your bet [1-" + 
						cash + "]");
					System.out.print("> $");
					bet = Integer.parseInt(keys.nextLine());
					if(bet <= 0 || bet > cash)
						System.out.println("\nPlease place your bet within " + 
							"the valid range!\n");
				}
			}

			winner = false;
			while(!winner){
				// 'winner' iff someone busts, or both stay
				stay = true;

				System.out.println("\nHouse hand:\n(House's hole card)\n" + 
					house.toString(1) + "\nHouse score: ***\n");
				System.out.println(name + "'s hand:\n" + player.toString() +
					"\n" + name + "'s score: " + player.getScore());

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

				System.out.println("\n---------------------------------------" +
					"---------------------------------------");

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
			System.out.println("\nHouse hand:\n" + 
				house.toString() + "\nHouse score: " + house.getScore() + 
				"\n");
			System.out.println(name + "'s hand:\n" + player.toString() +
				"\n" + name + "'s score: " + player.getScore() + "\n");

			// PRINT WINNER
			if((player.getScore() > 21 && house.getScore() <= 21) ||
					(player.getScore() < house.getScore() && 
					house.getScore() <= 21)){
				System.out.println("The house wins!");
				cash -= bet;
			}
			else if((house.getScore() > 21 && player.getScore() <= 21) ||
					(house.getScore() < player.getScore() && 
					player.getScore() <= 21)){
				System.out.println("You win!");
				cash += bet;
			}
			else if(house.getScore() == player.getScore()){
				System.out.println("You and the house have the same score, " +
					"so the house wins.");
				cash -= bet;
			}
			else{
				System.out.println("You and the house both have busted hands.");
				cash -= bet;
			}

			System.out.println(name + "'s cash: $" + cash);
			System.out.println(name + "'s bet: $" + bet);

			//Play again?
			if(cash > 0){
				System.out.print("Would you like to play again? (y/n)\n> ");
				again = keys.next();
				keys.nextLine();
			}
			else{
				System.out.println("You're out of money. Please get lost :-)");
				again = "";
			}
		}

		if(cash > 0)
			System.out.println("Thank you for playing :-)\n");
	}
}