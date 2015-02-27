import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Poker extends JPanel implements MouseListener{

	static final long serialVersionUID = 1;

	private ImageIcon background = new ImageIcon(getClass().getResource("images/background.jpg"));
	private ImageIcon b_ride = new ImageIcon(getClass().getResource("images/ride.jpg"));
	private ImageIcon b_fold = new ImageIcon(getClass().getResource("images/fold.jpg"));

	private ImageIcon b_rules = new ImageIcon(getClass().getResource("images/b_rules.jpg"));
	private ImageIcon overlay = new ImageIcon(getClass().getResource("images/rules.png"));
	private ImageIcon b_start = new ImageIcon(getClass().getResource("images/start.jpg"));
	private ImageIcon b_again = new ImageIcon(getClass().getResource("images/playagain.jpg"));

	Deck deck = new Deck();
	Hand player = new Hand(true);
	Hand house = new Hand(false);
	boolean winner = false;
	boolean start_active = true; //determine if start button is active
	boolean rules = false; //for toggling rules overlay
	int cash = 100,
		ante = 0,
		call = 0;

	Poker(){
		player.setDeck(deck);
		house.setDeck(deck);

		setSize(800,600);
		addMouseListener(this);
		setVisible(true);
	}

	public void paintComponent(Graphics g){
		background.paintIcon(this,g,0,0);
		player.paint(this,g,false);
		house.paint(this,g,!winner);

		//print cash
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
		g.setColor(Color.BLACK);
		g.drawString("Cash: $"+cash,20,580);
		g.drawString("Ante: $"+ante,300,580);
		g.drawString("Call: $"+call,580,580);

		b_rules.paintIcon(this,g,205,265);
		if(rules)
			overlay.paintIcon(this,g,0,0);

		//if hand is finished, print play again button
		if(winner){
			b_again.paintIcon(this,g,400,265);
		}
		else{
			//print buttons at 400,265 and 500,265
			b_ride.paintIcon(this,g,400,265);
			b_fold.paintIcon(this,g,500,265);
		}

		if(start_active)
			b_start.paintIcon(this,g,203,262);
	}

	//call this instead of start() to start
	private void bet(){
		boolean valid = false;

		if(cash > 0){
			while(!valid){
				try{
					ante = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter a betting ammount: " +
						"[1-" + cash + "]"));
				}
				catch(Exception e){
					ante = 0;
				}
				if(ante <= cash && ante > 0)
					valid = true;
				else{
					ante = 0;
					JOptionPane.showConfirmDialog(this,"Your bet is invalid.",
						"Invalid",JOptionPane.OK_CANCEL_OPTION);
				}
			}

			cash -= ante;
		}
		else{
			JOptionPane.showConfirmDialog(this,"You are out of money. Please get lost :-)",
				"Broke",JOptionPane.OK_CANCEL_OPTION);
			System.exit(0);
		}
		start();
	}

	private void start(){		
		winner = false;

		deck.shuffle();

		player.reset();
		house.reset();

		player.drawCard(3);
		house.drawCard(3);

		//System.out.println(player.getScore());

		repaint();
	}

	private void finish(){
		winner = true;
		if(house.getScore() < 12){
			ante = 2 * ante;
		}
		else if(player.getScore() == house.getScore()){

		}
		else if(player.getScore() > house.getScore()){
			ante = ante * 2;
			call = call * 2;
		}
		else if(player.getScore() < house.getScore()){
			ante = 0;
			call = 0;
		}
		//else.. hands are equal, so do nothing.. ante and call will be 
		//returned when you restart
	}

	public void mousePressed(MouseEvent e){
		//clicked start
		if(e.getX() > 203 &&
				e.getX() < (203 + b_start.getIconWidth()) &&
				e.getY() > 262 &&
				e.getY() < (262 + b_start.getIconHeight())&&
				start_active){
			bet();
			start_active = false;
		}
		//clicked ride
		else if(e.getX() > 400 &&
				e.getX() < (400 + b_ride.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < (265 + b_ride.getIconHeight())&&
				!winner){
			call = ante;
			cash -= call;
			finish();
			repaint();
		}
		//clicked fold
		else if(e.getX() > 500 &&
				e.getX() < (500 + b_fold.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < (265 + b_fold.getIconHeight())&&
				!winner){
			ante = 0;
			finish();
			repaint();
		}
		//clicked play again
		else if(e.getX() > 400 &&
				e.getX() < (400 + b_again.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < (265 + b_again.getIconHeight())&&
				winner){
			cash += ante;
			ante = 0;
			cash += call;
			call = 0;
			repaint();
			bet();
		}
		//clicked rules (205,265)
		else if(e.getX() > 205 &&
				e.getX() < (400 + b_rules.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < 265 + b_rules.getIconHeight()){
			rules = !rules;
			repaint();
		}
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}