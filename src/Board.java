import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import java.util.Scanner;

/*
 * This class is a panel that displays the actual game on the frame
 * The class allows the actual game to run and calls the end page when it is over
 */
public class Board extends JPanel implements KeyListener, ActionListener{
	
	private PacManGUI frame; // Reference to the main frame
	private Timer gameTimer = new Timer(PacManGame.TIME_BETWEEN_TICKS, this); // Create a timer for the game using the time between ticks
	private int tickCount = 0; // Counter for number of ticks	
	private Cell[][] mazeMatrix = new Cell[PacManGame.ROWS][PacManGame.COLUMNS]; // Create a new cell matrix to store every cell on the screen
	private Mover pacMan; // Create the PacMan, which is a mover
	private Ghost[] ghostArray = new Ghost[3]; // Creating an array to store the ghost entities
	private int pellets = 0; // Create a variable to keep track of how many pellets are in the board
	private int pelletsEaten = 0; // Create a variable to track the number of pellets eaten
	private Timer cherryTimer = new Timer(PacManGame.CHERRY_SPAWN_TIME, this); // Create a timer for spawning cherries
	private int cherriesSpawned = 0; // Variable for the number of cherries that has already been spawned
	private boolean cherryOnBoard = false; // Variable for whether there is already a cherry
	private int score = 0; // Create a score to keep track of the player's score
	private int lives = PacManGame.DEFAULT_LIVES; // Setting the number of lives PacMan has at the start	
	private Timer deathTimer = new Timer(PacManGame.DEATH_DURATION, this); // Setting a timer for loading the map after the player dies
	private Timer vulnerableTimer = new Timer(PacManGame.VULNERABLE_DURATION, this); // Setting a timer for how long the ghosts are vulnerable for
	
	// This method is the constructor called when the board is initialized
	public Board(PacManGUI frame) {
		
		// Setting the parent frame
		this.frame = frame;
		
		// Setting the board's layout to place the cells in the right spots
		setLayout(new GridLayout(PacManGame.ROWS, PacManGame.COLUMNS));
		setBackground(Color.BLACK);
		
		// Calling the loadBoard method to load the board
		loadBoard("mazes/maze_1.txt");
		
		// Playing the intro music
		Utilities.playSound("sounds/pacman_intro.wav");
		
		// Showing the frame
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
							
							// Initialize the ghosts
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

			// Check if direction is valid
			if (0 <= direction && direction <= 3) {
				
				// Start the game if it has not started yet, reset the tick count as well
				if (!gameTimer.isRunning()) {
					tickCount = 0;
					gameTimer.start();
					
					// Start spawning cherries if there are none
					if (cherriesSpawned < 3 && !cherryOnBoard)
						cherryTimer.start();
				
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
		
		// Move the mover to the other side if they use the teleporter
		if (mover.getColumn() == 1) {
			
			mover.setColumn(25);
			
			mover.setDirection(0);
			
			if (mover == pacMan) 
				mover.setIcon(Icons.PACMAN[0]);
			
			// Replace the door
			mazeMatrix[12][1].setIcon(Icons.DOOR);
			
		} else if (mover.getColumn() == 25) {
			
			mover.setColumn(1);
			
			mover.setDirection(2);
			
			if (mover == pacMan) 
				mover.setIcon(Icons.PACMAN[2]);
			
			// Replace the door
			mazeMatrix[12][25].setIcon(Icons.DOOR);
			
		}
		
		// Set the next cell again
		nextCell = mazeMatrix[mover.getNextRow()][mover.getNextColumn()];
		
		// Check if the next cell is not a wall
		if (nextCell.getId() != PacManGame.ID_WALL) {
			
			// Check if the mover is a ghost
			if (mover != pacMan) {
				
				// If the next cell is a pusher and the ghost is moving down, stop it from moving
				if (nextCell.getId() == PacManGame.ID_PUSHER && mover.getDirection() == 3 && !mover.isDead()) 
					return;
				
				// If the current cell is a pusher and pacman is alive, set the direction to up
				if (currentCell.getId() == PacManGame.ID_PUSHER && !mover.isDead()) {
					mover.setDirection(1);
					nextCell = mazeMatrix[mover.getNextRow()][mover.getNextColumn()]; // setting it again incase it changed
				}
				
				// If the ghosts will be overlapping, stop them from moving
				if (isOverlapping(mover, nextCell) && !mover.isDead())
					return;
				
				// Check if the ghost just walked over the food, then replace the food
				if (currentCell.getId() == PacManGame.ID_FOOD)
					currentCell.setIcon(Icons.FOOD);
				
				// Check if the ghost just walked over a big food, then replace it
				else if (currentCell.getId() == PacManGame.ID_BIG_FOOD)
					currentCell.setIcon(Icons.BIG_FOOD);
				
				// Check if the ghost just walked over a cherry, then replace it
				else if (currentCell.getId() == PacManGame.ID_CHERRY)
					currentCell.setIcon(Icons.CHERRY);
				
				// Otherwise replace it with a blank image
				else
					currentCell.setIcon(Icons.BLANK);
				
			}
			
			// Check if mover is pacman
			else if (mover == pacMan) {
				
				// Check if pacman will eat food
				if (nextCell.getId() == PacManGame.ID_FOOD) {
					
					// Set the id to empty
					nextCell.setId(PacManGame.ID_EMPTY);
					
					// Increment the score and pellets eaten
					pelletsEaten++;
					score += PacManGame.SCORE_PELLET;
					
					// Update the score label using the new score
					frame.updateScore(score);
					
					// Play a chomping sound
					Utilities.playSound("sounds/eat_dot_0.wav");
					
				// Check if pacman will eat big food
				} else if (nextCell.getId() == PacManGame.ID_BIG_FOOD) {
					
					// Set the id to empty
					nextCell.setId(PacManGame.ID_EMPTY);
					
					// Add score
					score += PacManGame.SCORE_BIG;
					
					// Update the score label accordingly
					frame.updateScore(score);
					
					// Play a fruit eating sound
					Utilities.playSound("sounds/fruit_eaten.wav");
					
					// Set all the ghosts to be vulnerable and blue
					for (Ghost ghost : ghostArray) {
						
						ghost.setVulnerable(true);

						ghost.setIcon(Icons.GHOST[3]);
						
					}
					
					// If the ghosts are already vulnerable, add more time before the vulnerable expires
					if (vulnerableTimer.isRunning()) {

						vulnerableTimer.restart();
						
						for (Ghost ghost : ghostArray)
							ghost.setVulnerableTime(0);
						
					}
					
					// If the ghosts are not vulnerable yet, make them vulnerable
					else 
						vulnerableTimer.start();
					
				// Check if pacman will eat a cherry
				} else if (nextCell.getId() == PacManGame.ID_CHERRY) {
					
					// Set the id to empty
					nextCell.setId(PacManGame.ID_EMPTY);
					
					// Increment the score
					score += PacManGame.SCORE_CHERRY;
			
					// Update the score label
					frame.updateScore(score);
					
					// Play a fruit eating sound
					Utilities.playSound("sounds/fruit_eaten.wav");
					
					// Set the tracker to false because there is no more cherry
					cherryOnBoard = false;
					
					// If less than 3 cherries have spawned, start the spawning timer
					if (cherriesSpawned < 3)
						cherryTimer.start();
					
				}
				
				// Remove the cherry image
				currentCell.setIcon(Icons.BLANK);
				
			}
			
			// Move the mover and access the new cell with the new position
			mover.move();
			currentCell = mazeMatrix[mover.getRow()][mover.getColumn()];
			
			// Set the image of the current cell to the mover's image
			currentCell.setIcon(mover.getIcon());
			
			// Check if the player collided
			Ghost collidedGhost = collided();
			
			// Check if a ghost has collided
			if (collidedGhost != null) {
				
				// If the collided ghost is not vulnerable, kill the player
				if (!collidedGhost.isVulnerable()) {

					death();
					return;
					
				// If the ghost is vulnerable, kill the ghost
				} else {

					// Play a fruit eating sound
					Utilities.playSound("sounds/fruit_eaten.wav");
					
					// Increment the score
					score += PacManGame.SCORE_GHOST;
					
					// Kill the ghost
					ghostDeath(collidedGhost);
					
				}
				
			}
			
			// Check if the player ate all the food, end the game
			if (pelletsEaten == pellets)
				endGame();
			
		}
		
	}
	
	// This method triggers when the player dies
	private void death() {
		
		// Stop the game and cherry spawning
		gameTimer.stop();
		cherryTimer.stop();
		
		// Set the pacman to dead
		pacMan.setDead(true);
		
		// Play death sound
		Utilities.playSound("sounds/death_0.wav");

		// Set the player image to dead
		mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIcon(Icons.SKULL);
		
		// Minus lives and update the label
		lives--;
		frame.updateLives(lives);
		
		// Start the death timer, which gives the user to react to their death
		deathTimer.start();
		
	}
	
	// This method processes the ghosts' death
	private void ghostDeath(Ghost ghost) {

		// Set the ghost to dead
		ghost.setDead(true);
		
		// Replace the ghosts' icon with pacman's icon
		mazeMatrix[ghost.getRow()][ghost.getColumn()].setIcon(pacMan.getIcon());
		
		// Declare the ghost number
		int ghostNum = -1;
		
		// Find the ghost it is
		for (int i = 0; i < ghostArray.length; i++) {
			
			if (ghostArray[i] == ghost)
				ghostNum = i;
			
		}

		// Reset the vulnerable time
		ghost.setVulnerableTime(0);
		
		// Set the ghosts' image
		ghost.setIcon(Icons.GHOST[ghostNum]);
		
	}
	
	// This method moves all the ghosts
	private void moveGhosts() {
		
		// Do these steps for every ghost
		for (Ghost ghost : ghostArray) {
			
			// If the ghost is alive and not vulnerable, it will move at a normal speed
			if (!ghost.isDead() && !ghost.isVulnerable() && tickCount % 5 == 0) {

				// Set the ghost's direction
				if (tickCount <= 200) // 10 second grace period when the ghosts just wonder around
					ghost.moveRandomly();
				else // Ghost pathfinds to player
					ghost.movePath(mazeMatrix[pacMan.getRow()][pacMan.getColumn()]);
				
				// If pacman is alive, move the ghost
				if (!pacMan.isDead())
					performMove(ghost);
				
			// If the ghost is alive and vulnerable
			} else if (!ghost.isDead() && ghost.isVulnerable()){
				
				// Adjust vulnerable time
				ghost.setVulnerableTime(ghost.getVulnerableTime() + PacManGame.TIME_BETWEEN_TICKS);
				
				// Check if the vulnerable time has exceeded the time before warning and its been 5 ticks
				// This creates a flashing effect where the ghost is switching between blue and normal image
				if (ghost.getVulnerableTime() >= PacManGame.TIME_BEFORE_WARNING && tickCount % 5 == 0) {
					
					// Get the ghost number
					int ghostNum = -1;
					
					for (int i = 0; i < ghostArray.length; i++) {
						
						if (ghostArray[i] == ghost)
							ghostNum = i;
						
					}
					
					// Set the ghost to blue if it is not blue and set the ghost to its normal color if it is blue
					ghost.setIcon(ghost.getIcon() == Icons.GHOST[3] ? Icons.GHOST[ghostNum] : Icons.GHOST[3]);
					mazeMatrix[ghost.getRow()][ghost.getColumn()].setIcon(ghost.getIcon());
					
				}
				
				// Ghost moves 3 times slower than player
				if (tickCount % 15 == 0) {

					// Set ghost direction to move randomly
					ghost.moveRandomly();

					// If pacman is alive, move the ghost
					if (!pacMan.isDead())
						performMove(ghost);
					
				}
				
			// Check if the ghost is dead
			} else if (ghost.isDead()){
				
				// Set the ghost's direction towards its spawn
				ghost.movePath(mazeMatrix[ghost.getDefaultRow()][ghost.getDefaultColumn()]);

				// If pacman is alive, move the ghost
				if (!pacMan.isDead())
					performMove(ghost);
				
				// Check if the ghost has reached its spawn
				if (ghost.getRow() == ghost.getDefaultRow() && ghost.getColumn() == ghost.getDefaultColumn()) {
					
					// Make the ghost alive and not vulnerable
					ghost.setVulnerable(false);
					ghost.setDead(false);

					// Make the ghost image normal and set the image
					ghost.setIcon(Icons.GHOST[Integer.parseInt("" + mazeMatrix[ghost.getRow()][ghost.getColumn()].getId())]);
					mazeMatrix[ghost.getRow()][ghost.getColumn()].setIcon(ghost.getIcon());
				
				}
				
			}
			
		}
		
	}
	
	// This method spawns a cherry
	private void spawnCherry() {

		// Declare the random row and column
		int randRow = -1;
		int randColumn = -1;

		do {
			
			// Get a random row in the grid
			randRow = (int) (Math.random() * PacManGame.ROWS);
			randColumn = (int) (Math.random() * PacManGame.COLUMNS);
			
		// Check if the row is in the ghosts' house or is not empty or is on the player
		// If it is, generate a new set of row and column numbers
		} while (mazeMatrix[randRow][randColumn].getId() != PacManGame.ID_EMPTY || 
				getIdOfMover(randRow, randColumn) == PacManGame.ID_PLAYER ||
				(randRow > 10 && randRow <= 14 && randColumn > 10 && randColumn <= 17));

		// Set the id and icon of the cherry
		mazeMatrix[randRow][randColumn].setId(PacManGame.ID_CHERRY);
		mazeMatrix[randRow][randColumn].setIcon(Icons.CHERRY);
		
		// Increase the counter of cherries spawned
		cherriesSpawned++;
		
	}

	// This method detects if a timer is finished
	public void actionPerformed(ActionEvent event) {
		
		// Check if a tick has passed
		if (event.getSource() == gameTimer) {
			
			// Increase the tick count
			tickCount++;
			
			// Every 5 ticks, move player
			if (tickCount % 5 == 0)
				performMove(pacMan);
			
			// Call move ghosts function
			moveGhosts();
			
		// Check if the death timer is up
		} else if (event.getSource() == deathTimer) {

			// If the player has more lives
			if (lives > 0) {

				// Reset the ghosts
				for (int i = 0; i < ghostArray.length; i++) {
					
					// Set the image of the ghost based on what is under it
					mazeMatrix[ghostArray[i].getRow()][ghostArray[i].getColumn()].setIdIcon(mazeMatrix[ghostArray[i].getRow()][ghostArray[i].getColumn()].getId());
				
					// Reset the ghost
					ghostArray[i].setVulnerable(false);
					ghostArray[i].setDead(false);
					ghostArray[i].setRow(ghostArray[i].getDefaultRow());
					ghostArray[i].setColumn(ghostArray[i].getDefaultColumn());
					ghostArray[i].setIcon(Icons.GHOST[i]); 
					
					// Set the actual image of the ghost
					mazeMatrix[ghostArray[i].getRow()][ghostArray[i].getColumn()].setIcon(ghostArray[i].getIcon());
					
				}
				
				// Set the image of the icon under the pacman
				mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIdIcon(mazeMatrix[pacMan.getRow()][pacMan.getColumn()].getId());
				
				// Reset the pacman
				pacMan.setRow(pacMan.getDefaultRow());
				pacMan.setColumn(pacMan.getDefaultColumn());
				pacMan.setDirection(0);
				pacMan.setIcon(Icons.PACMAN[0]);
				pacMan.setDead(false);
				
				// Set the image of the pacman
				mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIcon(pacMan.getIcon());
				
			// If the player has no more lives, show end screen
			} else				
				endGame();
			
			// Stop the death timer
			deathTimer.stop();
			
		// If the vulnerable timer is over
		} else if (event.getSource() == vulnerableTimer) {
			
			// Make all the ghosts not vulnerable
			for (int i = 0; i < ghostArray.length; i++) {

				ghostArray[i].setVulnerableTime(0);
				ghostArray[i].setVulnerable(false);
				ghostArray[i].setIcon(Icons.GHOST[i]);
				
			}
			
		// If the cherry spawn timer is over
		} else if (event.getSource() == cherryTimer) {
			
			// Spawn a cherry and set the variables
			spawnCherry();
			cherryOnBoard = true;
			
			// Stop this timer
			cherryTimer.stop();
			
		}
		
	}
	
	// This method stops the game timer and closes this frame and opens the end frame
	private void endGame() {
		
		gameTimer.stop();
		frame.dispose();
		
		new EndPage(score);
		
	}

	// This method checks if the next cell of a mover has another ghost in it
	public boolean isOverlapping(Mover self, Cell nextCell) {
		
		// Check every ghost
		for (Mover ghost : ghostArray) {
			
			// If the ghost is itself, go to the next ghost
			if (ghost == self) continue;
			
			// If the next cell has a ghost in it, return true
			if (nextCell == mazeMatrix[ghost.getRow()][ghost.getColumn()])
				return true;
			
		}
		
		// If nothing returned, return false
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
	
	// This method gets the id of the mover on the given row and column
	public char getIdOfMover(int row, int column) {
		
		// Go through every ghost
		for (Ghost ghost : ghostArray) {
			
			// Check if the ghost is on the given row and column
			if (ghost.getRow() == row && ghost.getColumn() == column) 
				return PacManGame.ID_GHOST; 
			
		}
		
		// Check if the pacman is on the given row and column
		if (pacMan.getRow() == row && pacMan.getColumn() == column) 
			return PacManGame.ID_PLAYER;
		
		// If nothing else returned, return nothing
		return '_';
		
	}

	// Getter for the maze matrix
	public Cell[][] getMazeMatrix(){
		return mazeMatrix;
	}
	
}
