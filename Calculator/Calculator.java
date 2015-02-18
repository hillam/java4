import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class Calculator extends JPanel implements ActionListener{

	public static final long serialVersionUID = 1;

	JPanel container = new JPanel();

	JTextField display = new JTextField(15);
	JButton numbers[] = new JButton[10];
	JPanel b_panels[] = new JPanel[12];
	JPanel spacer = new JPanel();

	Calculator(){
		display.setEditable(false);

		container.setLayout(new BorderLayout());

		container.setSize(300,300);
		addDisplay();
		addNumbers();

		this.add(container);
	}

	private void addDisplay(){
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200,50));
		panel.add(display);
		container.add(panel,BorderLayout.NORTH);
	}

	private void addNumbers(){
		String[] nums = {"7","8","9","4","5","6","1","2","3","0"};
		for(int i = 0;i < numbers.length;i++)
			numbers[i] = new JButton(nums[i]);

		for(int i = 0;i < b_panels.length;i++)
			b_panels[i] = new JPanel();

		GridLayout grid = new GridLayout(4,3);
		JPanel gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(160,160));
		JPanel panel = new JPanel();
		gridPanel.setLayout(grid);

		for(int i=0;i < numbers.length;i++){
			numbers[i].addActionListener(this);
			b_panels[i].add(numbers[i]);
			gridPanel.add(b_panels[i]);
		}
		panel.add(gridPanel);
		container.add(panel,BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e){
		display.setText(display.getText() + ((JButton)e.getSource()).getText());
	}

	public static void main(String[] args){
		Calculator c = new Calculator();
		JFrame frame = new JFrame("Calculator");
		frame.add(c);
		frame.setSize(300,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}