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
import java.lang.Thread;

public class Blackjack extends JPanel implements MouseListener,ActionListener{

	private ImageIcon background = new ImageIcon("images/background.jpg");
	private ImageIcon b_hit = new ImageIcon("images/hit.jpg");
	private ImageIcon b_stay = new ImageIcon("images/stay.jpg");

	Deck deck = new Deck();
	Hand player = new Hand(true);
	Hand house = new Hand(false);
	boolean winner, stay, valid;
	int cash = 100,
		bet = 1;
	Timer t;

	Blackjack(){
		player.setDeck(deck);
		house.setDeck(deck);

		setSize(800,600);
		addMouseListener(this);
		setVisible(true);

		t = new Timer(1000,this);

		start();
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

		//print buttons at 400,265 and 500,265
		g.setColor(Color.BLACK);
		b_hit.paintIcon(this,g,400,265);
		b_stay.paintIcon(this,g,500,265);

		//if game is won, print result
		if(winner){
			if((player.getScore() > 21 && house.getScore() <= 21) ||
					(player.getScore() < house.getScore() && house.getScore() <= 21)){
				//print house wins
				finish(false);
			}
			else{
				//print house wins
				finish(true);
			}
		}

	}

	private void start(){
		winner = false;
		stay = false;
		valid = false;

		while(!valid){
			bet = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter a betting ammount: " +
				"[1-" + cash + "]"));
			if(bet < cash && bet > 0)
				valid = true;
			else
				JOptionPane.showConfirmDialog(this,"Your bet is invalid.",
					"Invalid",JOptionPane.OK_CANCEL_OPTION);
		}

		t.start();

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
	private void finish(boolean player){
		if(player)
			cash += bet;
		else
			cash -= bet;
		start();
	}

	public void actionPerformed(ActionEvent e){
		//if the player has stayed, the house draws each 'frame'
		if(stay){
			if(house.getScore() < 17 && player.getScore() <= 21)
				house.drawCard();
			else{
				winner = true;
				repaint();
				t.stop();
			}
		}
		else
			repaint();
	}

	public void mousePressed(MouseEvent e){
		if(e.getX() > 400 &&
				e.getX() < (400 + b_hit.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < (265 + b_hit.getIconHeight())&&
				!stay){
			player.drawCard();
			if(player.getScore() > 21)
				stay = true;
			repaint();
		}
		else if(e.getX() > 500 &&
				e.getX() < (500 + b_stay.getIconWidth()) &&
				e.getY() > 265 &&
				e.getY() < (265 + b_stay.getIconHeight())&&
				!stay){
			stay = true;
			repaint();
		}
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

}