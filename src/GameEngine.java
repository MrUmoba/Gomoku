//Classe Test
//Non ho tempo, poi faccio con calma, volevo farla semplice
//Mettere classe board con draw easy, matrice sotto, classe cell per comunicare con embasp
//ho messo già le librerie

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  
public class GameEngine extends JPanel{
	//Gomoku Matrix of matrix
	private Ball [][] matrix;
	//PosXY mouse
	private int posX, posY;
	//Dimension
	private int width,height,numCells;
	private int player = 0;
	
	public GameEngine(int WIDTH, int HEIGHT) {
		super();
		this.numCells = 15;
		matrix = new Ball[numCells][numCells];
		this.width = WIDTH;
		this.height = HEIGHT;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		initEH();
		//stampaMatrix();
			
	}
	public void stampaMatrix() {
		System.out.println("---------------------------------------------------------------------");
		for(int i = 0; i < numCells; i++) {
			for(int j = 0; j < numCells; j++ ) {
				if(matrix[i][j] == null)
					System.out.print(matrix[i][j] + " ");
				else
					System.out.print(matrix[i][j].getColor() + " ");
			}
			System.out.println();
		}

		System.out.println("---------------------------------------------------------------------");
	}
	
	public void initEH() {

		//Add balls to matrix & facts to ASP
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) { 
				//calculate posx,posy in matrix mode.
				posX = (me.getX()- width/4) / ((width-(width/4))/numCells);
				posY = (me.getY()) / (height/numCells);
				
				//System.out.println("[" + posX + " " + posY + "]");
				
				//if matrice vuota nel punto inserisci e switcha giocatore
				if(matrix[posY][posX] == null) {
					matrix[posY][posX] = new Ball(player);
					player++;
					player %= 2;
					repaint();//no timer
					stampaMatrix();
				}
				
				/*if(hasWon()){
					System.out.println("Ha vinto il giocatore: " + player);
				}*/
			}
		});
}

	public void paintComponent(Graphics g) {
		//entro tre volte a gioco partito, problema
		super.paintComponent(g);
		drawHUD(g);
	}
	
	public void drawHUD(Graphics g) {
		
					//GAME INFO
		g.setColor(new Color(255, 0, 0));
		g.drawString("Player: " + (player+1) , 10, height/2);
		
					//GAME GRID
		g.setColor(new Color(0, 0, 0));
		//HORIZONTAL
		for(int a = 0; a <= height; a += height/numCells) {
			g.drawLine(width/4, a, width, a);
		}
		//VERTICAL
		for(int b = width/4; b <= width; b += (width-(width/4))/numCells) {
			g.drawLine(b, 0, b, height);
		}

	}
	public boolean hasWon() {
		//5 on the same row
		//5 on the same column
		//5 on first 
		//5 on second
		return false;
	}
}

