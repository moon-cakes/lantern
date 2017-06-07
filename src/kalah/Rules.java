package kalah;

import kalah.model.Player;

/**
 * Store a set of rules
 */
public class Rules {

	static int NUMBER_OF_HOUSES = 6;
	static int STARTING_NUMBER_OF_SEEDS = 4;
	static boolean CAPTURE_ON_EMPTY_HOUSE = false;

	public static int getScore(Player player1) {
		return player1.getStore().getSeeds() + player1.getHouseSeeds();
	}

	public static Player getWinner(Player player1, Player player2) {
		if (getScore(player1) > getScore(player2)) {
			return player1;
		} else if (getScore(player1) < getScore(player2)) {
			return player2;
		} else {
			return null;
		}
	}

	public static boolean isGameFinished(Player currentPlayer, Player player1, Player player2) {
		if (currentPlayer.equals(player1)) {
			return player1.areAllHousesEmpty();
		} else {
			return player2.areAllHousesEmpty();
		}
	}
}
