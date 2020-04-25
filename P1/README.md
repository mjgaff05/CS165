P1 - This assignment creates a maze that has a leprechaun in search of a pot of gold at the end of a rainbow. Some squares are empty while others are blocked by trees. Your code must find the path between the leprechaun and the pot of gold, without running into any trees or going outside the maze.

The assignment used files written by my school and I had to complete part of the main as well as two other methods

Code that I wrote it contained in MazeSolver.java
Lines 55-65 (completed in main)
  Loop through the maze row by row, column by column to find the Leprechaun's current position
  Once the Leprechaun is found, exit the loop and print the current row and column and store the values
  
Lines 93-126 (loadMaze)
  This method reads the maze from a text file that has a given structure
  After reading the text file the maze is stored into a 2D array with the characters from the text file
  If it is given an invalid file it throws a file not found exception
  
Lines 158-201 (findPath)
  This method calls itself recursively while searching for a valid path for the Leprechaun 
  It returns the status of the searched path (valid, invalid, off the grid, etc...)
  When the pot of gold is found this method reports that it is found and exits all the functions
