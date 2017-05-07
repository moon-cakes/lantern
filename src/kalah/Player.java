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

	public Player() {
		for (int i = 0; i < NUMBER_OF_HOUSES; i++) {
			mHouses.add(new House());
		}
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
	 * Returns the player's store
	 *
	 * @return The player's store
	 */
	public Store getStore() {
		return mStore;
	}

	/**
	 * Get the total score for the player calculated from the seeds in her store and seeds in the house
	 *
	 * @return The score for the player
	 */
	public int getScore() {
		int houseSeeds = 0;
		for (House house : mHouses) {
			houseSeeds += house.getSeeds();
		}
		return mStore.getSeeds() + houseSeeds;
	}

}
