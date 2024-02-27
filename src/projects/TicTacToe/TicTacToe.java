package projects.TicTacToe;

import projects.TicTacToe.controller.GameController;
import projects.TicTacToe.model.*;
import projects.TicTacToe.service.winningStrategy.WinningStrategyName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Class diagram
// https://lucid.app/lucidchart/8315f1ec-ae0d-47cf-8cb9-b168e7db8a65/edit?invitationId=inv_a3b94719-f414-43ce-a31c-313a26b2ec4d&page=0_0#

public class TicTacToe {
    public static void main(String[] args) throws InterruptedException {
        GameController gameController = new GameController();
        int id = 1;
        List<Player> players = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to TicTacToe Game");
        System.out.println("Please enter the dimension for the board");
        int dimension = sc.nextInt();
        System.out.println("Do you want a bot in the game ? Y or N");
        String botAns = sc.next();
        if(botAns.equalsIgnoreCase("Y")){
            Player bot = new Bot(id++, '$', BotDifficultyLevel.HARD);
            players.add(bot);
        }
        while(id < dimension){
            System.out.println("Please enter the player name:");
            String playerName = sc.next();
            System.out.println("Please enter the player symbol:");
            char symbol = sc.next().charAt(0);
            Player newPlayer = new Player(id++, playerName, symbol, PlayerType.HUMAN);
            players.add(newPlayer);
        }
        //Collections.shuffle(players); //randomise the player list
        Game game = gameController.createGame(dimension, players, WinningStrategyName.ORDERONEWINNINGSTRATEGY );
        int playerIndex = -1;

        Move lastMovePlayed=null;
        Player lastPlayerPlayed=null;

        while(game.getGameStatus().equals(GameStatus.IN_PROGRESS)){
            System.out.println("Current board status");
            gameController.displayBoard(game);

            // ask player to undo last move
            undoLastMove(lastPlayerPlayed, lastMovePlayed, game, gameController, dimension);

            playerIndex++;
            playerIndex = playerIndex % players.size();

            Player currentPlayer = players.get(playerIndex);
            // clone last board and set as current board
            Board newBoard = game.getCurrentBoard().clone();
            game.setCurrentBoard(newBoard);

            Move movePlayed = gameController.executeMove(game, players.get(playerIndex));
            game.getMoves().add(movePlayed); // add moves
            game.getBoardStates().add(newBoard); // add board states

            // set last move and player(for undo logic)
            lastMovePlayed = movePlayed;
            lastPlayerPlayed = currentPlayer;

            // get the winner
            Player winner = gameController.checkWinner(game, movePlayed);
            if(winner != null){
                System.out.println("WINNER IS : " + winner.getName());
                break;
            }

            if(checkIfGameIsDraw(game, gameController)) {
                System.out.println("GAME IS DRAW");
                break;
            }
        }
        System.out.println("Final Board Status");
        gameController.displayBoard(game);

        replayGame(game, gameController);
    }

    private static void undoLastMove(Player lastPlayerPlayed, Move lastMovePlayed, Game game, GameController gameController, int dimension) {
        // undo logic
        Board currentBoard = game.getCurrentBoard();
        if(lastPlayerPlayed!= null && lastMovePlayed !=null && lastPlayerPlayed.getPlayerType() == PlayerType.HUMAN && !currentBoard.isEmpty()) {
            System.out.print("Do you want to undo last move?");
            Scanner sc = new Scanner(System.in);
            String playerAns = sc.next();
            if(playerAns.equalsIgnoreCase("Y")) {
                gameController.undoLastMove(game);
                if(!game.getBoardStates().isEmpty()) {
                    System.out.println("Board state is");
                    Board board = game.getBoardStates().getLast();
                    gameController.displayBoard(game);
                } else {
                    game.setCurrentBoard(new Board(dimension));
                    System.out.println("Board state is");
                    gameController.displayBoard(game);
                }
            }
        }
    }

    private static boolean checkIfGameIsDraw(Game game, GameController gameController) {
        // check if game is draw after the board is fully filled
        boolean drawOnFullBoard = game.getMoves().size() == game.getCurrentBoard().getDimension() * game.getCurrentBoard().getDimension();

        // check if game is a draw in between
        boolean drawInBetween = gameController.isGameDraw(game);

        return drawInBetween || drawOnFullBoard;
    }

    // replay game logic
    private static void replayGame(Game game, GameController gameController) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to replay? ");
        String replayLogicInput = sc.next();
        if(replayLogicInput.equalsIgnoreCase("Y")) {
            System.out.println("Replaying game again!");
            for(int i=0;i<game.getBoardStates().size();i++) {
                Board board = game.getBoardStates().get(i);
                Move move = game.getMoves().get(i);
                Player player = move.getPlayer();
                System.out.println(player.getName() + " played " + player.getSymbol() + " on " +  "(" + move.getCell().getRow() + ", " + move.getCell().getCol() + ")");
                System.out.println("Current game Status");
                game.setCurrentBoard(board);
                gameController.displayBoard(game);

                // wait for 1 sec
                Thread.sleep(1000);
            }
        }
    }
}
/*
        dimension = 5, players = 4
        id = 1 to 4
        if(bot is present) || b p1 p2 p3
        id++ -> id = 2

        while(id < dimension)
            p1 => id++ -> 3
            p2 => id++ -> 4
            p3 => id++ -> 5

                      0  1  2  3
    List<Players> => p1 p2 p3 p4
                     i

                     i%4 => 0%4 => 0
                            1%4 => 1
                            2%4 => 2
                            3%4 => 3
                            4%4 => 0
 */