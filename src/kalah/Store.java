package kalah;

import com.sun.istack.internal.NotNull;
import kalah.model.Pit;

/**
 * A Store which is owned by a {@link Player}
 */
public class Store implements Pit {

    private static int INITIAL_NUMBER_OF_SEEDS = 0;

    private int mSeeds;

    Store() {
        this.mSeeds = INITIAL_NUMBER_OF_SEEDS;
    }

    @Override
    public void addSeeds(int numberOfSeeds) {
        mSeeds += numberOfSeeds;
    }

    @Override
    public int getSeeds() {
        return mSeeds;
    }

    @Override
    public void setSeeds(int seeds) {
        this.mSeeds = seeds;
    }

}
