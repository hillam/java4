import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GUI extends JPanel implements ActionListener{
	static final long serialVersionUID = 1;

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

		buildUI();

		frame.add(this);
		frame.setSize(800,500); //adjust when testing on windows
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		readFile();

		// save on close
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				int safe = JOptionPane.showConfirmDialog(frame, "Would you like to save your changes?");
				if(safe == JOptionPane.YES_OPTION){
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//yes
					save();
				} else if (safe == JOptionPane.CANCEL_OPTION) {
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//cancel
				} else {
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//no
				}
			}
		});

		games.setFrame(frame);
	}

	public void buildUI(){
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
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		
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
		sort.addActionListener(this);
	}

	public void readFile(){
		try{
			ArrayList<PriceFetcher> data = new ArrayList<PriceFetcher>();
			File file = new File("games.spt");
			if(!file.exists())
				file.createNewFile();
			else
			{
				FileInputStream fis = new FileInputStream("games.spt");
				ObjectInputStream ois = new ObjectInputStream(fis);
				data = (ArrayList<PriceFetcher>) ois.readObject();
				ois.close();
				fis.close();

				for(PriceFetcher p : data)
					games.addGame(p);

				games.refresh();

				int rows = games.getRowCount();
				games.fireTableRowsInserted(rows, rows);
				table.repaint();
				updateUI();
			}

		} catch(Exception e){e.printStackTrace();}
	}

	public void save(){
		try{
			FileOutputStream fos = new FileOutputStream("games.spt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(games.getGames());
			oos.close();
			fos.close();
		} catch(Exception e){};
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
		else if(e.getSource() == sort){
			games.sort(sortBy.getSelectedIndex());
		}

		games.fireTableRowsInserted(rows, rows);
		table.repaint();
		updateUI();
	}

	public static void main(String[] args){
		new GUI();
	}
}