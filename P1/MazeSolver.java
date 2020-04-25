import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MazeSolver {

    /**
     * Exception thrown when path is complete
     */
    public static class MazeSolvedException extends Exception {
        private static final long serialVersionUID = 1L;

        MazeSolvedException() {
            super("Found the pot of gold!");
        }
    }

    /**
     * Stores the user interface
     */
    private static UserInterface gui;

    /**
     * Data structures for maze
     */
    private static char[][] maze;

    /**
     * This is the starting point for your program.
     * This method calls the loadMaze method to read the maze file. <p>
     * <ol>
     *   <li> Implement the {@link MazeSolver#loadMaze(String)} method first,
     *        if correctly implemented the user interface should display it correctly.
     *   <li> After the <b>//YOUR CODE HERE! comment</b> write code to find the row and column of the leprechaun,
     *        which is then used for the initial call to {@link MazeSolver#findPath(int, int)}.
     *   <li> Since code has been provided to CALL the method {@link MazeSolver#findPath(int, int)},
     *        no other code in the main method is needed.
     * </ol>
     * @param args set run configurations to either choose or change the maze you are using
     * @throws FileNotFoundException exception thrown when program unable to recognize file name
     */
    public static void main(String[] args) throws FileNotFoundException {

        // Load maze from file
        int mazeNum = 1;
    	loadMaze(args[mazeNum]);
    	
        // Find leprechaun in maze
        int currentRow = -1;
        int currentCol = -1;

        // YOUR CODE HERE!
        // Loop through the maze row by row, column by column, until the 'L' is found giving the current location, then store the current location and exit both loops
        rowLoop:
        for(int i = 0; i < maze.length; i++) {
        	for(int j = 0; j < maze[i].length; j++) {
        		if(maze[i][j] == 'L') {
        			currentRow = i;
        			currentCol = j;
        			break rowLoop;
        		}
        	}
        }
        System.out.println("Current row: " + currentRow + " | Current column: " + currentCol);

        // Instantiate graphical user interface
        gui = new UserInterface(maze);

        try {
            // Solve maze, using recursion
            findPath(currentRow, currentCol);

            // Impossible maze, notify user interface
            gui.sendStatus(CallbackInterface.SearchStatus.PATH_IMPOSSIBLE, -1, -1); // Cannot solve

        } catch (MazeSolvedException e) {

            // Maze solved, exit normally
        }

    }



    /**
     * Reads a maze file into the 2-dimensional array declared as a class variable.
     *
     * @param filename the name of the maze file.
     * @throws FileNotFoundException exception thrown when program unable to recognize file name
     */
     static void loadMaze(String filename) throws FileNotFoundException {
        // YOUR CODE HERE!
    	// Try to create a scanner object using the provided filename
 		try {
 			// Create a scanner object using the given filename and create a string used to store lines from the file
 			Scanner scan = new Scanner(new File(filename));
 			String mazeLine;
 			System.out.println("reading from file: "  +  filename + "\n");
 			
 			// Store the number of rows and columns from the first two items in the file and initiate a maze array with the given size
 			int cols = Integer.parseInt(scan.next());
 			int rows = Integer.parseInt(scan.next());
			System.out.print("Maze has: " + cols + " columns and " + rows + " rows.\n");
			maze = new char[rows][cols];
 			mazeLine = scan.nextLine();
			
			// Loop through the input file line by line and keep looping while the file has another line. Store the current line in a string called mazeLine
 			for(int i = 0; i < rows; i++) {
 				if(scan.hasNextLine()) {
 					mazeLine = scan.nextLine();
 					// Loop through line character by character storing each character into the maze
 					for(int j = 0; j<mazeLine.length(); j++) {
 						maze[i][j] = mazeLine.charAt(j);
 						System.out.print(maze[i][j]);
 					}
 					System.out.println();
 				} else { 
 					
 				}
 			}		
 			scan.close();	// Close the scanner object
 		// Catch the exception thrown if the file is not found and print the given error
 		} catch(FileNotFoundException E){
 			System.out.println(E + "\n");
 		}
    }

    /**
     * This method calls itself recursively to find a path from the leprechaun to the pot of gold. <p>
     * It also notifies the user interface about the path it is searching. <p>
     * Here is a list of the actions taken in findPath, based on the square it is searching:
     * <p>
     * <ul>
     *   <li> If the row or column is not on the board, notify the user interface calling
     *        sendStatus with PATH_ILLEGAL, and return false.
     *   <li> If maze[row][col] == 'G', then you have found a path to the pot of gold, notify the user
     *        interface by calling sendStatus with PATH_COMPLETE and throw the MazeSolvedException to terminate execution.
     *   <li> If maze[row][col] == 'S', then the current square is already part of a valid path,
     *        do not notify the user interface, just return false.
     *   <li> If maze[row][col] == 'W', then the current square is already part of an invalid path,
     *        not notify the user interface, just return false.
     *   <li> If maze[row][col] == '#', then the current square contains a blocking tree,
     *        do not notify the user interface, just return false.
     *   <li> If current square contains a space or 'L', notify the user interface by calling sendStatus with PATH_VALID,
     *        then recursively call findPath with the row and column of the surrounding squares,
     *        in order: UP, RIGHT, DOWN, and LEFT.
     *   <li> If any of the recursive calls return true, return true from the current invocation of findPath,
     *        otherwise notify the user interface by calling sendStatus with PATH_INVALID, and return false.
     * </ul>
     *
     * @param row the current row of the leprechaun
     * @param col the current column of the leprechaun
     * @return true for a valid path, false otherwise
     * @throws MazeSolvedException exception thrown when maze is complete
     */
    public static boolean findPath(int row, int col) throws MazeSolvedException {
        // YOUR CODE HERE!
    	if ((row > (maze.length - 1)) | (col > (maze[0].length - 1)) | (row < 0) | (col < 0)) {
    		//Send status that path is illegal
    		gui.sendStatus(CallbackInterface.SearchStatus.PATH_ILLEGAL, row, col);
    		return false;
    	}
    	
    	else {
    		char search = maze[row][col];
    		switch(search) {
    		case 'G':
    			//Send staus with path complete
    			gui.sendStatus(CallbackInterface.SearchStatus.PATH_COMPLETE, row, col);
    			//Throw mazeSolvedException
    			throw new MazeSolvedException();
    		case 'S':
    			return false;
    		case 'W':
    			return false;
    		case '#':
    			return false;
    		default:
    			if ((search == 'L') | (search == ' ')) {
    				//Send status with path valid
    				gui.sendStatus(CallbackInterface.SearchStatus.PATH_VALID, row, col);
    				if(findPath(row - 1, col) == true) {return true;}
    				else if(findPath(row, col + 1) == true) {return true;}
    				else if(findPath(row + 1, col) == true) {return true;}
    				else if(findPath(row, col - 1) == true) {return true;}
    				else {
    					//Send status that path is invalid
    					gui.sendStatus(CallbackInterface.SearchStatus.PATH_INVALID, row, col);
    					return false;
    				}
    			}
    			
    			else{
    				//Send status that path is invalid
    				System.out.println("Invalid character found in the maze: " + search);
    				gui.sendStatus(CallbackInterface.SearchStatus.PATH_INVALID, row, col);
    				return false;
    			}
    		}
    	}
    }

}

	
