import javax.swing.JFrame;

public class Main{
	public static void main(String[] args){
		Phone ph = new Phone();
		JFrame frame = new JFrame("Telephone");
		frame.add(ph);
		frame.setSize(300,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}