package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import com.sun.istack.internal.NotNull;
import kalah.model.Pit;
import kalah.util.AsciiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {

	private static int CANCEL_CODE = -1;
	private static int MIN_HOUSE_NUMBER = 1;
	private static int MAX_HOUSE_NUMBER = Player.NUMBER_OF_HOUSES;

	@NotNull Player player1 = new Player();
	@NotNull Player player2 = new Player();
	@NotNull private Player mCurrentPlayer = player1;

	private int houseNumberSelected;

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		while (!isGameFinished()) {
			printBoard(io);

			houseNumberSelected = io.readInteger(mCurrentPlayer.equals(player1) ? AsciiUtil.getPlayer1Turn() :
					AsciiUtil.getPlayer2Turn(), MIN_HOUSE_NUMBER, MAX_HOUSE_NUMBER, CANCEL_CODE, "q");

			if (houseNumberSelected == CANCEL_CODE) {
				io.println("Game over");
				break;
			}

			int numberOfSeeds = mCurrentPlayer.getHouse(houseNumberSelected).getSeeds();
			mCurrentPlayer.getHouse(houseNumberSelected).setSeeds(0);

			int lastBoardIndex = 0; // the index of the global board in which the last seed was sown

			if (numberOfSeeds == 0) {
				io.println("House is empty. Move again.");
			} else {
				Player playerBoardToDistributeOn = mCurrentPlayer;

				for (int currentIndex = houseNumberSelected - 1; numberOfSeeds > 0; currentIndex++) {

					lastBoardIndex = currentIndex;

					playerBoardToDistributeOn.getHouse(currentIndex).addSeeds(1);
					numberOfSeeds--;

					if (numberOfSeeds == 0) break;

					// check if we have reached the end of the board
					if (currentIndex == Player.NUMBER_OF_HOUSES - 1) {
						// check if we can place it in the store
						boolean isStoreCurrentPlayers = playerBoardToDistributeOn.equals(mCurrentPlayer);
						if (isStoreCurrentPlayers) {
							playerBoardToDistributeOn.getStore().addSeeds(1);
							numberOfSeeds--;
							lastBoardIndex = -1;
						}
						// swap player's board if the current index is the last index on the board
						playerBoardToDistributeOn = playerBoardToDistributeOn.equals(player1) ? player2 : player1;
						currentIndex = 0;
					}
				}

				// check if the current playerBoardToDistributeOn gets another turn
				// if the last seed sown is in the players store
				if (lastBoardIndex == -1) {
					io.println("You get another turn");
				} else {
					// check for capture, i.e. when you land on your own board that had no seeds in it
					if (playerBoardToDistributeOn.equals(mCurrentPlayer) &&
							playerBoardToDistributeOn.getHouse(lastBoardIndex).getSeeds() == 1) {
						Player playerToCaptureFrom = mCurrentPlayer.equals(player1) ? player2 : player1;
						House oppositeHouse = playerToCaptureFrom.getHouse(Player.NUMBER_OF_HOUSES - 1 -
								lastBoardIndex);
						oppositeHouse.setSeeds(0);
						mCurrentPlayer.getStore().addSeeds(oppositeHouse.getSeeds() + 1);
						mCurrentPlayer.getHouse(lastBoardIndex).setSeeds(0);
					}
					mCurrentPlayer = mCurrentPlayer.equals(player1) ? player2 : player1;
				}
			}
		}

		if (houseNumberSelected != CANCEL_CODE) {
			io.println("Score" + String.valueOf(player1.getScore() + player1.getStore().getSeeds()) + String.valueOf
					(player2.getScore() + player1.getStore().getSeeds()));
		}
	}

	/**
	 * Determine if the game has finished
	 *
	 * @return True if the current player has no more seeds to play, false if otherwise
	 */
	private boolean isGameFinished() {
		boolean isFinished = true;
		for (House house : mCurrentPlayer.getHouses()) {
			if (house.getSeeds() != 0) isFinished = false;
		}
		return isFinished;
	}

	private void printBoard(IO io) {
		io.println(AsciiUtil.getLineDecor1());
		io.println(AsciiUtil.getTopBoard(player2.getHouses(), player1.getStore().getSeeds()));
		io.println(AsciiUtil.getLineDecor2());
		io.println(AsciiUtil.getBottomBoard(player1.getHouses(), player2.getStore().getSeeds()));
		io.println(AsciiUtil.getLineDecor1());
	}
}