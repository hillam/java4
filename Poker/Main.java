import javax.swing.JFrame;

public class Main{
	public static void main(String[] args){
		JFrame frame = new JFrame("Three Card Brag");
		Poker bj = new Poker();
		frame.add(bj);
		frame.setSize(800,628); //adjust when testing on windows
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}