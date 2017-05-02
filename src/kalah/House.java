package kalah;

import com.sun.istack.internal.NotNull;
import kalah.model.Pit;

/**
 * A House which is owned by a {@link Player}
 */
public class House implements Pit {

    private static int INITIAL_NUMBER_OF_SEEDS = 6;

    private int mCurrentSeeds;

    public House() {
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
