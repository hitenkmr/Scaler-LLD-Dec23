package projects.TicTacToe.exception;

public class CellAlreadyFilledException extends RuntimeException{
    public CellAlreadyFilledException() {
    }

    public CellAlreadyFilledException(String message) {
        super(message);
    }

    public CellAlreadyFilledException(String message, Throwable cause) {
        super(message, cause);
    }

    public CellAlreadyFilledException(Throwable cause) {
        super(cause);
    }

    public CellAlreadyFilledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

