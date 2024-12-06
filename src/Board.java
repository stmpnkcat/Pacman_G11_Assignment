import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * This class is a panel that displays the actual game on the frame
 */
public class Board extends JPanel implements KeyListener, ActionListener{
	
	// Create a timer for the game using the time between ticks
	private Timer gameTimer = new Timer(Settings.TIME_BETWEEN_TICKS, this);
	
	// Create a stopwatch for the game in milliseconds
	private int elapsedTimeInMS = 0;
	
	// Create a new cell matrix to store every cell on the screen
	private Cell[][] mazeMatrix = new Cell[Settings.ROWS][Settings.COLUMNS];
	
	// Create the PacMan, which can move
	private PacMan pacMan;
	
	// Creating an array to store the ghost entities
	private Ghost[] ghostArray = new Ghost[3];
	
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
					mazeMatrix[row][column] = new Cell(lineArray[column], row, column);
					
					// Track number of pellets and count it
					if (lineArray[column] == Settings.ID_FOOD) {
						pellets++;
					}
					
					// Find PacMan in the file and create him
					else if (lineArray[column] == Settings.ID_PLAYER) {
						pacMan = new PacMan(row, column);
						pacMan.setIcon(Icons.PACMAN[0]);
						pacMan.setDirection(0);
					}
					
					// Initialize the map
					else if (lineArray[column] == '0' || lineArray[column] == '1' || lineArray[column] == '2') {
						
						int gNum = Character.getNumericValue(mazeMatrix[row][column].getId());
						ghostArray[gNum] = new Ghost(row, column);
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
		
		// If the game is not yet running and the player is alive, start the game
		if (!gameTimer.isRunning() && !pacMan.isDead())
			gameTimer.start();
		
		// Get the key the user is pressing
		int keyCode = key.getKeyCode();
		
		// If the player is alive and there are more pellets to be collected, move PacMan
		if (!pacMan.isDead() && score != pellets) {
			
			// Get the key the user is pressing
			int direction = keyCode - KeyEvent.VK_LEFT;

			// If direction is valid
			if (0 <= direction && direction <= 3) {
				// Initialize the displacements in row and column
				int dRow = 0;
				int dCol = 0;
				
				// Check the user inputs and adjust displacement accordingly
				if (direction == 0) dCol = -1;
				else if (direction == 1) dRow = -1;
				else if (direction == 2) dCol = 1;
				else if (direction == 3) dRow = 1;
				
				// If the tile the user wants to go to is not a wall, change the player direction
				if (mazeMatrix[pacMan.getRow() + dRow][pacMan.getColumn() + dCol]
						.getId() != Settings.ID_WALL) {
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
			mazeMatrix[12][1].setIcon(Icons.DOOR);
		} else if (mover.getColumn() == 25) {
			mover.setColumn(1);
			mazeMatrix[12][25].setIcon(Icons.DOOR);
		}
		
		// Check if the next cell is not a wall
		if (nextCell.getId() != Settings.ID_WALL) {
			
			// Check if the mover is a ghost
			if (mover != pacMan) {
				
				// Check if the ghost just walked over the food, then replace the food
				if (currentCell.getId() == Settings.ID_FOOD) {
					currentCell.setIcon(Icons.FOOD);
				}
				
				// Replace the cell with a blank cell
				else 
					currentCell.setIcon(Icons.BLANK);
				
				
			}
			
			// Check if mover is pacman
			else if (mover == pacMan) {
				
				// Check if pacman will eat food
				if (currentCell.getId() == Settings.ID_FOOD) {
					
					// Increment the score and mark the cell as empty
					score++;
					currentCell.setId(Settings.ID_EMPTY);
					
					// Check if the player ate all the food
					if (score == pellets) {
						gameTimer.stop();
						JOptionPane.showMessageDialog
							(this, "You cleared the board!");
					}
					
				}
				
				currentCell.setIcon(Icons.BLANK);
				
			}
			
			// Move the mover and access the new cell with the new position
			mover.move();
			currentCell = mazeMatrix[mover.getRow()][mover.getColumn()];
			
			// Check if the player collided
			if (collided())
				death();
			
			// Set the image of the current cell to the mover's image
			currentCell.setIcon(mover.getIcon());
			
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
		mazeMatrix[pacMan.getRow()][pacMan.getColumn()].setIcon(Icons.SKULL);
		
		// Stop the game and show death message
		gameTimer.stop();
		JOptionPane.showMessageDialog(this, "GAME OVER");
		
	}
	
	// Method for moving each ghost
	private void moveGhostsRandomly() {
		
		// Iterate through the 3 ghosts
		for (Mover ghost : ghostArray) {
			
			// Initialize the direction variable
			int direction = 0;
			
			// Check if the movement isn't in opposite directions
			do {
				direction = (int)(Math.random() * 4);
			} while (Math.abs(ghost.getDirection() - direction) == 2);
			
			// Set the ghost's direction to the random direction chosen
			ghost.setDirection(direction);
			
			// If pacman is alive, move the ghost
			if (!pacMan.isDead())
				performMove(ghost);
			
		}
		
	}
	
	private void moveGhostsPathfinding() {

		// Iterate through the 3 ghosts
		for (Mover ghost : ghostArray) {
			updatePath(ghost, pacMan);
			Stack<Integer> directionSequence = calcDirectionSequence(ghost, pacMan);
			
			if (!directionSequence.isEmpty()) ghost.setDirection(directionSequence.pop());
			
			// If pacman is alive, move the ghost
			if (!pacMan.isDead())
				performMove(ghost);
		}
		
	}
	
	private void updatePath(Mover self, Mover target) {
		for (Cell[] cellArray : mazeMatrix) {
			for (Cell cell : cellArray) {
				cell.setCost(Integer.MAX_VALUE);
			}
		}
		
		SortedSet<Cell> open = new TreeSet<>((o1, o2) -> {
		    if (o1.getCost() != o2.getCost()) {
		        return Integer.compare(o1.getCost(), o2.getCost());
		    } else if (o1.getRow() != o2.getRow()) {
		        return Integer.compare(o1.getRow(), o2.getRow());
		    } else {
		        return Integer.compare(o1.getColumn(), o2.getColumn());
		    }
		});
		SortedSet<Cell> closed = new TreeSet<>((o1, o2) -> {
		    if (o1.getCost() != o2.getCost()) {
		        return Integer.compare(o1.getCost(), o2.getCost());
		    } else if (o1.getRow() != o2.getRow()) {
		        return Integer.compare(o1.getRow(), o2.getRow());
		    } else {
		        return Integer.compare(o1.getColumn(), o2.getColumn());
		    }
		});
		Cell selfCell = mazeMatrix[self.getRow()][self.getColumn()];
		Cell targetCell = mazeMatrix[target.getRow()][target.getColumn()];

		selfCell.setCost(0);
		open.add(selfCell);
		
		while (!open.isEmpty()) {
			
			Cell current = open.first();
			open.remove(current);
			
			closed.add(current);
			
			if (current == targetCell) break;
			
			Cell[] neighbours = {mazeMatrix[current.getRow()-1][current.getColumn()], mazeMatrix[current.getRow()+1][current.getColumn()],
			                     mazeMatrix[current.getRow()][current.getColumn()-1], mazeMatrix[current.getRow()][current.getColumn()+1]};
			
			for (Cell neighbour : neighbours) {
				if (neighbour.getId() == Settings.ID_WALL || neighbour.getId() == Settings.ID_DOOR || closed.contains(neighbour))
					continue;
				
				else if (current.getCost() + 1 < neighbour.getCost() || !open.contains(neighbour)) {
					neighbour.setCost(current.getCost() + 1);
					neighbour.setParent(current.getRow(), current.getColumn());
					if (!open.contains(neighbour))
						open.add(neighbour);
					
				}
				
			}
			
		}
		
	}
	
	private Stack<Integer> calcDirectionSequence (Mover self, Mover target) {

		Stack<Integer> directionStack = new Stack<>();
		
		int currentRow = target.getRow();
		int currentColumn = target.getColumn();
		
		int dRow = 0;
		int dColumn = 0;
		
		Cell current = mazeMatrix[currentRow][currentColumn];
		
		int direction = -1;
		
		while (true) {
			currentRow = current.getRow();
			currentColumn = current.getColumn();
			
			current = mazeMatrix[current.getParentRow()][current.getParentColumn()];
			
			if (currentRow == self.getRow() && currentColumn == self.getColumn())
				return directionStack;
			
			dRow = currentRow - current.getRow();
			dColumn = currentColumn - current.getColumn();
			
			// Sending the direction value based on the delta values
			if (dRow == 0 && dColumn == -1)
				direction = 0;
			else if (dRow == -1 && dColumn == 0)
				direction = 1;
			else if (dRow == 0 && dColumn == 1)
				direction = 2;
			else
				direction = 3;
			
			directionStack.add(direction);
			
		}
		
	}

	// This method calls on other methods when the game is running
	public void actionPerformed(ActionEvent event) {

		// Increment the elapsed time by the time between ticks
		elapsedTimeInMS += Settings.TIME_BETWEEN_TICKS;
		
		// Check if the event is the game timer
		if (event.getSource() == gameTimer) {
			
			// Move PacMan and the ghosts
			performMove(pacMan);
			moveGhostsPathfinding();
			
		}
		
	}
}
