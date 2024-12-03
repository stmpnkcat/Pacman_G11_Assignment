import java.awt.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

/*
 * This class is a panel that displays the actual game on the frame
 */
public class Board extends JPanel{
	
	// Create a new cell matrix to store every cell on the screen
	private Cell[][] mazeArray = new Cell[25][27];
	
	// This method is the constructor called when the board is initialized
	public Board() {
		
		// Setting the board's layout to place the cells in the right spots
		setLayout(new GridLayout(25, 27));
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
}
