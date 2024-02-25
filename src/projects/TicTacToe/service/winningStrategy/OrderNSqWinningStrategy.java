package projects.TicTacToe.service.winningStrategy;

import projects.TicTacToe.model.Board;
import projects.TicTacToe.model.Move;
import projects.TicTacToe.model.Player;

public class OrderNSqWinningStrategy implements WinningStrategy{
    @Override
    public Player checkWinner(Board board, Move lastMove) {
        return null;
    }
    public boolean isGameDraw() {
        return false;
    }
}

/*

* $ _
$ * *
_ * $

*/