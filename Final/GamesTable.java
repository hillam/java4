import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.Collections;
import java.util.ArrayList;
import java.awt.event.*;

public class GamesTable extends AbstractTableModel {
	static final long serialVersionUID = 1;

	private int columns = 2;
	private ArrayList<PriceFetcher> games = new ArrayList<PriceFetcher>();

	public static final int TITLE = 0;
	public static final int PRICE_ASCENDING = 1;
	public static final int PRICE_DESCENDING = 2;

	private JFrame frame;

	public void setFrame(JFrame f){
		frame = f;
	}

	/*--------------------------------------------------------------------------
		LIST STUFF
	--------------------------------------------------------------------------*/

	public void addGame(String url) {
		PriceFetcher p = new PriceFetcher(url);

		//if it worked, add it, and incriment the counter..
		if(p.getPrice() != -1 &&
				!contains(p)){
			games.add(p);
		}
	}

	public void addGame(PriceFetcher p){
		if(!contains(p))
			games.add(p);
	}

	public void removeGame(int index){
		games.remove(index);
	}

	public void removeAll(){
		games.clear();
	}

	public void sort(int param){
		//pass in one of the static constants, and sort accordingly
		switch(param){
			case TITLE:
				Collections.sort(games,PriceFetcher.TITLE_ASC);
				break;
			case PRICE_ASCENDING:
				Collections.sort(games,PriceFetcher.PRICE_ASC);
				break;
			case PRICE_DESCENDING:
				Collections.sort(games,PriceFetcher.PRICE_DESC);
				break;
		}
	}

	public void refresh(){
		JOptionPane optionPane = new JOptionPane("Fetching prices...", 
										JOptionPane.PLAIN_MESSAGE, 
										JOptionPane.DEFAULT_OPTION, 
										null, 
										new Object[]{}, 
										null);

		final JDialog dialog = new JDialog(frame);
		//dialog.setLocationRelativeTo(frame);
		dialog.setModal(true);

		dialog.setContentPane(optionPane);

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();

		Timer timer = new Timer(0, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				for(int i=0;i<games.size();i++){
					games.get(i).initialize();
				}
				dialog.dispose();
			}
		});
		timer.setRepeats(false);

		timer.start();

		dialog.setVisible(true);		
	}

	public boolean contains(PriceFetcher p){
		boolean c = false;
		for(int i=0;i<games.size();i++)
			if(p.compareTo(games.get(i)) == 0)
				c = true;
		return c;
	}

	public ArrayList<PriceFetcher> getGames(){
		return new ArrayList<PriceFetcher>(games);
	}

	/*--------------------------------------------------------------------------
		TABLE STUFF
	--------------------------------------------------------------------------*/

	public int getColumnCount(){
		return columns;
	}

	public int getRowCount(){
		return games.size();
	}

	public String getColumnName(int col){
		String[] names = {"Title","Price"};
		return names[col];
	}

	public Object getValueAt(int row, int col){ 
		if(col == 0)
			return games.get(row).getName();
		else if(col == 1)
			return games.get(row).getPriceString();
		else
			return "Table Error";
	}
}