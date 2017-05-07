package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.util.AsciiUtil;

import java.io.IOException;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {

	private static int CANCEL_CODE = -1;
	private static int MIN_HOUSE_NUMBER = 1;
	private static int MAX_HOUSE_NUMBER = Player.NUMBER_OF_HOUSES;

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {

		int houseNumberSelected = 0;
		Board board = new Board();

		while (true) {

			board.print(io);

			if (board.isGameFinished()) break;

			Player mCurrentPlayer = board.getCurrentPlayer();
			houseNumberSelected = io.readInteger(mCurrentPlayer.equals(board.getPlayer1()) ? AsciiUtil.getPlayer1Turn
					() : AsciiUtil.getPlayer2Turn(), MIN_HOUSE_NUMBER, MAX_HOUSE_NUMBER, CANCEL_CODE, "q");

			if (houseNumberSelected == CANCEL_CODE) break;

			try {
				board.update(houseNumberSelected);
			} catch (IOException e) {
				e.getMessage();
				io.println("House is empty. Move again.");
			}
		}

		io.println("Game over");
		board.print(io);

		if (houseNumberSelected != CANCEL_CODE) {
			Player player1 = board.getPlayer1();
			Player player2 = board.getPlayer2();

			io.println("\tplayer 1:" + player1.getScore());
			io.println("\tplayer 2:" + player2.getScore());

			if (player1.getScore() > player2.getScore()) {
				io.println("Player 1 wins!");
			} else if (player2.getScore() > player1.getScore()) {
				io.println("Player 2 wins!");
			} else {
				io.println("A tie!");
			}
		}
	}
}