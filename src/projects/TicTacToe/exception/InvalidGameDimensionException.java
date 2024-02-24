package projects.TicTacToe.exception;

public class InvalidGameDimensionException extends RuntimeException{
    public InvalidGameDimensionException() {
    }

    public InvalidGameDimensionException(String message) {
        super(message);
    }

    public InvalidGameDimensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGameDimensionException(Throwable cause) {
        super(cause);
    }

    public InvalidGameDimensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

