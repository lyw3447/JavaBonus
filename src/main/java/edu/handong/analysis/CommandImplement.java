package edu.handong.analysis;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import edu.handong.analysis.utils.Util;;

public class CommandImplement {
	private String path;
	private boolean help;
	private boolean line;
	private boolean reverse;
	private boolean absolutePath;
	
	public void run(String[] args) {
		Options options = createOptions();
		
		if (parseOptions(options, args)) {
			if (help) { 	// option 'h' --> help
				printHelp(options);
				return;
			}
			
			if (path == null) { 	// option 'p' --> set path
				System.out.println("Please enter path for option p");
				path = System.getProperty("user.dir");
			}
			else {
				try {
					if (!new File(path).isDirectory()) {
						throw new Util("\nNot directory! Please enter path for option p");
					}
				} catch (Util e) {
					System.out.println(e.getMessage());
					return;
				}
			}
			
			File file = new File(path);
			String[] files = file.list();
			
			if (line) {	// option 'l' --> ls
				System.out.println("<File list>\n");
				
				for (String onefile : files) {
					System.out.println(onefile);
				}
				System.out.println("\n");
			}
			
			if (reverse) { 	// option 'r' --> reverse ls
				List<String> list = Arrays.asList(files);
				Collections.reverse(list);
				files = list.toArray(new String[list.size()]);
				
				System.out.println("<Reverse file list>\n");
				
				for (String onefile : files) {
					System.out.println(onefile);
				}
				System.out.println("\n");
			}
			
			if (absolutePath) { 	// option 'a' --> absolute path
				System.out.println("<Absolute path>\n");
				System.out.println(file.getAbsolutePath() + "\n");
			}
			
		}
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options,  args);
			
			path = cmd.getOptionValue("p");
			line = cmd.hasOption("l");
			help = cmd.hasOption("h");
			reverse = cmd.hasOption("r");
			absolutePath = cmd.hasOption("a");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}
		
		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("p").longOpt("path")
				.desc("Set a path of a directory")
				.hasArg()
				.build());
		options.addOption(Option.builder("l").longOpt("line")
				.desc("Print out list line")
				.build());
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());
		options.addOption(Option.builder("r").longOpt("reverse")
				.desc("Display list reverse order")
				.build());
		options.addOption(Option.builder("a").longOpt("absolute")
				.desc("Print out absolute path of present directory")
				.build());
		
		return options;
	}
	
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "Command 'ls' implementing program";
		String footer = "";
		formatter.printHelp("Implementing the 'zip' command", header, options, footer, true);
	}
	
}
