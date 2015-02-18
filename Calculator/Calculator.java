import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;

public class Calculator extends JPanel implements ActionListener{

	public static final long serialVersionUID = 1;

	JPanel container = new JPanel();

	JTextField display = new JTextField(15);
	JPanel b_panel = new JPanel();
	JButton numbers[] = new JButton[12];
	JPanel num_panels[] = new JPanel[12];
	JButton operations[] = new JButton[4];
	JPanel op_panels[] = new JPanel[4];
	JPanel spacer = new JPanel();

	Calculator(){
		container.setLayout(new BorderLayout());
		b_panel.setLayout(new BorderLayout());

		container.setSize(300,300);
		addDisplay();
		addNumbers();
		addOperations();
		container.add(b_panel,BorderLayout.CENTER);

		this.add(container);
	}

	private void addDisplay(){
		display.setEditable(false);
		display.setBackground(Color.WHITE);
		display.setHorizontalAlignment(JTextField.RIGHT);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200,50));
		panel.setLayout(new BorderLayout());
		panel.add(spacer,BorderLayout.NORTH);
		panel.add(display,BorderLayout.SOUTH);
		container.add(panel,BorderLayout.NORTH);
	}

	private void addNumbers(){
		String[] nums = {"7","8","9","4","5","6","1","2","3","0",".","="};
		for(int i = 0;i < numbers.length;i++)
			numbers[i] = new JButton(nums[i]);

		for(int i = 0;i < num_panels.length;i++)
			num_panels[i] = new JPanel();

		JPanel gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(150,160));
		JPanel panel = new JPanel();
		gridPanel.setLayout(new GridLayout(4,3));

		for(int i=0;i < numbers.length;i++){
			numbers[i].addActionListener(this);
			num_panels[i].add(numbers[i]);
			gridPanel.add(num_panels[i]);
		}
		panel.add(gridPanel);
		b_panel.add(panel,BorderLayout.WEST);
	}

	private void addOperations(){
		String[] ops = {"/","*","-","+"};
		for(int i = 0;i < operations.length;i++)
			operations[i] = new JButton(ops[i]);

		for(int i = 0;i < op_panels.length;i++)
			op_panels[i] = new JPanel();

		JPanel gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(50,160));
		JPanel panel = new JPanel();
		gridPanel.setLayout(new GridLayout(4,1));

		for(int i=0;i < operations.length;i++){
			operations[i].addActionListener(this);
			op_panels[i].add(operations[i]);
			gridPanel.add(op_panels[i]);
		}
		panel.add(gridPanel);
		b_panel.add(panel,BorderLayout.EAST);
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