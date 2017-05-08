package kalah;

import kalah.model.Pit;

/**
 * A Store which is owned by a {@link Player}
 */
public class Store implements Pit {

    private static int INITIAL_NUMBER_OF_SEEDS = 0;
    private int mSeeds = INITIAL_NUMBER_OF_SEEDS;

    @Override
    public void addSeeds(int numberOfSeeds) {
        mSeeds += numberOfSeeds;
    }

    @Override
    public int getSeeds() {
        return mSeeds;
    }

}
