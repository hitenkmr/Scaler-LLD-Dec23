package projects.TicTacToe.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int dimension;
    private List<List<Cell>> matrix;
    /*
        dimension = 3
        [
            [_, _, _]
            [_, _, _]
            [_, _, _]
        ]
     */

    public Board(int dimension) {
        this.dimension = dimension;
        matrix = new ArrayList<>(); // outside list
        for(int i=0;i<dimension;i++){
            matrix.add(new ArrayList<>()); // adding the internal arraylist
            for(int j=0;j<dimension;j++){
                matrix.get(i).add(new Cell(i,j)); // adding the cells in the internal arraylist
            }
        }
    }

    public void displayBoard(){
        for(int i=0;i<dimension;i++){
            List<Cell> cells = matrix.get(i);
            for(Cell cell : cells){
                cell.displayCell();
            }
            System.out.println();
        }
    }

    public List<List<Cell>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<Cell>> matrix) {
        this.matrix = matrix;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public Board clone() {
        Board boardCopy = new Board(this.dimension);
        List<List<Cell>> matrixCopy = new ArrayList<>();
        for(List<Cell> cellList: matrix) {
            List<Cell> cellListCopy = new ArrayList<>();
            for(Cell cell: cellList) {
                cellListCopy.add(cell.clone());
            }
            matrixCopy.add(cellListCopy);
        }
        boardCopy.setMatrix(matrixCopy);
        return boardCopy;
    }

    public boolean isEmpty() {
        for(List<Cell> cells: getMatrix()) {
            for(Cell cell: cells) {
                if(cell.getCellState() == CellState.FILLED){
                    return false;
                }
            }
        }
        return true;
    }
}
