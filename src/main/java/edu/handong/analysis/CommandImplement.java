package edu.handong.analysis;

import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.comparator.LastModifiedFileComparator;

public class CommandImplement {
	private String path;
	private boolean help;
	private boolean unSorted;
	private boolean sorted;
	private boolean reverse;
	private boolean lastmodified;
	private boolean size;
	
	public void run(String[] args) {
		Options options = createOptions();
		
		if (parseOptions(options, args)) {
			if (help) { 	// option 'i' --> help
				printHelp(options);
				return;
			}
	
			if (path == null) { 	// option 'ls' --> set path
				System.out.println("Please enter path for option ls");
				path = System.getProperty("user.dir");
			}
			
			File file = new File(path);
			File[] fileList = file.listFiles();
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> paths = new ArrayList<String>();
			
			for(File onefile: fileList) {
				  if(onefile.isFile()) {
				    String tempPath = onefile.getParent();
				    paths.add(tempPath);
				    
				    String tempFileName = onefile.getName();
				    names.add(tempFileName);
				  }
				}
			
			if (unSorted) {	// option 'f' --> unsorted
				System.out.println("<File list>\n");
				
				for (String onefile : names) {
					System.out.println(onefile);
				}
				System.out.println("\n");
			}
			
			if (sorted) {	// option 'a' --> sorted
				Collections.sort(names);
				System.out.println("<Sorted file list>\n");
				
				for (String onefile : names) {
					System.out.println(onefile);
				}
				System.out.println("\n");
			}
			
			if (reverse) { 	// option 'r' --> reversed
				Collections.reverse(names);
				System.out.println("<Reverse file list>\n");
				
				for (String onefile : names) {
					System.out.println(onefile);
				}
				System.out.println("\n");
			}
			
			if (lastmodified) { 	// option 't' --> last modified
				System.out.println("<Last Modified>\n");
				
				//Arrays.sort(fileList, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				for (File toCheck : fileList) {
					long currTimeModified = toCheck.lastModified();
					String pattern = "yyyy-MM-dd hh:mm aa";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					Date lastModifiedDate = new Date( currTimeModified );

					System.out.println( "The file " + toCheck + " was last modified on " + simpleDateFormat.format( lastModifiedDate ) );
				}
			}
			
			if (size) { 	//option 'h' --> print out file size
				System.out.println("<Files by size>\n");
				String size = ""
					
				for (File onefile : fileList) {
					long filesize = onefile.length();
					size = Long.toString(filesize) + "bytes";
					System.out.println(onefile.getName() + "File Size : " + size);

				}
				System.out.println("\n");
			}
		}
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options,  args);
			
			path = cmd.getOptionValue("ls");
			unSorted = cmd.hasOption("f");
			sorted = cmd.hasOption("a");
			help = cmd.hasOption("i");
			reverse = cmd.hasOption("r");
			lastmodified = cmd.hasOption("t");
			size = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}
		
		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("ls").longOpt("path")
				.desc("Set a path of a directory")
				.hasArg()
				.build());
		options.addOption(Option.builder("f").longOpt("line")
				.desc("Print out unsorted file list")
				.build());
		options.addOption(Option.builder("a").longOpt("sorted")
				.desc("Print out sorted list")
				.build());
		options.addOption(Option.builder("i").longOpt("help")
				.desc("Help")
				.build());
		options.addOption(Option.builder("r").longOpt("reverse")
				.desc("Print out list reverse order")
				.build());
		options.addOption(Option.builder("t").longOpt("lastModified")
				.desc("Print out last modified")
				.build());
		options.addOption(
				Option.builder("h").longOpt("size")
				.desc("Print out the size of files")
				.argName("size")
				.build());
		
		return options;
	}
	
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "Command 'ls' implementing program";
		String footer = "";
		formatter.printHelp("Implementing the 'ls' command", header, options, footer, true);
	}
	
}
