import java.awt.*;
import javax.swing.*;

public class App {
	private static JFrame frame;
	private static GameEngine ge;
	private static int WIDTH = 800, HEIGHT= 600; 
	public static void main(String [] args) {
		ge = new GameEngine(WIDTH, HEIGHT); 
		initGUI();
		
	}
	public static void initGUI() {
		//add FULLSCREEN?
		frame = new JFrame("Gomoku");
		frame.setSize(WIDTH, HEIGHT);  
	    frame.setAlwaysOnTop(true);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	    frame.add(ge);
		frame.pack();
		frame.setVisible(true);
	}
}
