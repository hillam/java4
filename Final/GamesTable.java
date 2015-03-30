import javax.swing.table.*;
import javax.swing.event.*;

public class GamesTable implements TableModel{

	private int columns = 3;
	private int rows = 18;
	private PriceFetcher[] games = new PriceFetcher[18];
	private int num_games = 0;

	public static final int PRICE_ASCENDING = 0;
	public static final int PRICE_DECENDING = 1;
	public static final int TITLE = 2;

	/*--------------------------------------------------------------------------
		LIST STUFF
	--------------------------------------------------------------------------*/

	public void addGame(PriceFetcher p){
		games[num_games] = p;
		num_games++;
	}

	public void removeGame(int index){
		
	}

	public void removeAll(){
		
	}

	public void sort(int param){
		//pass in one of the static constants, and sort accordingly
	}

	public void refresh(){
		
	}


	/*--------------------------------------------------------------------------
		TABLE STUFF
	--------------------------------------------------------------------------*/

	public int getColumnCount(){
		return columns;
	}

	public int getRowCount(){
		return rows;
	}

	public String getColumnName(int col){
		String[] names = {"*","Title","Price"};
		return names[col];
	}

	public Object getValueAt(int row, int col){ 
		if(row >= num_games)
			return "";
		if(col == 0)
			return "";
		if(col == 1)
			return games[row].getName();
		else if(col == 2)
			return games[row].getPriceString();
		else
			return "Table Error";
	}

	public void setValueAt(Object o,int row,int col){
		//update table here?
	}

	public void addTableModelListener(TableModelListener l){}
	public void removeTableModelListener(TableModelListener l){}
	public boolean isCellEditable(int row,int col){ return false; }

	public Class<?> getColumnClass(int col){ return (new PriceFetcher()).getClass(); }
}