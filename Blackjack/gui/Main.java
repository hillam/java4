import javax.swing.JFrame;

public class Main{
	public static void main(String[] args){
		JFrame frame = new JFrame("Blackjack");
		Blackjack bj = new Blackjack();
		frame.add(bj);
		frame.setVisible(true);
	}
}