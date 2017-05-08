package kalah;

import kalah.model.Pit;

/**
 * A House which is owned by a {@link Player}
 */
public class House implements Pit {

	private static int INITIAL_NUMBER_OF_SEEDS = 4;
	private int mSeeds = INITIAL_NUMBER_OF_SEEDS;

	@Override
	public void addSeeds(int numberOfSeeds) {
		mSeeds += numberOfSeeds;
	}

	@Override
	public int getSeeds() {
		return mSeeds;
	}

	void resetSeeds() {
		mSeeds = 0;
	}
}
