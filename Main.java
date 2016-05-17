package main;

import java.io.IOException;
import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) throws IOException
	{
		// Initializes the GUI frame.
		JFrame frame = new Frame();
		frame.setTitle("Tower Builder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
