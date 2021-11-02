package cs146F21.hatch.project3;

import java.util.ArrayList;
import java.util.Stack;

public class MazeGenerator {
    int[][] adjMatrix;
    MazeCell[][] maze;
    int SIZE;

    public MazeGenerator(int size) {
        this.adjMatrix = new int[size][size];
        this.SIZE = size * size;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                this.adjMatrix[i][j] = 0;
            }
        }
    }

    public void generateMaze() {
        Stack<MazeCell> mazeCells = new Stack<>();
        MazeCell currentCell = maze[0][0];
        currentCell.isVisited = true;
        int visitedCells = 1;
        int totalCells = SIZE;

        while(visitedCells < totalCells) {
            if(){

            }

        }


    }

    public class MazeCell {
        static final int WHITE = 1;
        static final int GREY = 2;
        static final int BLACK = 3;

        int visitBfsOrder;
        int distance;
        int label;
        int x;
        int y;
        int visitNum = 0;
        int color = WHITE;
        boolean isVisited = false;
        boolean isInPath = false;
        int dtime;
        int ftime;
        Maze.Vertex parent;
        int visitedOrder = 0;

        // {north, south, east, west}
        boolean[] walls = {true, true, true, true};
        ArrayList<MazeCell> neighbors = new ArrayList<>();

        public MazeCell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean hasAllWalls() {
            for(boolean each : walls) {
                if(!each) {
                    return false;
                }
            }
            return true;
        }
    }
}
