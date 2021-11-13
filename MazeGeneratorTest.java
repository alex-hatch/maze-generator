package hatchjones.cs146.project3;
import java.util.*;
import java.io.*;
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
        //creates a txt file containing the DFS from the output of code
        
        //DFS txt file for the mazes 
        File dfs1= new File("OutputDFS.txt");
        FileOutputStream fos1=new FileOutputStream(dsf1);
        PrintWriter test=new PrintWriter(fos1);
        String hold_4= testing.displayDepthFirstSolution();
        String hold_7=odd.displayDepthFirstSolution();
        String hold_10=even.displayDepthFirstSolution();
        test.write("Display First Search"+"\n 4x4 Maze"+hold_4 +"\n 7x7 Maze"+hold_7+"\n 10x10 Maze"+hold_10);
        test.flush();
        fos1.close();
        test.close();
       
        
    }

    @Test
    void displayBreadthFirstSolution() {

        //BFS txt file for the mazes
        File bfs1= new File("OutputBFS.txt");
        FileOutputStream fos2=new FileOutputStream(bfs1);
        PrintWriter test2=new PrintWriter(fos2);
        String holder4= testing.displayBreadthFirstSolution();
        String holder7= odd.displayBreadthFirstSolution();
        String holder10= even.displayBreadthFirstSolution();
        test2.write("Breadth First Search"+"\n 4x4 Maze"+holder4 +"\n 7x7 Maze"+holder7+"\n 10x10 Maze"+holder10);
        test2.flush();
        bfs1.close();
        test2.close();
        
    }

    @Test
    void displayShortestPath() {

        //dSp txt file for the mazes
        File dsp1= new File("Outputdsp.txt");
        FileOutputStream fos3=new FileOutputStream(dsp1);
        PrintWriter test3=new PrintWriter(fos3);
        String holding4= testing.displayShortestPath();
        String holding7= odd.displayShortestPath();
        String holding10= even.displayShortestPath();
        test3.write("Display Shortest Path"+"\n 4x4 Maze"+holding4 +"\n 7x7 Maze"+holding7+"\n 10x10 Maze"+holding10);
        test3.flush();
        dsp1.close();
        test3.close();
      
        
    }

    @Test
    void displayMaze() {
        
        //dM txt file for the mazes
        File dM1= new File("OutputdM.txt");
        FileOutputStream fos4=new FileOutputStream(dM1);
        PrintWriter test4=new PrintWriter(fos4);
        String hold_4= testing.displayShortestPath();
        String hold_7= odd.displayShortestPath();
        String hold_10= even.displayShortestPath();
        test4.write("Display Maze"+"\n 4x4 Maze"+hold_4 +"\n 7x7 Maze"+hold_7+"\n 10x10 Maze"+hold_10);
        test4.flush();
        dM1.close();
        test4.close();
    
        
    }
}
