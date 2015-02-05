import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class Blackjack extends JPanel implements MouseListener{

	private ImageIcon background = new ImageIcon("images/background.jpg");

	Deck deck = new Deck();
	Hand player = new Hand(true);
	Hand house = new Hand(false);
	boolean winner = false, 
			stay = false;
	int cash = 100,
		bet = 1;

	Blackjack(){
		setSize(800,600);
		addMouseListener(this);
		deck.shuffle();
		player.setDeck(deck);
		house.setDeck(deck);
		play();
	}

	public void paintComponent(Graphics g){
		background.paintIcon(this,g,0,0);
		player.paint(this,g,false);
		house.paint(this,g,!winner);

		//print scores at ~ 335,270 and 335,300
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		g.drawString(house.getScore()+"",335,285);
		g.drawString(player.getScore()+"",335,320);

		//print buttons at 400,265 and 500,265
		g.setColor(Color.BLACK);
		g.fillRect(400,265,95,68);
		g.fillRect(500,265,95,68);
	}

	public void play(){
		deck.shuffle();

		player.reset();
		house.reset();

		player.drawCard();
		player.drawCard();
		house.drawCard();
		house.drawCard();

		repaint();

		System.out.println("\nHouse hand:\n" + 
			house.toString() + "\nHouse score: " + house.getScore() + 
			"\n");
		System.out.println("Player's hand:\n" + player.toString() +
			"\nPlayer's score: " + player.getScore() + "\n");
	}

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

}