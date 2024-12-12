import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.*;
import java.util.Scanner;

/*
 * This class is a panel that displays the actual game on the frame
 */
public class Board extends JPanel implements KeyListener, ActionListener{
	
	private PacManGUI frame;
	
	// Create a timer for the game using the time between ticks
	private Timer gameTimer = new Timer(PacManGame.TIME_BETWEEN_TICKS, this);
	
	private Timer stopwatch = new Timer(1, this);
	
	// Create a stopwatch for the game in milliseconds
	private int elapsedTimeInMS = 0;
	
	private int tickCount = 0;
	
	// Create a new cell matrix to store every cell on the screen
	private Cell[][] mazeMatrix = new Cell[PacManGame.ROWS][PacManGame.COLUMNS];
	
	// Create the PacMan, which can move
	private Mover pacMan;
	
	// Creating an array to store the ghost entities
	private Ghost[] ghostArray = new Ghost[3];
	
	// Create a variable to keep track of how many pellets are in the board
	private int pellets = 0;
	
	// Create a variable to track the number of pellets eaten
	private int pelletsEaten = 0;
	
	// Create a score to keep track of the player's score
	private int score = 0;
	
	private int lives = PacManGame.DEFAULT_LIVES;
	
	private Timer deathTimer = new Timer(PacManGame.DEATH_DURATION, this);
	
	private Timer vulnerableTimer = new Timer(PacManGame.VULNERABLE_DURATION, this);
	
	// This method is the constructor called when the board is initialized
	public Board(PacManGUI frame) {
		this.frame = frame;
		
		// Setting the board's layout to place the cells in the right spots
		setLayout(new GridLayout(PacManGame.ROWS, PacManGame.COLUMNS));
		setBackground(Color.BLACK);
		
		// Calling the loadBoard method to load the board
		loadBoard("mazes/maze_1.txt");
		
		Sounds.playSound("sounds/pacman_intro.wav");
		
		setVisible(true);
	}
	
	// This method loads the board using the file path
	private void loadBoard(String filePath) {
		
		// Declaring the row variable to keep track of the row number while traversing the file
				int row = 0;
				
				// Declaring the scanner
				Scanner inputFile;
				
				// Trying to get the image, if it fails, send error message
				try {
					
					// Setting the scanner to read the file location given
					inputFile = new Scanner(new File(filePath));
					
					// Iterate through the rows of the file
					while (inputFile.hasNext()) {
						
						// Create a char array of the current row
						char[] lineArray = inputFile.nextLine().toCharArray();
						
						// Iterate through every character in the current row
						for (int column = 0; column < lineArray.length; column++) {
							
							// Create a new cell in the maze array given the character in the current position
							mazeMatrix[row][column] = new Cell(lineArray[column], row, column);
							
							// Track number of pellets and count it
							if (lineArray[column] == PacManGame.ID_FOOD) {
								pellets++;
							}
							
							// Find PacMan in the file and create him
							else if (lineArray[column] == PacManGame.ID_PLAYER) {
								pacMan = new Mover(this, row, column);
								pacMan.setIcon(Icons.PACMAN[0]);
								pacMan.setDirection(0);
							}
							
							// Initialize the map
							else if (lineArray[column] == '0' || lineArray[column] == '1' || lineArray[column] == '2') {
								
								int gNum = Character.getNumericValue(mazeMatrix[row][column].getId());
								ghostArray[gNum] = new Ghost(this, row, column);
								ghostArray[gNum].setIcon(Icons.GHOST[gNum]);
								
							}
							
							// Add the current cell to the board panel
							add(mazeMatrix[row][column]);
							
						}
						
						// Increment the row number, as if it were a for-loop
						row++;
						
					}
					
					// Close the input file
					inputFile.close();
					
				// If the file cannot be found, return an error message.
				} catch (FileNotFoundException error) {
					
					System.out.println("File not found");
					
				}
	}

	// This method triggers when a key is pressed
	public void keyPressed(KeyEvent key) {
		
		// Get the key the user is pressing
		int keyCode = key.getKeyCode();
		
		// If the player is alive and there are more pellets to be collected, move PacMan
		if (!pacMan.isDead() && pelletsEaten != pellets) {
			
			// Get the key the user is pressing
			int direction = keyCode - KeyEvent.VK_LEFT;

			// If direction is valid
			if (0 <= direction && direction <= 3) {
				
				if (!gameTimer.isRunning()) {
					gameTimer.start();
				}
				
				// Initialize the displacements in row and column
				int dRow = 0;
				int dCol = 0;
				
				// Check the user inputs and adjust displacement accordingly
				if (direction == 0) dCol = -1;
				else if (direction == 1) dRow = -1;
				else if (direction == 2) dCol = 1;
				else if (direction == 3) dRow = 1;
				
				// If the tile the user wants to go to is not a wall or a pusher, change the player direction
				if (mazeMatrix[pacMan.getRow() + dRow][pacMan.getColumn() + dCol].getId() != PacManGame.ID_WALL && 
						mazeMatrix[pacMan.getRow() + dRow][pacMan.getColumn() + dCol].getId() != PacManGame.ID_PUSHER) {
					
					pacMan.setIcon(Icons.PACMAN[direction]);
					pacMan.setDirection(direction);
					
				}
				
			}
			
		}
		
	}

	// This method triggers when a key is released
	public void keyReleased(KeyEvent key) {
		// Not used
		
	}

	// This method triggers when a key is typed
	public void keyTyped(KeyEvent key) {
		// Not used
		
	}
	
	// This method moves the mover
	private void performMove(Mover mover) {
		
		// Set the current and next cell to the cell in the array
		Cell currentCell = mazeMatrix[mover.getRow()][mover.getColumn()];
		Cell nextCell = mazeMatrix[mover.getNextRow()][mover.getNextColumn()];
		

		
		// Move them mover to the other side if they use the teleporter
		if (mover.getColumn() == 1) {
			
			mover.setColumn(25);
			
			mover.setDirection(0);
			
			if (mover == pacMan) 
				mover.setIcon(Icons.PACMAN[0]);
			
			mazeMatrix[12][1].setIcon(Icons.DOOR);
			
		} else if (mover.getColumn() == 25) {
			
			mover.setColumn(1);
			
			mover.setDirection(2);
			
			if (mover == pacMan) 
				mover.setIcon(Icons.PACMAN[2]);
			
			mazeMatrix[12][25].setIcon(Icons.DOOR);
			
		}
		
		nextCell = mazeMatrix[mover.getNextRow()][mover.getNextColumn()];
		
		// Check if the next cell is not a wall
		if (nextCell.getId() != PacManGame.ID_WALL) {
			
			// Check if the mover is a ghost
			if (mover != pacMan) {
				
				if (nextCell.getId() == PacManGame.ID_PUSHER && mover.getDirection() == 3 && !mover.isDead()) 
					return;
				
				if (currentCell.getId() == PacManGame.ID_PUSHER && !mover.isDead()) {
					mover.setDirection(1);
					nextCell = mazeMatrix[mover.getNextRow()][mover.getNextColumn()];
				}
				
				if (isOverlapping(mover, nextCell) && !mover.isDead())
					return;
				
				// Check if the ghost just walked over the food, then replace the food
				if (currentCell.getId() == PacManGame.ID_FOOD)
					currentCell.setIcon(Icons.FOOD);
				
				// Replace the cell with a blank cell
				else if (currentCell.getId() == PacManGame.ID_BIG_FOOD)
					currentCell.setIcon(Icons.BIG_FOOD);
				
				else
					currentCell.setIcon(Icons.BLANK);
				
			}
			
			// Check if mover is pacman
			else if (mover == pacMan) {
				
				// Check if pacman will eat food
				if (nextCell.getId() == PacManGame.ID_FOOD) {

					nextCell.setId(PacManGame.ID_EMPTY);
					
					// Increment the score and mark the cell as empty
					pelletsEaten++;
					score += PacManGame.SCORE_PELLET;
					
					frame.updateScore(score);
					
					Sounds.playSound("sounds/eat_dot_0.wav");
					
				} else if (nextCell.getId() == PacManGame.ID_BIG_FOOD) {
					
					nextCell.setId(PacManGame.ID_EMPTY);
					
					score += PacManGame.SCORE_BIG;
					
					frame.updateScore(score);
					
					Sounds.playSound("sounds/eat_dot_0.wav");
					
					for (Ghost ghost : ghostArray) {
						
						ghost.setVulnerable(true);
						
						ghost.setIcon(Icons.GHOST[3]);
						
					}
					
					if (vulnerableTimer.isRunning()) {

						vulnerableTimer.restart();
						for (Ghost ghost : ghostArray)
							ghost.setVulnerableTime(0);
						
					}
					else 
						vulnerableTimer.start();
					
				}
				
				currentCell.setIcon(Icons.BLANK);
				
			}
			
			// Move the mover and access the new cell with the new position
			mover.move();
			currentCell = mazeMatrix[mover.getRow()][mover.getColumn()];
			
			// Set the image of the current cell to the mover's image
			currentCell.setIcon(mover.getIcon());
			
			// Check if the player collided
			Ghost collidedGhost = collided();
			
			if (collidedGhost != null) {
				
				if (!collidedGhost.isVulnerable()) {

					death();
					return;
					
				} else {
					
					score += PacManGame.SCORE_GHOST;
					ghostDeath(collidedGhost);
					
				}
				
			}
			
			// Check if the player ate all the food
			if (pelletsEaten == pellets) {
				
				endGame();
				
			}
			
		}
		
	}
	
	// This method triggers when the player dies
	private void death() {
		
		gameTimer.stop();
		
		// Set the pacman to dead
		pacMan.setDead(true);
		
		for (Ghost ghost : ghostArray) {
			
			ghost.setVulnerable(false);
			ghost.setDead(false);
			
		}
		
		Sounds.playSound("sounds/death_0.wav");

		// Set the player image to dead
		mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIcon(Icons.SKULL);
		
		lives--;
		frame.updateLives(lives);
		
		deathTimer.start();
		
	}
	
	private void ghostDeath(Ghost ghost) {

		ghost.setDead(true);
		
		mazeMatrix[ghost.getRow()][ghost.getColumn()].setIcon(pacMan.getIcon());
		
		int ghostNum = -1;
		
		for (int i = 0; i < ghostArray.length; i++) {
			
			if (ghostArray[i] == ghost)
				ghostNum = i;
			
		}
		
		ghost.setIcon(Icons.GHOST[ghostNum]);
		
	}
	
	private void moveGhosts() {
		
		for (Ghost ghost : ghostArray) {
			
			if (!ghost.isDead() && !ghost.isVulnerable() && tickCount % 5 == 0) {
				
				ghost.movePath(mazeMatrix[pacMan.getRow()][pacMan.getColumn()]);
				
				// If pacman is alive, move the ghost
				if (!pacMan.isDead())
					performMove(ghost);
				
			} else if (!ghost.isDead() && ghost.isVulnerable()){
				
				ghost.setVulnerableTime(ghost.getVulnerableTime() + PacManGame.TIME_BETWEEN_TICKS);
				
				if (ghost.getVulnerableTime() >= PacManGame.TIME_BEFORE_WARNING && tickCount % 5 == 0) {
					
					int ghostNum = -1;
					
					for (int i = 0; i < ghostArray.length; i++) {
						
						if (ghostArray[i] == ghost)
							ghostNum = i;
						
					}
					
					ghost.setIcon(ghost.getIcon() == Icons.GHOST[3] ? Icons.GHOST[ghostNum] : Icons.GHOST[3]);
					mazeMatrix[ghost.getRow()][ghost.getColumn()].setIcon(ghost.getIcon());
				}
				
				if (tickCount % 15 == 0) {

					ghost.moveRandomly();

					// If pacman is alive, move the ghost
					if (!pacMan.isDead())
						performMove(ghost);
					
				}
				
			} else if (ghost.isDead()){
				
				ghost.setVulnerableTime(0);
				
				ghost.movePath(mazeMatrix[ghost.getDefaultRow()][ghost.getDefaultColumn()]);

				// If pacman is alive, move the ghost
				if (!pacMan.isDead())
					performMove(ghost);
				
				if (ghost.getRow() == ghost.getDefaultRow() && ghost.getColumn() == ghost.getDefaultColumn()) {
					
					ghost.setVulnerable(false);
					ghost.setDead(false);

					ghost.setIcon(Icons.GHOST[Integer.parseInt("" + mazeMatrix[ghost.getRow()][ghost.getColumn()].getId())]);
					mazeMatrix[ghost.getRow()][ghost.getColumn()].setIcon(ghost.getIcon());
				
				}
				
			}
			
		}
	}

	// This method calls on other methods when the game is running
	public void actionPerformed(ActionEvent event) {
		
		// Check if a tick has passed
		if (event.getSource() == stopwatch)
			elapsedTimeInMS++;
		
		else if (event.getSource() == gameTimer) {
			
			tickCount++;
			System.out.println(tickCount);
			
			if (tickCount % 5 == 0)
				performMove(pacMan);
			
			moveGhosts();
			
		} else if (event.getSource() == deathTimer) {

			if (lives > 0) {

				for (int i = 0; i < ghostArray.length; i++) {
					
					System.out.println(mazeMatrix[ghostArray[i].getRow()][ghostArray[i].getColumn()]);
					
					mazeMatrix[ghostArray[i].getRow()][ghostArray[i].getColumn()].setIdIcon(mazeMatrix[ghostArray[i].getRow()][ghostArray[i].getColumn()].getId());
					
					System.out.println(mazeMatrix[ghostArray[i].getRow()][ghostArray[i].getColumn()].getIcon());
					
					ghostArray[i].setRow(ghostArray[i].getDefaultRow());
					ghostArray[i].setColumn(ghostArray[i].getDefaultColumn());
					ghostArray[i].setIcon(Icons.GHOST[i]); 
					
					mazeMatrix[ghostArray[i].getRow()][ghostArray[i].getColumn()].setIcon(ghostArray[i].getIcon());
					
				}
				
				mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIdIcon(mazeMatrix[pacMan.getRow()][pacMan.getColumn()].getId());
				
				pacMan.setRow(pacMan.getDefaultRow());
				pacMan.setColumn(pacMan.getDefaultColumn());
				pacMan.setDirection(0);
				pacMan.setIcon(Icons.PACMAN[0]);
				
				mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIcon(pacMan.getIcon());
				
				pacMan.setDead(false);
				
			} else {
				
				endGame();
				
			}
			
			deathTimer.stop();
			
		} else if (event.getSource() == vulnerableTimer) {
			
			for (int i = 0; i < ghostArray.length; i++) {

				ghostArray[i].setVulnerableTime(0);
				ghostArray[i].setVulnerable(false);
				ghostArray[i].setIcon(Icons.GHOST[i]);
				
			}
		}
		
	}
	
	private void endGame() {
		
		gameTimer.stop();
		
		frame.dispose();
		
		createEndScreen();
		
//		frame.dispose();
//		
//		createEndScreen();
//
//		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//	              new FileOutputStream("leaderboards/medium_difficulty.txt"), "utf-8"))) {
//			
//			writer.write(score);
//			
//		} catch (IOException e) {
//			
//			System.err.println(e);
//			e.printStackTrace();
//			
//		}
		
	}
	
	private void createEndScreen() {
		
		new EndPage(score);
		
	}

	public boolean isOverlapping(Mover self, Cell nextCell) {
		
		for (Mover ghost : ghostArray) {
			
			if (ghost == self) continue;
			
			if (nextCell == mazeMatrix[ghost.getRow()][ghost.getColumn()])
				return true;
			
		}
		
		return false;
		
	}
	
	// This method checks if a ghost collided with the player
	public Ghost collided() {
		
		// Iterate through the 3 ghosts
		for (Ghost ghost : ghostArray) {
			
			// Check if the player is on the same cell as the ghost
			if (ghost.getRow() == pacMan.getRow() && ghost.getColumn() == pacMan.getColumn())		
				return ghost;
			
		}
		
		// If the player is not collided, return false
		return null;
		
	}
	
	public Cell[][] getMazeMatrix(){
		return mazeMatrix;
	}
	
	public char getIdOfMover(int row, int column) {
		
		for (Ghost ghost : ghostArray) {
			
			if (ghost.getRow() == row && ghost.getColumn() == column) return PacManGame.ID_GHOST; 
			
		}
		
		if (pacMan.getRow() == row && pacMan.getColumn() == column) return PacManGame.ID_PLAYER;
		
		return '_';
		
	}

}
