package hatchjones.cs146.project3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeGeneratorTest {
    // need to test a 4x4, 7x7, and 10x10 grid
    MazeGenerator testing = new MazeGenerator(4);
    MazeGenerator odd = new MazeGenerator(7);
    MazeGenerator even = new MazeGenerator(10);
    
    //save the following tests in a seperate txt
    //have a saved expected output txt for each tested maze
    
    //Checks for correct DFS
    @Test
    void displayDepthFirstSolution() {
        testing.displayDepthFirstSolution();
        odd.displayDepthFirstSolution();
        even.displayDepthFirstSolution();
    }

    @Test
    void displayBreadthFirstSolution() {
        testing.displayBreadthFirstSolution();
        odd.displayBreadthPath();
        even.displayBreadthPath();
        
    }

    @Test
    void displayShortestPath() {
        testing.displayShortestPath();
        odd.displayShortestPath();
        even.displayShortestPath();
        
    }

    @Test
    void displayMaze() {
        testing.displayMaze();
        odd.displayMaze();
        even.displayMaze();
        
    }
}
