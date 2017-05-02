package kalah;

import java.util.ArrayList;
import java.util.List;

/**
 * A player who partakes in the game
 */
public class Player {

    private static int NUMBER_OF_HOUSES = 6;

    private List<House> mHouses = new ArrayList<>();
    private Store mStore;
    private int mScore;

    public Player() {
        for (int i = 0; i < NUMBER_OF_HOUSES; i++) {
            mHouses.add(new House());
        }
        this.mHouses = mHouses;
        this.mStore = mStore;
    }

    /**
     * Add the number of seeds collected to a players score
     *
     * @param seedsCollected The number of seeds collected in one round
     */
    public void addSeeds(int seedsCollected) {
        mScore += seedsCollected;
    }

    public int getScore() {
        return mScore;
    }
}
