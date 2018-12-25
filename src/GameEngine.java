//Classe Test
//Non ho tempo, poi faccio con calma, volevo farla semplice
//Mettere classe board con draw easy, matrice sotto, classe cell per comunicare con embasp
//ho messo già le librerie

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  
public class GameEngine {
	
	public static void main(String[] args) {
	    JFrame frame = new JFrame();  
	    frame.setResizable(false);
		frame.setSize(800,600);  
	    frame.setLayout(null);  
	    frame.setVisible(true);
	    frame.setTitle("Gomoku");
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    
	    frame.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) { 
	          System.out.println("Pressed: " + "[ " + me.getX() + ", " + me.getY() + " ]");
	        }
	    });
	 }
	

}


