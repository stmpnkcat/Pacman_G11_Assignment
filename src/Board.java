import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.Scanner;

/*
 * This class is a panel that displays the actual game on the frame
 */
public class Board extends JPanel implements KeyListener, ActionListener{
	
	private PacManGUI frame;
	
	// Create a timer for the game using the time between ticks
	private Timer gameTimer = new Timer(PacManGame.TIME_BETWEEN_TICKS, this);
	
	// Create a stopwatch for the game in milliseconds
	private double elapsedTimeInSEC = 0;
	
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
			mover.setIcon(Icons.PACMAN[0]);
			mazeMatrix[12][1].setIcon(Icons.DOOR);
			
		} else if (mover.getColumn() == 25) {
			
			mover.setColumn(1);
			mover.setDirection(2);
			mover.setIcon(Icons.PACMAN[2]);
			mazeMatrix[12][25].setIcon(Icons.DOOR);
			
		}
		
		// Check if the next cell is not a wall
		if (nextCell.getId() != PacManGame.ID_WALL) {
			
			// Check if the mover is a ghost
			if (mover != pacMan) {
				
				if (currentCell.getId() == PacManGame.ID_PUSHER) {
					mover.setDirection(1);
					nextCell = mazeMatrix[mover.getNextRow()][mover.getNextColumn()];
				}
				
				if (nextCell.getId() == PacManGame.ID_PUSHER && mover.getDirection() == 3) 
					return;
				
				if (isOverlapping(mover, nextCell))
					return;
				
				// Check if the ghost just walked over the food, then replace the food
				if (currentCell.getId() == PacManGame.ID_FOOD)
					currentCell.setIcon(Icons.FOOD);
				
				// Replace the cell with a blank cell
				else 
					currentCell.setIcon(Icons.BLANK);
				
			}
			
			// Check if mover is pacman
			else if (mover == pacMan) {
				
				// Check if pacman will eat food
				if (currentCell.getId() == PacManGame.ID_FOOD) {
					
					// Increment the score and mark the cell as empty
					pelletsEaten++;
					score += PacManGame.PELLET_SCORE;
					currentCell.setId(PacManGame.ID_EMPTY);
					
					frame.updateScore(score);
					
					Sounds.playSound("sounds/eat_dot_0.wav");
					
					// Check if the player ate all the food
					if (score == pellets) {
						gameTimer.stop();
						JOptionPane.showMessageDialog
							(this, "You cleared the board!");
					}
					
				}
				
				currentCell.setIcon(Icons.BLANK);
				
			}
			
			mover.setPrevCell(currentCell);
			
			// Move the mover and access the new cell with the new position
			mover.move();
			currentCell = mazeMatrix[mover.getRow()][mover.getColumn()];
			
			// Set the image of the current cell to the mover's image
			currentCell.setIcon(mover.getIcon());
			
			// Check if the player collided
			if (collided())
				death();
			
		}
		
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
	private boolean collided() {
		
		// Iterate through the 3 ghosts
		for (Ghost ghost : ghostArray) {
			
			// Check if the player is on the same cell as the ghost
			if (ghost.getRow() == pacMan.getRow() && ghost.getColumn() == pacMan.getColumn())		
				return true;
			
		}
		
		// If the player is not collided, return false
		return false;
		
	}
	
	// This method triggers when the player dies
	private void death() {
		
		// Set the pacman to dead
		pacMan.setDead(true);
		
		Sounds.playSound("sounds/death_0.wav");
		
		// Stop the game
		gameTimer.stop();

		// Set the player image to dead
		mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIcon(Icons.SKULL);
		
		lives--;
		frame.updateLives(lives);
		
		deathTimer.start();
		
	}
	
	public void moveGhosts() {
		
		for (Ghost ghost : ghostArray) {
			
			// Move PacMan and the ghosts
			
//			ghost.moveRandomly();
			ghost.movePath(mazeMatrix[pacMan.getRow()][pacMan.getColumn()]);
			
			/*
			if (elapsedTimeInSEC <= 1)
				ghost.movePath(mazeMatrix[pacMan.getRow()][pacMan.getColumn()]);
//			else if (elapsedTimeInSEC % 10 >= 0 && elapsedTimeInSEC % 10 <= ghost.getRandomInterval())
//				ghost.moveRandomly();
			else
				ghost.movePath(mazeMatrix[pacMan.getRow()][pacMan.getColumn()]);
			*/
			
			// If pacman is alive, move the ghost
			if (!pacMan.isDead())
				performMove(ghost);
					
		}
		
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

	// This method calls on other methods when the game is running
	public void actionPerformed(ActionEvent event) {
		
		// Check if a tick has passed
		if (event.getSource() == gameTimer) {
			
			System.out.println(elapsedTimeInSEC);

			// Increment the elapsed time by the time between ticks
			elapsedTimeInSEC += (double)PacManGame.TIME_BETWEEN_TICKS / 1000;

			performMove(pacMan);
			moveGhosts();
			
		} else if (event.getSource() == deathTimer) {

			if (lives > 0) {

				mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIcon(pacMan.getPrevCell().getIcon());
				System.out.println("PacMan: " + mazeMatrix[pacMan.getRow()][pacMan.getColumn()].getIcon().toString());
				pacMan.setRow(15);
				pacMan.setColumn(13);
				
				mazeMatrix[ghostArray[0].getRow()][ghostArray[0].getColumn()].setIcon(ghostArray[0].getPrevCell().getIcon());
				System.out.println("1: " + mazeMatrix[ghostArray[0].getRow()][ghostArray[0].getColumn()].getIcon().toString());
				ghostArray[0].setRow(11);
				ghostArray[0].setColumn(11);
				
				mazeMatrix[ghostArray[1].getRow()][ghostArray[1].getColumn()].setIcon(ghostArray[1].getPrevCell().getIcon());
				System.out.println("2: " + mazeMatrix[ghostArray[1].getRow()][ghostArray[1].getColumn()].getIcon().toString());
				ghostArray[1].setRow(11);
				ghostArray[1].setColumn(15);
				
				mazeMatrix[ghostArray[2].getRow()][ghostArray[2].getColumn()].setIcon(ghostArray[2].getPrevCell().getIcon());
				System.out.println("3: " + mazeMatrix[ghostArray[2].getRow()][ghostArray[2].getColumn()].getIcon().toString());
				ghostArray[2].setRow(13);
				ghostArray[2].setColumn(13);

				pacMan.setDead(false);
				
			} else {
				
				frame.dispose();
				new TitlePage();
				
			}
			
			deathTimer.stop();
			
		}
		
	}

}
