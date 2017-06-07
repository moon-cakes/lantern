package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.model.Player;
import kalah.util.PrintUtil;

/**
 * This is the main class which is responsible for handling starting and stopping of the game
 */
public class Kalah {

	private static int CANCEL_CODE = -1;

	public static void main(String[] args) {
		CommandLineManager commandLineManager = new CommandLineManager();
		commandLineManager.addOptions("h", true, "Number of houses");
		commandLineManager.addOptions("s", true, "Stating number of seeds");
		commandLineManager.addOptions("c", false, "Capture an empty house");
		commandLineManager.parseArgs(args);

		new Kalah().play(new MockIO());
	}

	public void play(IO io) {

		Board board = new Board();
		int houseNumberSelected = 0;

		while (true) {

			PrintUtil.printBoard(io, board.getPlayer1(), board.getPlayer2(), Rules.NUMBER_OF_HOUSES);

			if (board.isGameFinished()) break;

			Player currentPlayer = board.getCurrentPlayer();
			houseNumberSelected = PrintUtil.printPromptMessage(io, currentPlayer, board.getPlayer1(),
					1, Rules.NUMBER_OF_HOUSES, CANCEL_CODE);

			if (houseNumberSelected == CANCEL_CODE) break;

			try {
				board.update(houseNumberSelected);
			} catch (ChosenHouseIsEmptyException e) {
				PrintUtil.printChosenHouseIsEmpty(io);
			}
		}

		PrintUtil.printGameOver(io);
		PrintUtil.printBoard(io, board.getPlayer1(), board.getPlayer2(), Rules.NUMBER_OF_HOUSES);

		if (houseNumberSelected != CANCEL_CODE) {
			PrintUtil.printScores(io, board.getPlayer1Score(), board.getPlayer2Score());
			if (board.getWinner() == null) {
				PrintUtil.printTie(io);
			} else if (board.getWinner().equals(board.getPlayer1())) {
				PrintUtil.printPlayer1Wins(io);
			} else {
				PrintUtil.printPlayer2Wins(io);
			}
		}
	}
}