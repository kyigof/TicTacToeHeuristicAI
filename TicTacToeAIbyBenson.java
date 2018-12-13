import java.util.Scanner;
import javax.swing.JOptionPane;

public class TicTacToeAIbyBenson {
	
	public static void main(String[] args) {
		boolean a = true;
		while (a) {
			Scanner key = new Scanner(System.in);
			char[][] board = {{'7', '8', '9'},{'4', '5', '6'},{'1', '2', '3'}};
			int count = 0;
			boolean playerfirst = false;
			System.out.println("Do you want to go first? Y/N");
			if (key.next().equalsIgnoreCase("y")) {
				playerfirst = true;
			}
			int number = playerfirst ? 0 : 1;
			System.out.println("\nRemember that the grid is like this");
			System.out.println("7 8 9\n4 5 6\n1 2 3");
			while (true) {
				if (count % 2 == number) {
					drawboard(board);
					makemove('x', board);
				} else {
					findbestmove(board, count, playerfirst);
				}
				count++;
				if (count == 9 && checkwin(board) == 'n') {
					drawboard(board);
					break;
				} else if (checkwin(board) != 'n') {
					drawboard(board);
					break;
				}
			}
			String win = (checkwin(board) == 'n') ? "It's a draw!" 
				: "Player " + checkwin(board) + " won!";
			System.out.println("\n" + win);
			System.out.println("Play again? Y/N");
			if (!key.next().equalsIgnoreCase("y")) {
				a = false;
			}
		}
	}
		
	public static char[][] findbestmove(char[][] board, int count, boolean playerfirst) {
		char holdx;
		char holdo;
		boolean xwin = false;
		boolean blocked = false;
		boolean gotcorner = false;
		char[][] open = new char[3][3];
		
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board[row][col] != 'x' && board[row][col] != 'o') {
					open[row][col] = 'y';
				} else {
					open[row][col] = 'n';
				}
			}
		}
		if (open[1][1] == 'y') {
			board[1][1] = 'o';
			return board;
		}
		owins:
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (open[row][col] == 'y') {
					holdo = board[row][col];
					board[row][col] = 'o';
					if (checkwin(board) == 'o') {
						break owins;
					} else {
						board[row][col] = holdo;
					}
				}
			}
		}
		if (checkwin(board) == 'o') {
			return board;
		}
		blockedx:
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (open[row][col] == 'y') {
					holdx = board[row][col];
					board[row][col] = 'x';
					if (checkwin(board) == 'x') {
						board[row][col] = 'o';
						blocked = true;
						break blockedx;
					} else {
						board[row][col] = holdx;
					}
				}
			}
		}
		if (blocked) {
			return board;
		}
		if (playerfirst) {
			if (count == 3) {
				if (board[0][0] == 'x' && board[2][2] == 'x') {
					board[0][1] = 'o';
					gotcorner = true;
				} else if (board[2][0] == 'x' && board[0][2] == 'x') {
					board[0][1] = 'o';
					gotcorner = true;
				} else if (board[1][1] == 'x' && board[2][2] == 'x') {
					board[2][0] = 'o';
					gotcorner = true;
				} else if (board[2][0] == 'x' && board[1][2] == 'x') {
					board[2][2] = 'o';
					gotcorner = true;
				}
			} else if (count == 5) {
				if (board[0][0] == 'x' && board[1][2] == 'x' && board[2][1] == 'x') {
					board[2][0] = 'o';
					gotcorner = true;
				}
			}
		}
		if (count == 3 || count == 4 && !gotcorner) {
			if (board[0][0] == 'x' && board [2][1] == 'x') {
				board[2][0] = 'o';
				gotcorner = true;
			}
		}
		if (gotcorner) {
			return board;
		}
		playsafe:
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (open[row][col] == 'y') {
					holdo = board[row][col];
					board[row][col] = 'o';
					open[row][col] = 'n';
					for (int r = 0; r < 3; r++) {
						for (int c = 0; c < 3; c++) {
							if (open[r][c] == 'y') {
								holdx = board[r][c];
								board[r][c] = 'x';
								if (checkwin(board) != 'x') {
									board[r][c] = holdx;
								} else {
									xwin = true;
								}
							}
						}
					}
					if (!xwin) {
						break playsafe;
					} else {
						board[row][col] = holdo;
						open[row][col] = 'y';
						xwin = false;
					}
				}
			}
		}
		return board;
	}
	
	public static char checkwin(char[][] b) {
		char winner = 'n';
		winner = (b[0][0] == b[0][1] && b[0][1] == b[0][2]) ? b[0][0] : winner;
		winner = (b[1][0] == b[1][1] && b[1][1] == b[1][2]) ? b[1][0] : winner;
		winner = (b[2][0] == b[2][1] && b[2][1] == b[2][2]) ? b[2][0] : winner;
		winner = (b[0][0] == b[1][0] && b[1][0] == b[2][0]) ? b[0][0] : winner;
		winner = (b[0][1] == b[1][1] && b[1][1] == b[2][1]) ? b[0][1] : winner;
		winner = (b[0][2] == b[1][2] && b[1][2] == b[2][2]) ? b[0][2] : winner;
		winner = (b[2][0] == b[1][1] && b[1][1] == b[0][2]) ? b[2][0] : winner;
		winner = (b[0][0] == b[1][1] && b[1][1] == b[2][2]) ? b[0][0] : winner;
		return winner;
	}
	
	public static char[][] makemove(char player, char[][] board) {
		Scanner key = new Scanner(System.in);
		int ui = 0;
		int[] hold = new int[2];
		
		while (true) {
			System.out.println("\nPlayer " + player + " please make a move"); 
			System.out.println("Use numbers 1 - 9");
			ui = key.nextInt();
			if (ui < 4) {
				hold[0] = 2;
				hold[1] = ui - 1;
			} else if (ui < 7) {
				hold[0] = 1;
				hold[1] = ui - 4;
			} else if (ui < 10) {
				hold[0] = 0;
				hold[1] = ui - 7;
			}
			if (Character.isDigit(board[hold[0]][hold[1]])) {
				board[hold[0]][hold[1]] = player;
				break;
			}
			System.out.println("\nPlease enter a valid number");
		}
		return board;
	}
	
	public static void drawboard(char[][] board) {
		String prin = "";
		System.out.println("\nHere is the game board");
		for (int r = 0; r < board.length; r++) {
			System.out.println("\n-------------");
			System.out.print("| ");
			for (int c = 0; c < board[r].length; c++) {
				prin = (Character.isDigit(board[r][c])) ? "  | " : board[r][c] + " | ";
				System.out.print(prin);
			}
		}
		System.out.println("\n-------------");
	}
}