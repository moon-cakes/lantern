package kalah;

import java.util.ArrayList;
import java.util.List;

/**
 * A player who plays the game and had a score. A player has 6 {@link House's} and a {@link Store}.
 */
public class Player {

	static final int NUMBER_OF_HOUSES = 6;

	private List<House> mHouses = new ArrayList<>();
	private Store mStore = new Store();
	private int mScore;

	public Player() {
		for (int i = 0; i < NUMBER_OF_HOUSES; i++) {
			mHouses.add(new House());
		}
		mStore.setOwner(this);
	}

	/**
	 * Returns the player's store
	 *
	 * @return
	 */
	public Store getStore() {
		return mStore;
	}

	/**
	 * Return a House for the houseNumber
	 *
	 * @param houseNumber The houseNumber of the house to be retrieved
	 * @return a House according to a house number                                                         /
	 */
	public House getHouse(int houseNumber) {
		return mHouses.get(houseNumber);
	}

	public List<House> getHouses() {
		return mHouses;
	}

	/**
	 * Add the number of seeds collected to a players score
	 *
	 * @param seedsCollected The number of seeds collected in one round
	 */
	public void addToScore(int seedsCollected) {
		mScore += seedsCollected;
	}

	public int getScore() {
		return mScore;
	}

}
