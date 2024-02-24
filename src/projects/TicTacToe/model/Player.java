package projects.TicTacToe.model;
import projects.TicTacToe.exception.OutOfBoundException;
import projects.TicTacToe.exception.CellAlreadyFilledException;

import java.util.Scanner;

public class Player {
    private int id;
    private String name;
    private char symbol;
    private PlayerType playerType;

    public Player() {
    }

    public Player(int id, String name, char symbol, PlayerType playerType) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
    }

    public Move makeMove(Board board){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the row for the target cell");
        int row = sc.nextInt();
        System.out.println("Enter the col for the target cell");
        int col = sc.nextInt();

        if(row<0 || col<0 || row>= board.getDimension() || col >= board.getDimension()) {
            throw new OutOfBoundException("row or col is out of bound");
        }
        Cell playedMoveCell = board.getMatrix().get(row).get(col);

        if(playedMoveCell.getCellState() == CellState.FILLED) {
            throw new CellAlreadyFilledException("cell already filled");
        }
        playedMoveCell.setCellState(CellState.FILLED);
        playedMoveCell.setPlayer(this);

        return new Move(playedMoveCell, this);
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
