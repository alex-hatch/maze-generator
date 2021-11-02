package cs146F21.hatch.project3;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private final int SIZE;
    private ArrayList<MazeCell> listOfCells;

    public MazeGenerator(int size) {
        SIZE = size;
        listOfCells = new ArrayList<>();
        for(int i = 0; i < SIZE * SIZE; i++) {
            MazeCell newCell = new MazeCell(i);
            listOfCells.add(newCell);
        }

        // generate adjacency list
        for(MazeCell eachCell : listOfCells) {
            getAdjacentNeighbors(eachCell);
        }

        // print adjacency list for each cell
        for(MazeCell eachCell : listOfCells) {
            printAdjList(eachCell);
        }

        generateMaze();

        System.out.println();
        for(MazeCell eachCell : listOfCells) {
            printAdjList(eachCell);
        }
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
            for(MazeCell eachCell : validNeighborList) {
                System.out.println("Valid neighbor of cell " + currentCell.LOCATION + ": " + eachCell.LOCATION);
            }
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

        System.out.println("Currently removing walls between " + cell1.LOCATION + " and " + cell2.LOCATION);

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

        cell1.adjacentCells.remove(cell2);
        cell2.adjacentCells.remove(cell1);
    }

    private void printAdjList(MazeCell cell) {
        System.out.print(cell.LOCATION + ": ");
        for(MazeCell eachCell : cell.adjacentCells) {
            System.out.print(eachCell.LOCATION + " ");
        }
        System.out.println();
    }
}

class MazeCell {
    final int LOCATION;
    boolean[] walls;
    ArrayList<MazeCell> adjacentCells;
    boolean visited;

    public MazeCell(int location) {
        LOCATION = location;
        walls = new boolean[]{true, true, true, true};
        adjacentCells = new ArrayList<>();
        visited = false;
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
