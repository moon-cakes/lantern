package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.util.PrintUtil;

import java.io.IOException;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {

		Board board = new Board();
		int houseNumberSelected = 0;

		while (true) {

			PrintUtil.printBoard(io, board.getPlayer1(), board.getPlayer2());

			if (board.isGameFinished()) break;

			Player currentPlayer = board.getCurrentPlayer();
			houseNumberSelected = PrintUtil.printPromptMessage(io, currentPlayer, board.getPlayer1());

			if (houseNumberSelected == PrintUtil.CANCEL_CODE) break;

			try {
				board.update(houseNumberSelected);
			} catch (IOException e) {
				PrintUtil.printChosenHouseIsEmpty(io);
			}
		}

		PrintUtil.printGameOver(io);
		PrintUtil.printBoard(io, board.getPlayer1(), board.getPlayer2());

		if (houseNumberSelected != PrintUtil.CANCEL_CODE) {
			int player1Score = board.getPlayer1().getScore();
			int player2Score = board.getPlayer2().getScore();

			PrintUtil.printScores(io, player1Score, player2Score);
		}
	}
}