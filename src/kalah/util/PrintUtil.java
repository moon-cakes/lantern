package kalah.util;

import com.qualitascorpus.testsupport.IO;
import kalah.House;
import kalah.Player;

import java.util.List;

/**
 * A utility class for printing
 */
public class PrintUtil {

	public static int CANCEL_CODE = -1;
	private static int MIN_HOUSE_NUMBER = 1;
	private static int MAX_HOUSE_NUMBER = Player.NUMBER_OF_HOUSES;

	private static String LINE_DECOR_1 = "+----+-------+-------+-------+-------+-------+-------+----+";
	private static String LINE_DECOR_2 = "|    |-------+-------+-------+-------+-------+-------|    |";

	private static String TOP_BOARD_TEMPLATE = "| P2 | 6[f] | 5[e] | 4[d] | 3[c] | 2[b] | 1[a] | s |";
	private static String BOTTOM_BOARD_TEMPLATE = "| s | 1[a] | 2[b] | 3[c] | 4[d] | 5[e] | 6[f] | P1 |";

	private static String PLAYER_1_TURN = "Player P1's turn - Specify house number or 'q' to quit: ";
	private static String PLAYER_2_TURN = "Player P2's turn - Specify house number or 'q' to quit: ";

	public static int printPromptMessage(IO io, Player currentPlayer, Player player1) {
		return io.readInteger(currentPlayer.equals(player1) ? PLAYER_1_TURN : PLAYER_2_TURN, MIN_HOUSE_NUMBER,
				MAX_HOUSE_NUMBER, CANCEL_CODE, "q");
	}

	public static void printBoard(IO io, Player player1, Player player2) {
		io.println(LINE_DECOR_1);
		io.println(PrintUtil.getTopBoard(player2.getHouses(), player1.getStore().getSeeds()));
		io.println(LINE_DECOR_2);
		io.println(PrintUtil.getBottomBoard(player1.getHouses(), player2.getStore().getSeeds()));
		io.println(LINE_DECOR_1);
	}

	public static void printChosenHouseIsEmpty(IO io) {
		io.println("House is empty. Move again.");
	}

	public static void printGameOver(IO io) {
		io.println("Game over");
	}

	public static void printScores(IO io, int player1Score, int player2Score) {
		io.println("\tplayer 1:" + player1Score);
		io.println("\tplayer 2:" + player2Score);

		if (player1Score > player2Score) {
			io.println("Player 1 wins!");
		} else if (player2Score > player1Score) {
			io.println("Player 2 wins!");
		} else {
			io.println("A tie!");
		}
	}

	/**
	 * Get the top board of the game
	 *
	 * @param player2House      Player 2's house
	 * @param player1StoreSeeds The number of seeds in player 1's store
	 * @return A string representing the top board
	 */
	private static String getTopBoard(List<House> player2House, int player1StoreSeeds) {
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
	private static String getBottomBoard(List<House> player1House, int player2StoreSeeds) {
		String board = replaceStoreSeeds(player2StoreSeeds, BOTTOM_BOARD_TEMPLATE);
		board = replaceHouseSeeds(player1House, board);
		return board;
	}

	/**
	 * Update the current number of seed's in a Store with a new number of seeds
	 *
	 * @param seeds The number of seeds the store contains
	 * @param board The template of the board to use
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
	 * @param board The template of the board to use
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