package kalah.util;

import com.qualitascorpus.testsupport.IO;
import kalah.model.House;
import kalah.model.Player;

import java.util.Arrays;
import java.util.List;

/**
 * A utility class for printing
 */
public class PrintUtil {

	public static int printPromptMessage(IO io, Player currentPlayer, Player player1, int minHouseNumber, int
			maxHouseNumber, int cancelCode) {
		String prompt = "Player P%d's turn - Specify house number or 'q' to quit: ";
		return io.readInteger(currentPlayer.equals(player1) ? String.format(prompt, 1) : String.format(prompt, 2),
				minHouseNumber, maxHouseNumber, cancelCode, "q");
	}

	public static void printBoard(IO io, Player player1, Player player2, int maxHouseNumber) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("+----");
		for (int i = 0; i < maxHouseNumber; i++) {
			stringBuilder.append("+-------");
		}
		stringBuilder.append("+----+");
		String lineDecor1 = stringBuilder.toString();

		stringBuilder.setLength(0);
		stringBuilder.append("|    |");
		for (int i = 0; i < maxHouseNumber - 1; i++) {
			stringBuilder.append("-------+");
		}
		stringBuilder.append("-------");
		stringBuilder.append("|    |");
		String lineDecor2 = stringBuilder.toString();

		io.println(lineDecor1);
		io.println(PrintUtil.getTopBoard(player2.getHouses(), player1.getStore().getSeeds()));
		io.println(lineDecor2);
		io.println(PrintUtil.getBottomBoard(player1.getHouses(), player2.getStore().getSeeds()));
		io.println(lineDecor1);
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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("| P2 ");
		for (int i = player2House.size(); i > 0; i--) {
			String housePadding = getPadding(player2House.get(i - 1).getSeeds());
			stringBuilder.append("| " + (i) + "[" + housePadding + player2House.get(i - 1).getSeeds() + "] ");
		}
		String seedsPadding = getPadding(player1StoreSeeds);
		stringBuilder.append("| " + seedsPadding + String.valueOf(player1StoreSeeds) + " |");
		return stringBuilder.toString();
	}

	/**
	 * Get the bottom board of the game
	 *
	 * @param player1House      Player 1's house
	 * @param player2StoreSeeds The number of seeds in player 2's store
	 * @return A string representing the bottom board
	 */
	private static String getBottomBoard(List<House> player1House, int player2StoreSeeds) {
		StringBuilder stringBuilder = new StringBuilder();
		String seedsPadding = getPadding(player2StoreSeeds);
		stringBuilder.append("| " + String.valueOf(seedsPadding) + String.valueOf(player2StoreSeeds) + " ");
		for (int i = 0; i < player1House.size(); i++) {
			String housePadding = getPadding(player1House.get(i).getSeeds());
			stringBuilder.append("| " + (i + 1) + "[" + housePadding + player1House.get(i).getSeeds() + "] ");
		}
		stringBuilder.append("| P1 |");
		return stringBuilder.toString();
	}

	/**
	 * Return the amount of padding (blank spaces) to add to the strings. Padding is only added
	 * if the number is less than 10.
	 *
	 * @param number The number to calculate the padding for
	 * @return A string containg the amount of padding
	 */
	private static String getPadding(int number) {
		int paddingSize = 2;
		char[] pad = new char[paddingSize - String.valueOf(number).length()];
		Arrays.fill(pad, ' ');
		return String.valueOf(pad);
	}
}