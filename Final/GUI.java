import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JPanel implements ActionListener{
	private JFrame frame = new JFrame("Steam Price Tracker");

	private JPanel firstRow = new JPanel();
	private JPanel secondRow = new JPanel();
	private JPanel left = new JPanel();
	private JPanel right = new JPanel();
	private JPanel controlPanel = new JPanel();
	private JPanel tablePanel = new JPanel();

	private JTextField url = new JTextField(40);
	private JButton track = new JButton("Track");
	private JButton refresh = new JButton("Refresh");
	private JButton submit = new JButton("Submit");
	private JButton sort = new JButton("Sort");
	private JComboBox<String> action = new JComboBox<String>();
	private JComboBox<String> sortBy = new JComboBox<String>();

	private GamesTable games = new GamesTable();
	private JTable table = new JTable((TableModel) games);

	public GUI(){
		setSize(800,500);
		setLayout(new BorderLayout());

		//set look and feel
		try
		{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {}

		JLabel urlLabel = new JLabel("Paste Steam URL:");
		JLabel actionLabel = new JLabel("Action:");
		JLabel sortLabel = new JLabel("Sort by:");

		url.setFont(new Font("Sans-Serif", Font.PLAIN, 16));

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
		left.add(new JLabel("    "));
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

		table.getColumn("Price").setMinWidth(100);
		table.getColumn("Price").setMaxWidth(100);
		table.setRowHeight(30);
		table.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		tablePanel.add(table, BorderLayout.CENTER);

		JPanel scrollPanel = new JPanel();

		JScrollPane scroll = new JScrollPane();
		//scroll.setPreferredSize(new Dimension(355,540));
		//scroll.setBackground(new Color(0,0,0,0));
		
		scrollPanel.setLayout(new BorderLayout());
		scrollPanel.setBackground(new Color(0,0,0,0));
		
		//mainPanel.setLayout(new GridBagLayout());
		//mainPanel.setBackground(new Color(0,0,0,0));

		scrollPanel.add(tablePanel,BorderLayout.NORTH);
		scroll.setViewportView(scrollPanel);

		add(controlPanel,BorderLayout.NORTH);
		add(scroll,BorderLayout.CENTER);

		//add action listeners
		track.addActionListener(this);
		refresh.addActionListener(this);
		submit.addActionListener(this);

		frame.add(this);
		frame.setSize(800,500); //adjust when testing on windows
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		int rows = games.getRowCount();

		if(e.getSource() == track && url.getText().compareTo("") > 0){
			games.addGame(url.getText());
			url.setText("");
		}
		else if(e.getSource() == refresh){
			games.refresh();
		}
		else if(e.getSource() == submit){
			if(action.getSelectedIndex() == 0){
				int[] selected = table.getSelectedRows();
				for(int i=selected.length-1;i>=0;i--)
					games.removeGame(selected[i]);
			}
			else
				games.removeAll();

			rows = games.getRowCount();
		}

		games.fireTableRowsInserted(rows, rows);
		table.repaint();
		updateUI();
	}

	public static void main(String[] args){
		new GUI();
	}
}