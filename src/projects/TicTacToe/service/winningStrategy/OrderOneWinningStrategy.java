package projects.TicTacToe.service.winningStrategy;

import projects.TicTacToe.model.Board;
import projects.TicTacToe.model.Cell;
import projects.TicTacToe.model.Move;
import projects.TicTacToe.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneWinningStrategy implements WinningStrategy{

    private int dimension;
    private List<HashMap<Character, Integer>> rowHashMapList; // index in the list will correspond to the row number for hashmap identification
    private List<HashMap<Character, Integer>> colHashMapList; // index in the list will correspond to the col number for hashmap identification
    private HashMap<Character, Integer> leftDiagonal;
    private HashMap<Character, Integer> rightDiagonal;
    private HashMap<Character, Integer> cornerHashMap;


    public OrderOneWinningStrategy(int dimension) {
        this.dimension = dimension;
        rowHashMapList = new ArrayList<>();
        colHashMapList = new ArrayList<>();
        rightDiagonal = new HashMap<>();
        leftDiagonal = new HashMap<>();
        cornerHashMap = new HashMap<>();
        for(int i=0;i<dimension;i++){
            rowHashMapList.add(new HashMap<>());
            colHashMapList.add(new HashMap<>());
        }
    }

    @Override
    public Player checkWinner(Board board, Move lastMove) {
        Player player = lastMove.getPlayer();
        char symbol = player.getSymbol();
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();

        boolean winnerResult = winnerCheckForCorners(symbol, row, col)
                || checkAndUpdateForRowHashMap(row, symbol)
                || checkAndUpdateForColHashMap(col, symbol)
                || (checkLeftDiagonal(row, col) && checkAndUpdateLeftDiagonalHashmap(symbol))
                || (checkRightDiagonal(row, col) && checkAndUpdateRightDiagonalHashmap(symbol));

        if(winnerResult)
            return player;
        else
            return null;
    }

    private boolean checkLeftDiagonal(int row, int col){
        return row==col;
    }

    private boolean checkRightDiagonal(int row, int col){
        return ((row+col) == (dimension-1));
    }

    private boolean checkAndUpdateForRowHashMap(int row, char symbol){
        HashMap<Character, Integer> rowHashMap = rowHashMapList.get(row);
        return checkAndUpdate(rowHashMap, symbol);
    }

    private boolean checkAndUpdateForColHashMap(int col, char symbol){
        HashMap<Character, Integer> colHashMap = colHashMapList.get(col);
        return checkAndUpdate(colHashMap, symbol);
    }

    private boolean checkAndUpdateLeftDiagonalHashmap(char symbol){
        return checkAndUpdate(leftDiagonal, symbol);
    }

    private boolean checkAndUpdateRightDiagonalHashmap(char symbol){
        return checkAndUpdate(rightDiagonal, symbol);
    }

    //TODO : shorten this code using inbuilt hashmap methods
    private boolean checkAndUpdate(HashMap<Character, Integer> map, char symbol) {
        if(map.containsKey(symbol)){
            map.put(symbol, map.get(symbol)+1);
            return map.get(symbol) == dimension;
        } else{
            map.put(symbol, 1);
        }
        return false;
    }

    private boolean winnerCheckForCorners(char symbol, int row, int col) {
        boolean isTopLeftCorner = (row==0 && col==0);
        boolean isTopRightCorner = (row==0 && col==dimension-1);
        boolean isBottomLeftCorner = (row==dimension-1 && col==0);
        boolean isBottomRightCorner = (row==dimension-1 && col==dimension-1);

        // check and update only if we are on corner cell
        if(isTopLeftCorner || isTopRightCorner || isBottomLeftCorner || isBottomRightCorner) {
            if(cornerHashMap.containsKey(symbol)){
                cornerHashMap.put(symbol, cornerHashMap.get(symbol)+1);
                return cornerHashMap.get(symbol) == 4;
            } else{
                cornerHashMap.put(symbol, 1);
            }
        }
        return false;
    }
}

/*
        Steps for checking winner -
        1. Create N hashmaps for rows
        2. Create N hashmaps for cols
        3. Create 2 hashmaps for diagonals
        Basis on the last move played by the player
        4. Go to respective hashmaps, and update the frequency of that symbol,
        5. If the updated frequency becomes equal to N, then declare winner
        6. Also check for all 4 corners.


        Board size is N
        hashmaps -> N hashmaps for rows, N hashmaps for cols, 2 for daig
        List<HashMaps> -> row
        List<HashMaps> -> col
        HashMap leftDaig
        HashMap rightDaig

        N = 3

        X   _   _ -> <X = 1, O = 0> index in list = 0  || row : 0
        _   _   _ -> <Map> index in list = 0  || row : 1
        _   _   _ -> <Map> index in list = 0  || row : 2

        X   _   _     || Row[0] hashmap, Col[0] hashmap, leftDiagonal
        _   X   _     || Row[1] hashmap, Col[1] hashmap, leftDiagonal, rightDiagonal
        _   X   _     || Row[2] hashmap, Col[1] hashmap

        basis of row and column, diagonal hashmap checks are optional, row&col hashmaps check are mandatory

        a && b -> a is false -> false
        if( fun1() && fun2()) -> fun1() is false, fun2() is not computed -> short circuit

        a || b -> a is true -> true

 */