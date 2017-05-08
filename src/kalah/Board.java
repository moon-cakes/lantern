package kalah;

import com.sun.istack.internal.NotNull;

import java.io.IOException;

/**
 * A class that represents the Mancala board.
 */
public class Board {

	@NotNull private Player mPlayer1 = new Player();
	@NotNull private Player mPlayer2 = new Player();
	@NotNull private Player mCurrentPlayer = mPlayer1;

	// update board method
	void update(int houseNumberSelected) throws IOException {

		int numberOfSeeds = mCurrentPlayer.getHouse(houseNumberSelected - 1).getSeeds();
		mCurrentPlayer.getHouse(houseNumberSelected - 1).setSeeds(0);

		int lastBoardIndex = 0; // the index of the global board in which the last seed was sown

		if (numberOfSeeds == 0) {
			throw new IOException("The chosen house has no seeds");
		} else {
			Player playerBoardToDistributeOn = mCurrentPlayer;

			for (int currentIndex = houseNumberSelected; numberOfSeeds > 0; currentIndex++) {
				lastBoardIndex = currentIndex;

				if (currentIndex <= Player.NUMBER_OF_HOUSES - 1) {
					playerBoardToDistributeOn.getHouse(currentIndex).addSeeds(1);
					numberOfSeeds--;
					if (numberOfSeeds == 0) break;
				}

				// check if we have reached the end of the board
				if (currentIndex >= Player.NUMBER_OF_HOUSES - 1) {
					// check if we can place it in the store
					boolean isStoreCurrentPlayers = playerBoardToDistributeOn.equals(mCurrentPlayer);
					if (isStoreCurrentPlayers) {
						playerBoardToDistributeOn.getStore().addSeeds(1);
						numberOfSeeds--;
						lastBoardIndex = -1;
					}
					// swap player's board if the current index is the last index on the board
					playerBoardToDistributeOn = playerBoardToDistributeOn.equals(mPlayer1) ? mPlayer2 : mPlayer1;
					currentIndex = -1; // the current index should be 0, but set it to -1 as the for
					// loop is going to increment it again
				}
			}

			// check if the current player gets another turn
			// if the last seed sown is in the players store
			if (lastBoardIndex != -1) {
				// check for capture, i.e. when you land on your own board that had no seeds in it
				if (playerBoardToDistributeOn.equals(mCurrentPlayer) &&
						playerBoardToDistributeOn.getHouse(lastBoardIndex).getSeeds() == 1) {
					Player playerToCaptureFrom = mCurrentPlayer.equals(mPlayer1) ? mPlayer2 : mPlayer1;
					House oppositeHouse = playerToCaptureFrom.getHouse(Player.NUMBER_OF_HOUSES - 1 -
							lastBoardIndex);
					if (oppositeHouse.getSeeds() != 0) {
						mCurrentPlayer.getStore().addSeeds(oppositeHouse.getSeeds() + 1);
						oppositeHouse.setSeeds(0);
						mCurrentPlayer.getHouse(lastBoardIndex).setSeeds(0);
					}
				}
				mCurrentPlayer = mCurrentPlayer.equals(mPlayer1) ? mPlayer2 : mPlayer1;
			}
		}
	}

	Player getPlayer1() {
		return mPlayer1;
	}

	Player getPlayer2() {
		return mPlayer2;
	}

	Player getCurrentPlayer() {
		return mCurrentPlayer;
	}

	/**
	 * Determine if the game has finished
	 *
	 * @return True if the current player has no more seeds to play, false if otherwise
	 */
	boolean isGameFinished() {
		boolean isFinished = true;
		for (House house : mCurrentPlayer.getHouses()) {
			if (house.getSeeds() != 0) isFinished = false;
		}
		return isFinished;
	}

}