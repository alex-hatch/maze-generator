package cs146F21.hatch.project3;

import java.util.*;

public class MazeGenerator {
    private final int SIZE;
    private final ArrayList<MazeCell> listOfCells;
    private ArrayList<MazeCell> dfsCellPath;
    private ArrayList<MazeCell> bfsCellPath;
    private ArrayList<MazeCell> shortestPathList;

    public MazeGenerator(int size) {
        dfsCellPath = new ArrayList<>();
        SIZE = size;
        listOfCells = new ArrayList<>();
        bfsCellPath = new ArrayList<>();
        shortestPathList = new ArrayList<>();
        for(int i = 0; i < SIZE * SIZE; i++) {
            MazeCell newCell = new MazeCell(i);
            listOfCells.add(newCell);
        }

        // generate adjacency list
        for(MazeCell eachCell : listOfCells) {
            getAdjacentNeighbors(eachCell);
        }

/*        // print adjacency list for each cell
        for(MazeCell eachCell : listOfCells) {
            printAdjList(eachCell);
        }*/

        generateMaze();

/*        System.out.println("Maze Adjacency List");
        for(MazeCell eachCell : listOfCells) {
            printMazeAdj(eachCell);
        }*/

/*        System.out.println();
        displayMaze();*/



/*        printDFSPath();*/

        displayMaze();

        resetVisitState();
        depthFirstSolve(listOfCells.get(0));
        displayDepthFirstSolution();

        resetVisitState();
        breadthFirstSolve(listOfCells.get(0));
        displayBreadthFirstSolution();

        resetVisitState();
        shortestPath();
        displayShortestPath();

    }

    public void getAdjacentNeighbors(MazeCell cell) {
        int row = cell.LOCATION / SIZE;
        int column = cell.LOCATION % SIZE;


        // northern cell
        if(row != 0) {
            cell.adjacentCells.add(listOfCells.get(cell.LOCATION - SIZE));
        }

        //cell south of the current cell
        if(row != (SIZE - 1)) {
            cell.adjacentCells.add(listOfCells.get(cell.LOCATION + SIZE));
        }

        //cell left of the current cell
        if(column != 0) {
            cell.adjacentCells.add(listOfCells.get(cell.LOCATION - 1));
        }

        //right of the current
        if(column != SIZE - 1) {
            cell.adjacentCells.add(listOfCells.get(cell.LOCATION + 1));
        }
    }

    private void generateMaze() {
        Stack<MazeCell> cellStack = new Stack<>();
        int totalCells = SIZE * SIZE;
        MazeCell currentCell = listOfCells.get(0);
        int visitedCells = 1;

        Random r = new Random();

        while(visitedCells < totalCells) {
            ArrayList<MazeCell> validNeighborList = validNeighbors(currentCell);
            if(validNeighborList.size() > 0) {
                MazeCell randomValidNeighbor = validNeighborList.get(r.nextInt(validNeighborList.size()));
                removeWall(currentCell, randomValidNeighbor);
                cellStack.push(currentCell);
                currentCell = randomValidNeighbor;
                visitedCells++;
            } else {
                currentCell = cellStack.pop();
            }
        }
    }


    private ArrayList<MazeCell> validNeighbors(MazeCell cell) {
        ArrayList<MazeCell> validNeighborList = new ArrayList<>();
        for(MazeCell each : cell.adjacentCells) {
            if(each.hasAllWalls()) {
                validNeighborList.add(each);
            }
        }
        return validNeighborList;
    }

    private void removeWall(MazeCell cell1, MazeCell cell2) {

        // System.out.println("Currently removing walls between " + cell1.LOCATION + " and " + cell2.LOCATION);

        if(cell1.LOCATION - SIZE == cell2.LOCATION) {
            //remove the northern wall
            cell1.walls[0] = false;
            cell2.walls[1] = false;
        }

        else if(cell1.LOCATION + SIZE == cell2.LOCATION) {
            //remove south wall
            cell1.walls[1] = false;
            cell2.walls[0] = false;
        }
        else if(cell1.LOCATION + 1 == cell2.LOCATION) {
            //remove east wall
            cell1.walls[2] = false;
            cell2.walls[3] = false;
        }
        else if(cell1.LOCATION - 1 == cell2.LOCATION) {
            //remove the west wall
            cell1.walls[3] = false;
            cell2.walls[2] = false;

        }

        cell1.adjMazeCells.add(cell2);
        cell2.adjMazeCells.add(cell1);

        cell1.adjacentCells.remove(cell2);
        cell2.adjacentCells.remove(cell1);
    }

    private void displayDepthFirstSolution() {
        MazeCell[][] grid = new MazeCell[SIZE][SIZE];

        // Put cells into SIZExSIZE matrix for easier iteration and printing
        int count = 0;
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                grid[i][j] = listOfCells.get(count++);
            }
        }

        System.out.println("DFS Solution");
        for(int row = 0; row < SIZE; row++) {
            // Print top walls
            for(int column = 0; column < SIZE; column++) {
                if(grid[row][column].walls[0]) {
                    if(row == 0 && column == 0) {
                        System.out.print("+   ");
                    } else {
                        System.out.print("+---");
                    }
                } else {
                    System.out.print("+   ");
                }
            }
            System.out.println("+");

            // Print inner walls (east and west)
            for(int column = 0; column < SIZE; column++) {
                if(grid[row][column].walls[3]) {
                    if(dfsCellPath.contains(grid[row][column])) {
                        System.out.print("| " + grid[row][column].LOCATION % 10 + " ");
                    } else {
                        System.out.print("|   ");
                    }
                } else {
                    if(dfsCellPath.contains(grid[row][column])) {
                        System.out.print("  " + grid[row][column].LOCATION % 10 + " ");
                    } else {
                        System.out.print("    ");
                    }
                }
            }
            System.out.println("|");
        }

        // Print bottom
        for(int column = 0; column < SIZE; column++) {
            if(column == SIZE - 1) {
                System.out.print("+   ");
            } else {
                System.out.print("+---");
            }
        }

        // Bottom right +
        System.out.println("+\n");


    }

    private void displayBreadthFirstSolution() {
        MazeCell[][] grid = new MazeCell[SIZE][SIZE];

        // Put cells into SIZExSIZE matrix for easier iteration and printing
        int count = 0;
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                grid[i][j] = listOfCells.get(count++);
            }
        }

        System.out.println("BFS Solution");
        for(int row = 0; row < SIZE; row++) {
            // Print top walls
            for(int column = 0; column < SIZE; column++) {
                if(grid[row][column].walls[0]) {
                    if(row == 0 && column == 0) {
                        System.out.print("+   ");
                    } else {
                        System.out.print("+---");
                    }
                } else {
                    System.out.print("+   ");
                }
            }
            System.out.println("+");

            // Print inner walls (east and west)
            for(int column = 0; column < SIZE; column++) {
                if(grid[row][column].walls[3]) {
                    if(bfsCellPath.contains(grid[row][column])) {
                        System.out.print("| " + grid[row][column].LOCATION % 10 + " ");
                    } else {
                        System.out.print("|   ");
                    }
                } else {
                    if(bfsCellPath.contains(grid[row][column])) {
                        System.out.print("  " + grid[row][column].LOCATION % 10 + " ");
                    } else {
                        System.out.print("    ");
                    }
                }
            }
            System.out.println("|");
        }

        // Print bottom
        for(int column = 0; column < SIZE; column++) {
            if(column == SIZE - 1) {
                System.out.print("+   ");
            } else {
                System.out.print("+---");
            }
        }

        // Bottom right +
        System.out.println("+\n");


    }

    private void displayShortestPath() {
        MazeCell[][] grid = new MazeCell[SIZE][SIZE];

        // Put cells into SIZExSIZE matrix for easier iteration and printing
        int count = 0;
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                grid[i][j] = listOfCells.get(count++);
            }
        }

        System.out.println("Shortest Path Solution");
        for(int row = 0; row < SIZE; row++) {
            // Print top walls
            for(int column = 0; column < SIZE; column++) {
                if(grid[row][column].walls[0]) {
                    if(row == 0 && column == 0) {
                        System.out.print("+   ");
                    } else {
                        System.out.print("+---");
                    }
                } else {
                    System.out.print("+   ");
                }
            }
            System.out.println("+");

            // Print inner walls (east and west)
            for(int column = 0; column < SIZE; column++) {
                if(grid[row][column].walls[3]) {
                    if(shortestPathList.contains(grid[row][column])) {
                        System.out.print("| # ");
                    } else {
                        System.out.print("|   ");
                    }
                } else {
                    if(shortestPathList.contains(grid[row][column])) {
                        System.out.print("  # ");
                    } else {
                        System.out.print("    ");
                    }
                }
            }
            System.out.println("|");
        }

        // Print bottom
        for(int column = 0; column < SIZE; column++) {
            if(column == SIZE - 1) {
                System.out.print("+   ");
            } else {
                System.out.print("+---");
            }
        }

        // Bottom right +
        System.out.println("+\n");


    }

    private void displayMaze() {
        System.out.println("Unsolved Maze");
        MazeCell[][] grid = new MazeCell[SIZE][SIZE];

        // Put cells into SIZExSIZE matrix for easier iteration and printing
        int count = 0;
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                grid[i][j] = listOfCells.get(count++);
            }
        }


        for(int row = 0; row < SIZE; row++) {
            // Print top walls
            for(int column = 0; column < SIZE; column++) {
                if(grid[row][column].walls[0]) {
                    if(row == 0 && column == 0) {
                        System.out.print("+   ");
                    } else {
                        System.out.print("+---");
                    }
                } else {
                    System.out.print("+   ");
                }
            }
            System.out.println("+");

            // Print inner walls (east and west)
            for(int column = 0; column < SIZE; column++) {
                if(grid[row][column].walls[3]) {
                    System.out.print("|   ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println("|");
        }

        // Print bottom
        for(int column = 0; column < SIZE; column++) {
            if(column == SIZE - 1) {
                System.out.print("+   ");
            } else {
                System.out.print("+---");
            }
        }

        // Bottom right +
        System.out.println("+\n");

    }

    private void resetVisitState() {
        for(MazeCell eachCell : listOfCells) {
            eachCell.visited = false;
        }
    }

    private void depthFirstSolve(MazeCell cell) {
        if(cell == null) {
            return;
        }
        if(cell.LOCATION == (SIZE * SIZE) - 1) {
            dfsCellPath.add(cell);
            for(MazeCell eachCell : listOfCells) {
                eachCell.visited = true;
            }
            return;
        }
        dfsCellPath.add(cell);
        cell.visited = true;
        for(MazeCell each : cell.adjMazeCells) {
            if(!each.visited) {
                depthFirstSolve(each);
            }
        }
    }

    private void shortestPath() {
        resetVisitState();
        MazeCell startCell = listOfCells.get(0);
        MazeCell endCell = listOfCells.get(listOfCells.size() - 1);
        Queue<MazeCell> queue = new LinkedList();
        Stack<MazeCell> pathStack = new Stack<>();

        queue.add(startCell);
        pathStack.add(startCell);
        startCell.visited = true;

        while(!queue.isEmpty()) {
            MazeCell currentCell = queue.poll();
            for(MazeCell eachCell : currentCell.adjMazeCells) {
                if(!eachCell.visited) {
                    if(currentCell.LOCATION == endCell.LOCATION) {
                        break;
                    }
                    queue.add(eachCell);
                    eachCell.visited = true;
                    pathStack.add(eachCell);
                }
            }
        }

        shortestPathList.add(endCell);
        MazeCell currentSource = endCell;
        while(!pathStack.isEmpty()) {
            MazeCell currentCell = pathStack.pop();
            if(currentSource.adjMazeCells.contains(currentCell)) {
                shortestPathList.add(currentCell);
                currentSource = currentCell;
                if(currentCell == startCell) {
                    break;
                }
            }
        }
        System.out.println("Shortest Path Length: " + shortestPathList.size());
    }

    private void breadthFirstSolve(MazeCell cell) {
        Queue<MazeCell> queue = new LinkedList<>();
        cell.visited = true;
        queue.add(cell);

        while(!queue.isEmpty() && queue.peek().LOCATION != (SIZE * SIZE) - 1) {
            MazeCell currentCell = queue.poll();
            bfsCellPath.add(currentCell);
            for(MazeCell eachCell : currentCell.adjMazeCells) {
                if(!eachCell.visited) {
                    eachCell.visited = true;
                    queue.add(eachCell);
                }
            }
        }
        if(!queue.isEmpty()) {
            bfsCellPath.add(queue.poll());
        }
    }

    private void printAdjList(MazeCell cell) {
        System.out.print(cell.LOCATION + ": ");
        for(MazeCell eachCell : cell.adjacentCells) {
            System.out.print(eachCell.LOCATION + " ");
        }
        System.out.println();
    }

    private void printMazeAdj(MazeCell cell) {
        System.out.print(cell.LOCATION + ": ");
        for(MazeCell eachCell : cell.adjMazeCells) {
            System.out.print(eachCell.LOCATION + " ");
        }
        System.out.println();
    }

    private void printWalls(ArrayList<MazeCell> listOfCells) {
        for(MazeCell eachCell : listOfCells) {
            for(boolean eachWall : eachCell.walls) {
                System.out.print(eachWall);
            }
            System.out.println();
        }
    }

    private void printDFSPath() {
        depthFirstSolve(listOfCells.get(0));
        for(MazeCell eachCell : dfsCellPath) {
            System.out.print(eachCell.LOCATION + " ");
        }
        System.out.println();
    }

    private void printBFSPath() {
        breadthFirstSolve(listOfCells.get(0));
        for(MazeCell eachCell : bfsCellPath) {
            System.out.print(eachCell.LOCATION + " ");
        }
        System.out.println();
    }

    private void printShortestPathList() {
        shortestPath();
        for(MazeCell eachCell : shortestPathList) {
            System.out.print(eachCell.LOCATION + " ");
        }
        System.out.println();
    }
}

class MazeCell {
    final int LOCATION;
    boolean[] walls;

    // the adjacency list to represent all neighbors (regardless of wall status)
    // used to determine what adjacent cells have all their walls in tact
    ArrayList<MazeCell> adjacentCells;

    // The adjacency list to represent the maze
    // When populated will represent a random generated maze
    ArrayList<MazeCell> adjMazeCells;

    boolean visited;

     MazeCell(int location) {
        LOCATION = location;
        walls = new boolean[]{true, true, true, true};
        adjacentCells = new ArrayList<>();
        visited = false;
        adjMazeCells = new ArrayList<>();
    }

    public boolean hasAllWalls() {
        for(boolean eachWall : walls) {
            if(!eachWall) {
                return false;
            }
        }
        return true;
    }
}

class Test {
    public static void main(String[] args) {
        new MazeGenerator(3);
    }
}
