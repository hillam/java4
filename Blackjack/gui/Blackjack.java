import javax.swing.JPanel;

public class Blackjack extends JPanel{

	Deck deck = new Deck();
	Hand player = new Hand(true);
	Hand house = new Hand(false);
	boolean winner = false, stay = false;

	Blackjack(){

	}
}