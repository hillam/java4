import javax.swing.table.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class GamesTable extends AbstractTableModel {

	private int columns = 2;
	private ArrayList<PriceFetcher> games = new ArrayList<PriceFetcher>();
	private int num_games = 0;

	public static final int PRICE_ASCENDING = 0;
	public static final int PRICE_DECENDING = 1;
	public static final int TITLE = 2;

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

	public void removeGame(int index){
		games.remove(index);
	}

	public void removeAll(){
		games.clear();
	}

	public void sort(int param){
		//pass in one of the static constants, and sort accordingly
	}

	public void refresh(){
		for(int i=0;i<games.size();i++){
			games.get(i).initialize();
		}
	}

	public boolean contains(PriceFetcher p){
		boolean c = false;
		for(int i=0;i<games.size();i++)
			if(p.compareTo(games.get(i)) == 0)
				c = true;
		return c;
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