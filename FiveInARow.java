import java.util.Random;
import java.util.Scanner;

public class FiveInARow {
    private static final int SIZE = 8; // Spielfeldgröße
    private static final char EMPTY = '.';
    private static final char PLAYER = 'X';
    private static final char ENEMY = 'O';
    private char[][] board = new char[SIZE][SIZE]; // Das Spielfeld
    private Random random = new Random();

    public FiveInARow() {
        // Spielfeld initialisieren
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean playerTurn = true;
        System.out.println("You challenges TheBigBoss to a Game: Five in a Row!");
        System.out.println("");
        System.out.println("Welcome to Five in a Row! Get 5 stones in a row to win!");
        printBoard();

        while (true) {
            if (playerTurn) {
                System.out.println("Your turn! Choose a column (1-8):");
                int column = scanner.nextInt() - 1;

                if (column >= 0 && column < SIZE && placeStone(column, PLAYER)) {
                    if (triggerSpecialMove()) {
                        System.out.println("Special move triggered! Enter the row and column of the enemy's stone to replace (1-8):");
                        System.out.print("Row: ");
                        int row = scanner.nextInt() - 1;
                        System.out.print("Column: ");
                        int col = scanner.nextInt() - 1;
                        if (isValidSpecialMove(row, col)) {
                            board[row][col] = PLAYER;
                            System.out.println("Special move completed!");
                        } else {
                            System.out.println("Invalid special move. Skipping.");
                        }
                    }

                    printBoard();
                    if (checkWin(PLAYER)) {
                        System.out.println("Congratulations! You have 5 stones in a row. You win!");
                        break;
                    }
                } else {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }
            } else {
                System.out.println("TheBigBoss's turn...");
                if (!executeEnemyStrategy()) {
                    int column = random.nextInt(SIZE); // Zufällige Spalte
                    placeStone(column, ENEMY);
                }

                printBoard();
                if (checkWin(ENEMY)) {
                    System.out.println("TheBigBoss has 5 stones in a row. You lose.");
                    break;
                }
            }

            playerTurn = !playerTurn; // Zug wechseln
        }

        scanner.close();
    }

    private void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean placeStone(int column, char stone) {
        for (int row = SIZE - 1; row >= 0; row--) {
            if (board[row][column] == EMPTY) {
                board[row][column] = stone;
                return true;
            }
        }
        return false; // Spalte ist voll
    }

    private boolean checkWin(char stone) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkDirection(i, j, 0, 1, stone) || // Horizontal
                    checkDirection(i, j, 1, 0, stone) || // Vertikal
                    checkDirection(i, j, 1, 1, stone) || // Diagonal \
                    checkDirection(i, j, 1, -1, stone)) { // Diagonal /
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol, char stone) {
        int count = 0;
        for (int k = 0; k < 5; k++) {
            int r = row + k * dRow;
            int c = col + k * dCol;
            if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == stone) {
                count++;
                if (count == 5) {
                    return true; // 5 Steine in einer Reihe gefunden
                }
            } else {
                break; // Kein Stein des Spielers oder Gegners
            }
        }
        return false;
    }

    private boolean triggerSpecialMove() {
        return random.nextInt(100) < 50; // 2% Chance
    }

    private boolean isValidSpecialMove(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == ENEMY;
    }

    private boolean executeEnemyStrategy() {
        // Versuche, eine Zweierreihe zu erstellen
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ENEMY && checkDirection(i, j, 0, 1, ENEMY)) {
                    if (placeStone(j, ENEMY)) return true;
                }
            }
        }
        return false; // Keine Zweierreihe möglich
    }

    public static void main(String[] args) {
        FiveInARow game = new FiveInARow();
        game.playGame();
    }
}


