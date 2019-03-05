//Classe Test
//Non ho tempo, poi faccio con calma, volevo farla semplice
//Mettere classe board con draw easy, matrice sotto, classe cell per comunicare con embasp
//ho messo già le librerie

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.*;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPFilterOption;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class GameEngine extends JPanel{
	
	//Gomoku Matrix of matrix
	private Ball [][] matrix;
	
	//PosXY mouse
	private int posX, posY;
	private JButton replay;
	//Dimension
	private int width,height,numCells;
	private int player = 0;
	private int winner = -1;
	
	//EmbASP
	private InputProgram facts = null;
	private InputProgram encoding = null;
	private OptionDescriptor filter = null;
	private Handler handler = null;
	private String encodingResource="encodings/vincoliGomoku.txt";
	
	
	public GameEngine(int WIDTH, int HEIGHT) {
		super();
		this.numCells = 15;
		matrix = new Ball[numCells][numCells];
		this.width = WIDTH;
		this.height = HEIGHT;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setBackground(new Color(145, 189, 222));
		
		replay = new JButton("Replay");
		replay.setEnabled(true);
		this.setLayout(null);
		replay.setBounds(10, width/2 + 20, 80, 20);
		this.add(replay);
		
		
		initASP();
		initEH();
		
	}
	public void initASP() {
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		encoding= new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		filter = new OptionDescriptor(new String("--filter=place/2 "));
		
		handler.addProgram(encoding);
		handler.addOption(filter);
	}
	
	
	public void initEH() {
			
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearMatrix();
				winner = -1;
			}
		});
		//Add balls to matrix & facts to ASP
		this.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent me) { 
				if(winner == -1) {
					//calculate posx,posy in matrix mode.
					posX = (me.getX() - width/4); 
					if( posX >= 0) 
						posX /= ((width-(width/4))/numCells);
					posY = (me.getY()) / (height/numCells);
					
					//player 1
					if(player == 0)
						if(posX >=0 && posX < numCells && posY >= 0 && posY < numCells) {
							if(matrix[posY][posX] == null) {
								matrix[posY][posX] = new Ball(player);
								if(hasWon(posY, posX, matrix[posY][posX].getColor())) {
									winner = player+1;
								}
								player++;
								player %= 2;
								repaint();
								
							}
						}
					
					//player 2
					if(player == 1) {
						Place tmp = java_To_ASP();
						if(tmp != null) {
							matrix[tmp.getRow()][tmp.getColumn()] = new Ball(player);
							if(hasWon(tmp.getRow(), tmp.getColumn(), matrix[tmp.getRow()][tmp.getColumn()].getColor())) {
								winner = player+1;
							}
						}	else 
							System.out.println("pareggio, place null");
						
						player++;
						player %= 2;
						repaint();
					}
					
				}
			}
		});
}
	public Place java_To_ASP() {
		
		facts = new ASPInputProgram();
		//Add facts to ASP
		
		for(int i = 0; i < numCells; i++) {
			for(int j = 0; j < numCells; j++) {
				if(matrix[i][j] != null) {
					try {
						facts.addObjectInput(new Cell(i, j, matrix[i][j].getPlayer()+1));
						
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		handler.addProgram(facts);
		
		//Get AS from ASP
		Output o =  handler.startSync();
		
		AnswerSets answers = (AnswerSets) o;
		List<AnswerSet> a = answers.getAnswersets();
		if(a.size() >= 0) {
			for(int i = 0; i < a.size();i++) {
				String tmp = a.get(i).toString();
				int x = Integer.parseInt(tmp.substring(tmp.indexOf('(')+1, tmp.indexOf(',')));
				int y = Integer.parseInt(tmp.substring(tmp.indexOf(',')+1, tmp.indexOf(')')));
				handler.removeProgram(facts);
				return new Place(x,y);
			}
		}
		return null;
	}
	
	public void clearMatrix() {
		matrix = new Ball[numCells][numCells];
		player = 0;
		repaint();
	}
	public void paintComponent(Graphics g) {
		//entro tre volte a gioco partito, problema
		super.paintComponent(g);
		drawHUD(g,winner);
		drawMatrix(g);
	}
	
	public void drawMatrix(Graphics g) {
		for(int i = 0; i < numCells; i++) {
			for(int j = 0; j < numCells; j++ ) {
				if(matrix[i][j] != null) {
					int x = (width/4) + ((width-(width/4))/numCells) * j;
					int y = (height/numCells) * i;
					
					if(matrix[i][j].getColor().equals("nero")) {
						g.setColor(new Color(0, 0, 0));
						g.fillOval(x, y, 40, 40);
					} else {
						g.setColor(new Color(255,255,255));
						g.fillOval(x, y, 40, 40);
					}
						
				}
					
			}
			
		}
	}
	
	
	public void drawHUD(Graphics g, int winner) {
		
					//GAME INFO
		g.setColor(new Color(255, 0, 0));
		g.drawString("Current player: " + (player+1) , 10, height/2);
		if(winner != -1) {
			g.drawString("The winner is: " + winner, 10, height/2 + 20);
		}
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
	public boolean hasWon(int i, int j, String colore) {
		
		//5 on the same row
		int cont = 0;
		for(int col = 0; col < 15; col++)
			if(matrix[i][col] != null)
				if(matrix[i][col].getColor().equals(colore)) {
					cont++;
					if(cont == 5) return true;
				}
				else
					cont = 0;
			else
				cont = 0;
		
		//5 on the same column
		for(int rig = 0; rig < numCells; rig++)
			if(matrix[rig][j] != null)
				if(matrix[rig][j].getColor().equals(colore)) {
					cont++;
					if(cont == 5) return true;
				}
				else
					cont = 0;
			else
				cont = 0;
		
		//5 on first 
		int min = j > i ? i:j;
		int k = i - min;
		int l = j - min;
		while(k < numCells && l < numCells) {
			
			if(matrix[k][l] != null)
				if(matrix[k][l].getColor().equals(colore)) {
					cont++;
					if(cont == 5) return true;
				}
				else
					cont = 0;
			else
				cont = 0;
			k++;
			l++;
		}
		
		//5 on second
		k = i;
		l = j;
		//starter point
		while(k > 0 && l < numCells-1) {
			k--;
			l++;
		}
		
		while(k < numCells && l >= 0) {
			
			if(matrix[k][l] != null)
				if(matrix[k][l].getColor().equals(colore)) {
					cont++;
					if(cont == 5) return true;
				}
				else
					cont = 0;
			else
				cont = 0;
			k++;
			l--;
		}
		return false;
	}
}

