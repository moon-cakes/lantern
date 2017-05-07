package kalah.util;

import kalah.House;

import java.util.List;

/**
 * A utility class for returning the Kalah board in ASCII form
 */
public class AsciiUtil {

	private static String LINE_DECOR_1 = "+----+-------+-------+-------+-------+-------+-------+----+";
	private static String LINE_DECOR_2 = "|    |-------+-------+-------+-------+-------+-------|    |";

	private static String TOP_BOARD_TEMPLATE = "| P2 | 6[f] | 5[e] | 4[d] | 3[c] | 2[b] | 1[a] | s |";
	private static String BOTTOM_BOARD_TEMPLATE = "| s | 1[a] | 2[b] | 3[c] | 4[d] | 5[e] | 6[f] | P1 |";

	private static String PLAYER_1_TURN = "Player P1's turn - Specify house number or 'q' to quit: ";
	private static String PLAYER_2_TURN = "Player P2's turn - Specify house number or 'q' to quit: ";

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
	 * @param player2House      Player 2's house
	 * @param player1StoreSeeds The number of seeds in player 1's store
	 * @return A string representing the top board
	 */
	public static String getTopBoard(List<House> player2House, int player1StoreSeeds) {
		String board = replaceStoreSeeds(player1StoreSeeds, TOP_BOARD_TEMPLATE);
		board = replaceHouseSeeds(player2House, board);
		return board;
	}

	/**
	 * Get the bottom board of the game
	 *
	 * @param player1House      Player 1's house
	 * @param player2StoreSeeds The number of seeds in player 2's store
	 * @return A string representing the bottom board
	 */
	public static String getBottomBoard(List<House> player1House, int player2StoreSeeds) {
		String board = replaceStoreSeeds(player2StoreSeeds, BOTTOM_BOARD_TEMPLATE);
		board = replaceHouseSeeds(player1House, board);
		return board;
	}

	/**
	 * Update the current number of seed's in a Store with a new number of seeds
	 *
	 * @param seeds The number of seeds the store contains
	 * @param board      The template of the board to use
	 * @return A String containing the updated number of seeds in the store
	 */
	private static String replaceStoreSeeds(int seeds, String board) {
		return seeds < 10 ? board.replace("s", " " + String.valueOf(seeds)) :
				board.replace("s", String.valueOf(seeds));
	}

	/**
	 * Update the current number of seed's in a House with a new number of seeds
	 *
	 * @param house The house to get the seeds from
	 * @param board       The template of the board to use
	 * @return A string containing the updated number of seeds in houses
	 */
	private static String replaceHouseSeeds(List<House> house, String board) {
		char[] alphabet = "abcdef".toCharArray();
		for (int i = 0; i < house.size(); i++) {
			String characterToReplace = String.valueOf(alphabet[i]);
			int seeds = house.get(i).getSeeds();
			board = seeds < 10 ? board.replace(characterToReplace, " " + String.valueOf(seeds)) :
					board.replace(characterToReplace, String.valueOf(seeds));
		}
		return board;
	}
}