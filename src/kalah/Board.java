package kalah;

import com.sun.istack.internal.NotNull;
import kalah.model.House;
import kalah.model.Player;

/**
 * A class that represents one round or one move of the game. It distributes the seeds and checks
 * if rules are met.
 */
class Board {

	@NotNull private Player mPlayer1 = new Player(Rules.NUMBER_OF_HOUSES, Rules.STARTING_NUMBER_OF_SEEDS);
	@NotNull private Player mPlayer2 = new Player(Rules.NUMBER_OF_HOUSES, Rules.STARTING_NUMBER_OF_SEEDS);
	@NotNull private Player mCurrentPlayer = mPlayer1;

	Player getPlayer1() {
		return mPlayer1;
	}

	Player getPlayer2() {
		return mPlayer2;
	}

	Player getCurrentPlayer() {
		return mCurrentPlayer;
	}

	boolean isGameFinished() {
		return Rules.isGameFinished(mCurrentPlayer, mPlayer1, mPlayer2);
	}

	Player getWinner() {
		return Rules.getWinner(mPlayer1, mPlayer2);
	}

	int getPlayer1Score() {
		return Rules.getScore(mPlayer1);
	}

	int getPlayer2Score() {
		return Rules.getScore(mPlayer2);
	}

	// update board method
	void update(int houseNumberSelected) throws ChosenHouseIsEmptyException {

		int numberOfSeeds = mCurrentPlayer.getHouse(houseNumberSelected - 1).getSeeds();
		mCurrentPlayer.getHouse(houseNumberSelected - 1).resetSeeds();

		int lastSeedDistributedIndex = 0; // the index of the global board in which the last seed was sown

		if (numberOfSeeds == 0) {
			throw new ChosenHouseIsEmptyException();
		} else {
			Player playerBoardToDistributeOn = mCurrentPlayer;

			for (int currentIndex = houseNumberSelected; numberOfSeeds > 0; currentIndex++) {
				lastSeedDistributedIndex = currentIndex;

				if (currentIndex <= Rules.NUMBER_OF_HOUSES - 1) {
					playerBoardToDistributeOn.getHouse(currentIndex).addSeeds(1);
					numberOfSeeds--;
					if (numberOfSeeds == 0) break;
				}

				// check if we have reached the end of the board
				if (currentIndex >= Rules.NUMBER_OF_HOUSES - 1) {
					// check if we can place it in the store
					boolean isStoreCurrentPlayers = playerBoardToDistributeOn.equals(mCurrentPlayer);
					if (isStoreCurrentPlayers) {
						playerBoardToDistributeOn.getStore().addSeeds(1);
						numberOfSeeds--;
						lastSeedDistributedIndex = -1;
					}
					// swap player's board if the current index is the last index on the board
					playerBoardToDistributeOn = playerBoardToDistributeOn.equals(mPlayer1) ? mPlayer2 : mPlayer1;
					currentIndex = -1; // the current index should be 0, but set it to -1 as the for
					// loop is going to increment it again
				}
			}

			// check if the current player gets another turn
			// if the last seed sown is in the players store
			if (lastSeedDistributedIndex != -1) {
				// check for capture, i.e. when you land on your own board and it had no seeds in it
				if (playerBoardToDistributeOn.equals(mCurrentPlayer) &&
						playerBoardToDistributeOn.getHouse(lastSeedDistributedIndex).getSeeds() == 1) {
					Player playerToCaptureFrom = mCurrentPlayer.equals(mPlayer1) ? mPlayer2 : mPlayer1;
					House oppositeHouse = playerToCaptureFrom.getHouse(Rules.NUMBER_OF_HOUSES - 1 -
							lastSeedDistributedIndex);
					if (oppositeHouse.getSeeds() != 0 || Rules.CAPTURE_ON_EMPTY_HOUSE) {
						mCurrentPlayer.getStore().addSeeds(oppositeHouse.getSeeds() + 1);
						oppositeHouse.resetSeeds();
						mCurrentPlayer.getHouse(lastSeedDistributedIndex).resetSeeds();
					}
				}
				mCurrentPlayer = mCurrentPlayer.equals(mPlayer1) ? mPlayer2 : mPlayer1;
			}
		}
	}
}