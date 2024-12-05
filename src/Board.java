import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

/*
 * This class is a panel that displays the actual game on the frame
 */
public class Board extends JPanel implements KeyListener, ActionListener{
	
	// Create a timer for the game using the time between ticks
	private Timer gameTimer = new Timer(Settings.TIME_BETWEEN_TICKS, this);
	
	// Create a new cell matrix to store every cell on the screen
	private Cell[][] mazeArray = new Cell[Settings.ROWS][Settings.COLUMNS];
	
	// Create the PacMan, which can move
	private Mover pacMan;
	
	// Creating an array to store the ghost entities
	private Mover[] ghostArray = new Mover[3];
	
	// Create a variable to keep track of how many pellets are in the board
	private int pellets = 0;
	
	// Create a score to keep track of the player's score
	private int score = 0;
	
	// This method is the constructor called when the board is initialized
	public Board() {
		
		// Setting the board's layout to place the cells in the right spots
		setLayout(new GridLayout(Settings.ROWS, Settings.COLUMNS));
		setBackground(Color.BLACK);
		
		// Calling the loadBoard method to load the board
		loadBoard("mazes/maze_1.txt");
		
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
					mazeArray[row][column] = new Cell(lineArray[column]);
					
					// Track number of pellets and count it
					if (lineArray[column] == 'F') {
						pellets++;
					}
					
					// Find PacMan in the file and create him
					else if (lineArray[column] == 'P') {
						pacMan = new Mover(row, column);
						pacMan.setIcon(Icons.PACMAN[0]);
						pacMan.setDirection(0);
					}
					
					// Initialize the map
					else if (lineArray[column] == '0' || lineArray[column] == '1' || lineArray[column] == '2') {
						
						int gNum = Character.getNumericValue(mazeArray[row][column].getItem());
						ghostArray[gNum] = new Mover(row, column);
						ghostArray[gNum].setIcon(Icons.GHOST[gNum]);
						
					}
					
					// Add the current cell to the board panel
					add(mazeArray[row][column]);
					
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
		
		// If the game is not yet running and the player is alive, start the game
		if (!gameTimer.isRunning() && !pacMan.isDead())
			gameTimer.start();
		
		// If the player is alive and there are more pellets to be collected, move PacMan
		else if (!pacMan.isDead() && score != pellets) {
			
			// Get the key the user is pressing
			int direction = key.getKeyCode() - 37;
			
			// Initialize the displacements in row and column
			int dRow = 0;
			int dCol = 0;
			
			// Check the user inputs and adjust displacement accordingly
			if (direction == 0) dCol = -1;
			else if (direction == 1) dRow = -1;
			else if (direction == 2) dCol = 1;
			else if (direction == 3) dRow = 1;
			
			// If the tile the user wants to go to is not a wall, move the player there
			if (mazeArray[pacMan.getRow() + dRow][pacMan.getColumn() + dCol]
					.getIcon() != Icons.WALL) {
				pacMan.setIcon(Icons.PACMAN[direction]);
				pacMan.setDirection(direction);
				
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
		Cell currentCell = mazeArray[mover.getRow()][mover.getColumn()];
		Cell nextCell = mazeArray[mover.getNextRow()][mover.getNextColumn()];
		
		// Move them mover to the other side if they use the teleporter
		if (mover.getColumn() == 1) {
			mover.setColumn(25);
			mazeArray[12][1].setIcon(Icons.DOOR);
		} else if (mover.getColumn() == 25) {
			mover.setColumn(1);
			mazeArray[12][25].setIcon(Icons.DOOR);
		}
		
		// Check if the next cell is not a wall
		if (nextCell.getIcon() != Icons.WALL) {
			
			// Set the current cell to a food cell if the ghost just walked past it
			if (mover != pacMan && currentCell.getItem() == 'F')
				currentCell.setIcon(Icons.FOOD);
			
			// Replace the cell with a blank cell
			else 
				currentCell.setIcon(Icons.BLANK);
			
			// Move the mover and access the new cell with the new position
			mover.move();
			currentCell = mazeArray[mover.getRow()][mover.getColumn()];
			
			// Check if the player collided
			if (collided())
				death();
			
			// If the player is not dead, set the mover image
			else
				currentCell.setIcon(mover.getIcon());
			
			// Set the image of the current cell to the mover's image
			currentCell.setIcon(mover.getIcon());
			
			// Check if PacMan just ate a food
			if (mover == pacMan && nextCell.getItem() == 'F') {
				
				// Increment the score and mark the tile as empty
				score++;
				currentCell.setItem('E');
				
				// Check if the player ate all the food
				if (score == pellets) {
					gameTimer.stop();
					JOptionPane.showMessageDialog
						(this, "You cleared the board!");
					
				}
				
			}
			
		}
		
	}
	
	// This method checks if a ghost collided with the player
	private boolean collided() {
		
		// Iterate through the 3 ghosts
		for (Mover ghost : ghostArray) {
			
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
		
		// Set the player image to dead
		mazeArray[pacMan.getRow()][pacMan.getColumn()].setIcon(Icons.SKULL);
		
		// Stop the game and show death message
		gameTimer.stop();
		JOptionPane.showMessageDialog(this, "GAME OVER");
		
	}
	
	// Method for moving each ghost
	private void moveGhosts() {
		
		// Iterate through the 3 ghosts
		for (Mover ghost : ghostArray) {
			
			// Initialize the direction
			int dir = 0;
			
			// Check if the movement isnt in opposite directions
			do {
				dir = (int)(Math.random() * 4);
			} while (Math.abs(ghost.getDirection() - dir) == 2);
			
			// Set the ghost's direction to the random direction chosen
			ghost.setDirection(dir);
			
			// If pacman is alive, move the ghost
			if (!pacMan.isDead())
				performMove(ghost);
			
		}
		
	}

	// This method calls on other methods when the game is running
	public void actionPerformed(ActionEvent event) {

		// Check if the event is the game timer
		if (event.getSource() == gameTimer) {
			
			// Move PacMan and the ghosts
			performMove(pacMan);
			moveGhosts();
			
			System.out.println(gameTimer);
		}
		
	}
}
