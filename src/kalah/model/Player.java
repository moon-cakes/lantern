package kalah.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Player object who has some houses and a store
 */
public class Player {

	private List<House> mHouses = new ArrayList<>();
	private Store mStore = new Store();

	public Player(int numberOfHouses, int numberOfStartingSeeds) {
		for (int i = 0; i < numberOfHouses; i++) {
			mHouses.add(new House(numberOfStartingSeeds));
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

	public boolean areAllHousesEmpty() {
		for (House house : mHouses) {
			if (house.getSeeds() != 0) return false;
		}
		return true;
	}

	/**
	 * Returns the player's store
	 *
	 * @return The player's store
	 */
	public Store getStore() {
		return mStore;
	}

	public int getHouseSeeds() {
		int houseSeeds = 0;
		for (House house : mHouses) {
			houseSeeds += house.getSeeds();
		}
		return houseSeeds;
	}
}
