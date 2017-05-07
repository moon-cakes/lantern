package kalah.util;

import kalah.model.Pit;

import java.util.List;

/**
 * A utility class for returning the Kalah board in ASCII form
 */
public class AsciiUtil {

	private static String LINE_DECOR_1 = "+----+-------+-------+-------+-------+-------+-------+----+";
	private static String LINE_DECOR_2 = "|    |-------+-------+-------+-------+-------+-------|    |";

	private static String TOP_BOARD_TEMPLATE = "| P2 | 6[f] | 5[e] | 4[d] | 3[c] | 2[b] | 1[a] | s |";
	private static String BOTTOM_BOARD_TEMPLATE = "| s | 1[a] | 2[b] | 3[c] | 4[d] | 5[e] | 6[f] | P1 |";

	private static String PLAYER_1_TURN = "Player 1's turn - Specify house number or 'q' to quit:";
	private static String PLAYER_2_TURN = "Player 2's turn - Specify house number or 'q' to quit:";

	public static String getLineDecor1() {
		return LINE_DECOR_1;
	}

	public static String getLineDecor2() {
		return LINE_DECOR_2;
	}

	public static String getPlayer1Turn() {
		return PLAYER_1_TURN;
	}

	public static String getPlayer2Turn() {
		return PLAYER_2_TURN;
	}

	/**
	 * Get the top board of the game
	 *
	 * @param player2Board      Player 2's board
	 * @param player1StoreSeeds The number of seeds in player 1's store
	 * @return A string representing the top board
	 */
	public static String getTopBoard(List<Pit> player2Board, int player1StoreSeeds) {
		String board = replaceStoreSeeds(player1StoreSeeds, TOP_BOARD_TEMPLATE);
		board = replaceHouseSeeds(player2Board, board);
		return board;
	}

	/**
	 * Get the bottom board of the game
	 *
	 * @param player1Board      Player 1's board
	 * @param player2StoreSeeds The number of seeds in player 2's store
	 * @return A string representing the bottom board
	 */
	public static String getBottomBoard(List<Pit> player1Board, int player2StoreSeeds) {
		String board = replaceStoreSeeds(player2StoreSeeds, BOTTOM_BOARD_TEMPLATE);
		board = replaceHouseSeeds(player1Board, board);
		return board;
	}

	/**
	 * Update the current number of seed's in a Store with a new number of seeds
	 *
	 * @param storeSeeds The number of seeds the store should contain
	 * @param board      The template of the board to use
	 * @return A String containing the updated number of seeds in the store
	 */
	private static String replaceStoreSeeds(int storeSeeds, String board) {
		if (storeSeeds < 10) {
			return board.replace("s", " " + String.valueOf(storeSeeds));
		} else {
			return board.replace("s", String.valueOf(storeSeeds));
		}
	}

	/**
	 * Update the current number of seed's in a House with a new number of seeds
	 *
	 * @param playerBoard The player's board to get the seeds from
	 * @param board       The template of the board to use
	 * @return A string containing the updated number of seeds in houses
	 */
	private static String replaceHouseSeeds(List<Pit> playerBoard, String board) {
		char[] alphabet = "abcdef".toCharArray();
		for (int i = 0; i < playerBoard.size() - 1; i++) {
			String characterToReplace = String.valueOf(alphabet[i]);
			int seeds = playerBoard.get(i).getSeeds();
			if (seeds < 10) {
				board = board.replace(characterToReplace, " " + String.valueOf(seeds));
			} else {
				board = board.replace(characterToReplace, String.valueOf(seeds));
			}
		}
		return board;
	}
}