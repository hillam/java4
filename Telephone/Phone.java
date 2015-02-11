import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class Phone extends JPanel implements ActionListener{

	JTextField display = new JTextField(20);

	JButton buttons[] = new JButton[9];

	Phone(){
		display.setEditable(false);

		setSize(300,300);
		BorderLayout border = new BorderLayout();
		setLayout(border);
		addDisplay();
		addButtons();
	}

	private void addDisplay(){
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200,50));
		panel.add(display);
		this.add(panel,BorderLayout.NORTH);
	}

	private void addButtons(){
		for(int i = 0;i < buttons.length;i++)
			buttons[i] = new JButton(i+1+"");

		GridLayout grid = new GridLayout(3,3);
		JPanel gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(200,200));
		JPanel panel = new JPanel();
		gridPanel.setLayout(grid);

		for(JButton b : buttons){
			b.addActionListener(this);
			gridPanel.add(b);
		}
		panel.add(gridPanel);
		this.add(panel,BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e){
		display.setText(display.getText() + ((JButton)e.getSource()).getText());
	}
}