package kalah;

import org.apache.commons.cli.*;

/**
 *  Manager for creating command line options
 */
class CommandLineManager {

	private Options options = new Options();

	void addOptions(String option, Boolean hasArg, String description) {
		options.addOption(option, hasArg, description);
	}

	void parseArgs(String[] args) {
		CommandLineParser commandLineParser = new DefaultParser();
		try {
			CommandLine commandLine = commandLineParser.parse(options, args);
			if (commandLine.hasOption("h")) {
				Rules.NUMBER_OF_HOUSES = Integer.valueOf(commandLine.getOptionValue("h"));
			}
			if (commandLine.hasOption("s")) {
				Rules.STARTING_NUMBER_OF_SEEDS = Integer.valueOf(commandLine.getOptionValue("s"));
			}
			if (commandLine.hasOption("c")) {
				Rules.CAPTURE_ON_EMPTY_HOUSE = true;
			}
		} catch (ParseException | NumberFormatException exception) {
			System.out.println(exception);
		}

	}
}
