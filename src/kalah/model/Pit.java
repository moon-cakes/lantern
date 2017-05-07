package kalah.model;

import com.sun.istack.internal.NotNull;
import kalah.Player;

/**
 * A Pit that holds seeds
 */
public interface Pit {

    void addSeeds(int numberOfSeeds);

    int getSeeds();

    void setSeeds(int seeds);

    @NotNull
    Player getOwner();
}

