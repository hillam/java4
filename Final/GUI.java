import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class GUI extends JPanel{
	private JPanel firstRow = new JPanel();
	private JPanel secondRow = new JPanel();
	private JPanel left = new JPanel();
	private JPanel right = new JPanel();
	private JPanel controlPanel = new JPanel();
	private JPanel tablePanel = new JPanel();

	private JTextField url = new JTextField(42);
	private JButton track = new JButton("Track");
	private JButton refresh = new JButton("Refresh");
	private JButton submit = new JButton("Submit");
	private JButton sort = new JButton("Sort");
	private JComboBox<String> action = new JComboBox<String>();
	private JComboBox<String> sortBy = new JComboBox<String>();

	private GamesTable games = new GamesTable();
	private JTable table = new JTable((TableModel) games);

	public GUI() throws Exception{
		setSize(800,500);
		setLayout(new BorderLayout());

		//set look and feel
		try
		{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {}

		JLabel urlLabel = new JLabel("Paste Steam URL:");
		JLabel actionLabel = new JLabel("Action:");
		JLabel sortLabel = new JLabel("Sort by:");

		action.addItem("Remove Selected");
		action.addItem("Remove All");

		sortBy.addItem("Title");
		sortBy.addItem("Price Increasing");
		sortBy.addItem("Price Decreasing");

		firstRow.add(urlLabel);
		firstRow.add(url);
		firstRow.add(new JLabel());
		firstRow.add(track);
		firstRow.add(refresh);

		left.setLayout(new FlowLayout(FlowLayout.LEADING));
		left.add(new JLabel("     "));
		left.add(new JLabel("     "));
		left.add(new JLabel("     "));
		left.add(new JLabel("     "));
		left.add(new JLabel("  "));
		left.add(actionLabel);
		left.add(action);
		left.add(submit);

		right.setLayout(new FlowLayout(FlowLayout.TRAILING));
		right.add(sortLabel);
		right.add(sortBy);
		right.add(sort);
		right.add(new JLabel("     "));
		right.add(new JLabel());

		secondRow.setLayout(new GridLayout(1,2));
		secondRow.add(left);
		secondRow.add(right);

		controlPanel.setLayout(new GridLayout(2,1));
		controlPanel.add(firstRow);
		controlPanel.add(secondRow);

		table.getColumn("*").setMinWidth(30);
		table.getColumn("*").setMaxWidth(30);
		table.getColumn("Price").setMinWidth(100);
		table.getColumn("Price").setMaxWidth(100);
		table.setRowHeight(20);
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		tablePanel.add(table, BorderLayout.CENTER);

		add(controlPanel,BorderLayout.NORTH);
		add(tablePanel,BorderLayout.CENTER);

		games.addGame(new PriceFetcher("http://store.steampowered.com/app/314160/"));
	}

	public static void main(String[] args) throws Exception{
		JFrame frame = new JFrame("Steam Price Tracker");
		frame.add(new GUI());
		frame.setSize(800,500); //adjust when testing on windows
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}