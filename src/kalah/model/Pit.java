package kalah.model;

import com.sun.istack.internal.NotNull;
import kalah.Player;

/**
 * A Pit that holds seeds
 */
public interface Pit {

    int getSeeds();

    @NotNull
    Player getOwner();

}

