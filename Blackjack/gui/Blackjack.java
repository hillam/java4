import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Blackjack extends JPanel implements MouseListener,ActionListener{

	static final long serialVersionUID = 1;

	private ImageIcon background = new ImageIcon(getClass().getResource("images/background.jpg"));
	private ImageIcon b_hit = new ImageIcon(getClass().getResource("images/hit.jpg"));
	private ImageIcon b_stay = new ImageIcon(getClass().getResource("images/stay.jpg"));

	private ImageIcon b_start = new ImageIcon(getClass().getResource("images/start.jpg"));
	private ImageIcon b_win = new ImageIcon(getClass().getResource("images/win.jpg"));
	private ImageIcon b_lose = new ImageIcon(getClass().getResource("images/lose.jpg"));

	Deck deck = new Deck();
	Hand player = new Hand(true);
	Hand house = new Hand(false);
	boolean winner = false, stay = false;
	boolean start_active = true; //determine if start button is active
	int cash = 100,
		bet = 10;
	Timer t;

	Blackjack(){
		player.setDeck(deck);
		house.setDeck(deck);

		setSize(800,600);
		addMouseListener(this);
		setVisible(true);

		t = new Timer(800,this);
	}

	public void paintComponent(Graphics g){
		background.paintIcon(this,g,0,0);
		player.paint(this,g,false);
		house.paint(this,g,!stay);

		//print scores at ~ 335,270 and 335,300
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		g.drawString(player.getScore()+"",335,320);
		if(stay)
			g.drawString(house.getScore()+"",335,290);
		else
			g.drawString("???",335,290);

		//print cash
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
		g.drawString("Cash: $"+cash,20,580);

		//print bet
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
		g.drawString("Bet: $"+bet,300,580);

		//if game is won, print result
		if(winner){
			if(player.getScore() > 21 || player.getScore() == house.getScore() ||
					(player.getScore() < house.getScore() && house.getScore() <= 21)){
				b_lose.paintIcon(this,g,400,265);
				finish(false);
			}
			else{
				b_win.paintIcon(this,g,400,265);
				finish(true); 
			}
		}
		else{
			//print buttons at 400,265 and 500,265
			b_hit.paintIcon(this,g,400,265);
			b_stay.paintIcon(this,g,500,265);
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
					bet = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter a betting ammount: " +
						"[1-" + cash + "]"));
				}
				catch(Exception e){
					bet = -1;
				}
				if(bet <= cash && bet > 0)
					valid = true;
				else
					JOptionPane.showConfirmDialog(this,"Your bet is invalid.",
						"Invalid",JOptionPane.OK_CANCEL_OPTION);
			}
		}
		else{
			JOptionPane.showConfirmDialog(this,"You are out of money. Please get lost :-)",
				"Broke",JOptionPane.OK_CANCEL_OPTION);
			System.exit(0);
		}
		start();
	}

	private void start(){
		t.start();
		
		winner = false;
		stay = false;

		deck.shuffle();

		player.reset();
		house.reset();

		player.drawCard();
		player.drawCard();
		house.drawCard();
		house.drawCard();

		repaint();
	}

	//player is true if the player won
	private void finish(boolean p){
		if(p)
			cash += bet;
		else
			cash -= bet;
		t.stop();
	}

	public void actionPerformed(ActionEvent e){
		//if the player has stayed, the house draws each 'frame'
		if(stay){
			if(house.getScore() < 17 && player.getScore() < 22){
				house.drawCard();
			}
			else{
				winner = true;
			}
		}
		
		repaint();
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
		//clicked hit
		else if(e.getX() > 400 &&
				e.getX() < (400 + b_hit.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < (265 + b_hit.getIconHeight())&&
				!stay){
			player.drawCard();
			if(player.getScore() > 21)
				stay = true;
			repaint();
		}
		//clicked stay
		else if(e.getX() > 500 &&
				e.getX() < (500 + b_stay.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < (265 + b_stay.getIconHeight())&&
				!stay){
			stay = true;
			repaint();
		}
		//clicked win/lose
		else if(e.getX() > 400 &&
				e.getX() < (400 + b_win.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < (265 + b_win.getIconHeight())&&
				winner){
			bet();
		}
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}