import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class BoardClass {
	private  int BOARD_SIZE = 15;
	private  JButton buttons[] = new JButton[225]; // create 225 buttons

	private  void gamePanel()
	{
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creates a panel with a box like a Tic-Tac-Toe board
		JPanel panel = new JPanel(); 
		panel.setLayout(new GridLayout(15, 15));
		panel.setBorder(BorderFactory.createLineBorder(Color.gray, 3));
		panel.setBackground(Color.white);

		/*
		 * Place buttons on the board
		 * Each tile is a button
		 */
		for (int i = 0; i <= 224; i++)
		{ 
			buttons[i] = new MyButton();
			panel.add(buttons[i]);
		}

		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		
		// set frame size and start the game
		frame.setSize(500, 500);
	}
	
	

	public  int letterCount = 0; // used to count letters and keep track of who plays next

	
	/*
	 * The following code creates a customized button class,
	 * based on the JButton class of the Java Swing library 
	 */
	@SuppressWarnings("serial")
	private  class MyButton extends JButton implements ActionListener
	{
		boolean win = false; // there is not a win


		public MyButton()
		{ // creating blank board
			super();
			setFont(new Font(Font.SERIF,Font.BOLD, 125));
			setText(" ");
			addActionListener(this);
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	
	/*
	 * Clear all 8 buttons
	 */
	public void clearButtons()
	{
		for (int i = 0; i <= 224; i++)
		{
			buttons[i].setText(" ");
		}
		
		letterCount = 0; // reset the count
	}
	
	
	/*
	 * Scans the board and updates the state to record a new move
	 */
	private String[] getBoardState()
	{
		String[] boardState = new String[BOARD_SIZE * BOARD_SIZE];
		
		for (int tileNum = 0; tileNum < BOARD_SIZE*BOARD_SIZE; tileNum++)
		{
			boardState[tileNum] = buttons[tileNum].getText()	== " " ? "-" : buttons[tileNum].getText();
		}

		return boardState;
	}


	
	

}
