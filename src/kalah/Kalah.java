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

	@NotNull private Player mCurrentPlayer;

	private List<Pit> mPlayer1Board = new ArrayList<>();
	private List<Pit> mPlayer2Board = new ArrayList<>();
	private ArrayList<Pit> mGlobalBoard = new ArrayList<>();

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {

		Player player1 = new Player();
		Player player2 = new Player();

		for (int i = 0; i < Player.NUMBER_OF_HOUSES; i++) {
			mPlayer1Board.add(player1.getHouse(i));
		}
		mPlayer1Board.add(player1.getStore());

		for (int i = 0; i < Player.NUMBER_OF_HOUSES; i++) {
			mPlayer2Board.add(player2.getHouse(i));
		}
		mPlayer2Board.add(player2.getStore());

		mGlobalBoard.addAll(mPlayer1Board);
		mGlobalBoard.addAll(mPlayer2Board);

		mCurrentPlayer = player1;

		while (!isGameFinished()) {

			printBoard(io);

			int houseNumberSelected;

			if (mCurrentPlayer.equals(player1)) {
				houseNumberSelected = io.readInteger(AsciiUtil.getPlayer1Turn(), MIN_HOUSE_NUMBER, MAX_HOUSE_NUMBER,
						CANCEL_CODE, "q");
			} else {
				houseNumberSelected = io.readInteger(AsciiUtil.getPlayer2Turn(), MIN_HOUSE_NUMBER, MAX_HOUSE_NUMBER,
                        CANCEL_CODE, "q");
			}

			if (houseNumberSelected == CANCEL_CODE) break;

			// translate the selected house into the 1D array/global board's position
			int globalIndex = 0;
			if (mCurrentPlayer.equals(player1)) {
				globalIndex = houseNumberSelected - 1;
			} else if (mCurrentPlayer.equals(player2)) {
				globalIndex = houseNumberSelected + 6;
			}

			// move to the right from the beginning point
			globalIndex = globalIndex + 1;
			if (globalIndex == 13) globalIndex = 0; // we are at the end

			House house = mCurrentPlayer.getHouse(houseNumberSelected - 1);
			int numberOfSeeds = house.getSeeds();


			int lastBoardIndex = 0; // the index of the global board in which the last seed was sown
			for (int currentIndex = globalIndex; numberOfSeeds > 0; currentIndex++) {
				if (currentIndex > 13) currentIndex = 0;

				boolean isStore = mGlobalBoard.get(currentIndex) instanceof Store;
				if (isStore && mCurrentPlayer.equals(mGlobalBoard.get(currentIndex).getOwner())) {
					mGlobalBoard.get(currentIndex).addSeeds(1);
					numberOfSeeds--;
					lastBoardIndex = currentIndex;
				} else if (!isStore) {
					mGlobalBoard.get(currentIndex).addSeeds(1);
					numberOfSeeds--;
					lastBoardIndex = currentIndex;
				}
			}

			mGlobalBoard.get(globalIndex-1).setSeeds(0);

			// check if a player has won any seeds to be added to their score (capture)
			if (mGlobalBoard.get(lastBoardIndex).equals(mCurrentPlayer) &&
					mGlobalBoard.get(lastBoardIndex).getSeeds() == 0) {
				int houseIndex;
				if (lastBoardIndex < 7) {
					houseIndex = globalIndex;
				} else {
					houseIndex = globalIndex + 5;
				}
				int oppositeHouseIndex = 2 * (Player.NUMBER_OF_HOUSES - houseIndex) + houseIndex;
				// FIXME: set the opposite seeds to 0 ?
				// FIXME: 6/05/2017 collect my own seed too and set the number of seeds to 0
				int seedsToAdd = mGlobalBoard.get(oppositeHouseIndex).getSeeds();
				mCurrentPlayer.addToScore(seedsToAdd);
				// FIXME set the number of seeds back to 0;
			}

			// check if the current player gets another turn
			// if the last seed sown is in the players store
			if (mGlobalBoard.get(lastBoardIndex) instanceof Store && mGlobalBoard.get(numberOfSeeds + houseNumberSelected - 1)
                    .getOwner() == mCurrentPlayer) {
				io.println("You get another turn");
			}

			// check if its the other players turn
			if (!mGlobalBoard.get(lastBoardIndex).getOwner().equals(mCurrentPlayer) || mGlobalBoard.get
                    (lastBoardIndex).getSeeds() > 0) {
				mCurrentPlayer = player2;
			}

			updatePlayerBoards();

		}
	}

	private boolean isGameFinished() {

		boolean isPlayer1Finished = true;
		boolean isPlayer2Finished = true;

		for (int i = 0; i < Player.NUMBER_OF_HOUSES; i++) {
			if (mPlayer1Board.get(i).getSeeds() != 0) {
				isPlayer1Finished = false;
				break;
			}
		}

		for (int i = 0; i < Player.NUMBER_OF_HOUSES; i++) {
			if (mPlayer2Board.get(i).getSeeds() != 0) {
				isPlayer2Finished = false;
				break;

			}
		}
		return isPlayer1Finished || isPlayer2Finished;
	}

	// fix me should the simply return the strings to print then do the prining in the main function?
	private void printBoard(IO io) {
		io.println(AsciiUtil.getLineDecor1());
		io.println(AsciiUtil.getTopBoard(mPlayer2Board, mPlayer1Board.get(mPlayer1Board.size() - 1).getSeeds()));
		io.println(AsciiUtil.getLineDecor2());
		io.println(AsciiUtil.getBottomBoard(mPlayer1Board, mPlayer2Board.get(mPlayer2Board.size() - 1).getSeeds()));
		io.println(AsciiUtil.getLineDecor1());
	}

	private void updatePlayerBoards() {
		for (int i = 0; i < Player.NUMBER_OF_HOUSES; i++) {
			mPlayer1Board.set(i, mGlobalBoard.get(i));
		}
		mPlayer1Board.set(mPlayer1Board.size() - 1, mGlobalBoard.get(Player.NUMBER_OF_HOUSES));

		for (int i = Player.NUMBER_OF_HOUSES + 1; i < mGlobalBoard.size(); i++) {
			mPlayer2Board.set(i - Player.NUMBER_OF_HOUSES - 1, mGlobalBoard.get(i));
		}
		mPlayer2Board.set(mPlayer2Board.size() - 1, mGlobalBoard.get(mGlobalBoard.size() - 1));
	}

}
