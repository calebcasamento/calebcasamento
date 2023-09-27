import java.util.Scanner;

public class ConnectFour2 {
    // Constants for the board dimensions and game pieces.
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final char EMPTY = ' ';
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';

    // 2D array for the game board and current player's piece.
    private char[][] board = new char[ROWS][COLS];
    private char currentPlayer = PLAYER_X;
    // Names for players.
    private String player1Name;
    private String player2Name;

    // Constructor initializes the board to be empty.
    public ConnectFour2() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Checks if the board is full.
    public boolean isBoardFull() {
        for (int j = 0; j < COLS; j++) {
            if (board[0][j] == EMPTY) return false;
        }
        return true;
    }

    // Drops the current player's piece in the chosen column.
    public boolean dropPiece(int col) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = currentPlayer;
                return checkWin(i, col);  // Checks if the move results in a win.
            }
        }
        return false;
    }

    // Checks if the current player's move results in a win.
    public boolean checkWin(int row, int col) {
        return checkDirection(row, col, 1, 0) >= 4 ||  // horizontal
               checkDirection(row, col, 0, 1) >= 4 ||  // vertical
               checkDirection(row, col, 1, 1) >= 4 ||  // main diagonal
               checkDirection(row, col, 1, -1) >= 4;   // anti diagonal
    }

    // Helper method to count consecutive pieces in a given direction.
    public int checkDirection(int row, int col, int dRow, int dCol) {
        int count = 1;
        // Checking in one direction.
        for (int i = 1; i < 4; i++) {
            int curRow = row + dRow * i;
            int curCol = col + dCol * i;
            if (curRow < 0 || curRow >= ROWS || curCol < 0 || curCol >= COLS || board[curRow][curCol] != currentPlayer) {
                break;
            }
            count++;
        }
        // Checking in the opposite direction.
        for (int i = 1; i < 4; i++) {
            int curRow = row - dRow * i;
            int curCol = col - dCol * i;
            if (curRow < 0 || curRow >= ROWS || curCol < 0 || curCol >= COLS || board[curRow][curCol] != currentPlayer) {
                break;
            }
            count++;
        }
        return count;
    }

    // Switch to the other player.
    public void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    // Display the game board.
    public void printBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
        }
        // Print board footer.
        for (int j = 0; j < COLS; j++) {
            System.out.print("---");
        }
        System.out.println();
        for (int j = 0; j < COLS; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println("\n");
    }

    // Returns the name of the current player.
    public String getCurrentPlayerName() {
        return (currentPlayer == PLAYER_X) ? player1Name : player2Name;
    }

    // Main game loop.
    public static void main(String[] args) {
        ConnectFour2 game = new ConnectFour2();
        Scanner scanner = new Scanner(System.in);

        // Set player names.
        System.out.print("Enter name for Player 1 (X): ");
        game.player1Name = scanner.nextLine();

        System.out.print("Enter name for Player 2 (O): ");
        game.player2Name = scanner.nextLine();

        // Continue until there's a winner or the board is full.
        while (true) {
            game.printBoard();
            System.out.println(game.getCurrentPlayerName() + "'s turn. Enter column (0-6):");
            int col = scanner.nextInt();
            if (col < 0 || col >= COLS || game.board[0][col] != EMPTY) {
                System.out.println("Invalid column. Try again.");
                continue;  // Re-ask for input.
            }
            if (game.dropPiece(col)) {
                game.printBoard();
                System.out.println(game.getCurrentPlayerName() + " wins!");
                break;  // End the game.
            }
            if (game.isBoardFull()) {
                game.printBoard();
                System.out.println("It's a tie!");
                break;  // End the game.
            }
            game.switchPlayer();  // Switch to the other player.
        }
    }
}
