package kalah.model;

/**
 * A House which is owned by a {@link Player}
 */
public class House implements Pit {

	private int mSeeds;

	public House(int numberOfStartingSeeds) {
		this.mSeeds = numberOfStartingSeeds;
	}

	@Override
	public void addSeeds(int numberOfSeeds) {
		mSeeds += numberOfSeeds;
	}

	@Override
	public int getSeeds() {
		return mSeeds;
	}

	public void resetSeeds() {
		mSeeds = 0;
	}
}
