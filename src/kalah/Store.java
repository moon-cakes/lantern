package kalah;

import com.sun.istack.internal.NotNull;
import kalah.model.Pit;

/**
 * A Store which is owned by a {@link Player}
 */
public class Store implements Pit {

    private static int INITIAL_NUMBER_OF_SEEDS = 0;

    private int mCurrentSeeds;

    public Store() {
        this.mCurrentSeeds = INITIAL_NUMBER_OF_SEEDS;
    }

    @Override
    public int getSeeds() {
        return mCurrentSeeds;
    }

    @Override
    @NotNull
    public Player getOwner() {
        return new Player();
    }
}
